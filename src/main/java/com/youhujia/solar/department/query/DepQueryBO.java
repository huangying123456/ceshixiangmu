package com.youhujia.solar.department.query;

import com.google.zxing.WriterException;
import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.DepartmentStatusEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.halo.util.LogInfoGenerator;
import com.youhujia.solar.department.Department;
import com.youhujia.solar.department.DepartmentDAO;
import com.youhujia.solar.department.DepartmentDTOFactory;
import com.youhujia.solar.department.create.DepCreateBO;
import com.youhujia.solar.organization.Organization;
import com.youhujia.solar.organization.OrganizationDAO;
import com.youhujia.solar.wxQrcode.WechatQRCodeBO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by huangYing on 2017/4/17.
 */
@Service
public class DepQueryBO {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private DepartmentDAO departmentDAO;
    @Resource
    private OrganizationDAO organizationDAO;
    @Resource
    private DepCreateBO depCreateBO;
    @Resource
    private WechatQRCodeBO wechatQRCodeBO;
    @Autowired
    private DepQueryContextFactory depQueryContextFactory;

    @Autowired
    private DepartmentDTOFactory departmentDTOFactory;

    public DepQueryContext getDepartmentById(Long departmentId) throws IOException, WriterException {

        DepQueryContext context = new DepQueryContext();

        Department department = departmentDAO.findOne(departmentId);

        if (department == null) {
            logger.error(LogInfoGenerator.generateCallInfo("DepQueryBO—>getDepartmentById", "error", "departmentId illegal", "departmentId", departmentId));
            return context;
        }

        if (StringUtils.isEmpty(department.getWxSubQRCodeValue())
                || department.getWxSubQRCodeValue().contains("http://")
                || StringUtils.isEmpty(department.getQrCode())) {
            //如果此科室是访客科室，则将departmentId变为对应的hostId
            if (department.getGuest() == 1) {
                if (department.getHostId() == null) {
                    logger.error(LogInfoGenerator.generateCallInfo("DepQueryBO—>getDepartmentById", "error", "department illegal,guest is 1 but hostId is null", "departmentId", departmentId));
                } else {
                    departmentId = department.getHostId();
                }
            }

            Map<String,String> valueMap = wechatQRCodeBO.generateWxSubQRCodeBase64Image(departmentId);
            department.setWxSubQRCodeValue(valueMap.get(WechatQRCodeBO.WXSUBQRCODEKEY));
            department.setQrCode(valueMap.get(WechatQRCodeBO.QRCODEKEY));
            department = departmentDAO.save(department);
        }

        context.setDepartment(department);
        return context;
    }

    public DepQueryContext getDepartmentListByIds(String ids) {

        DepQueryContext context = new DepQueryContext();

        List<Department> departmentList = departmentDAO.findAll(getListByString(ids));
        context.setDepartmentList(departmentList);

        return context;
    }

    public DepQueryContext getDepartmentByNo(String departmentNo) {

        DepQueryContext context = new DepQueryContext();

        List<Department> list = departmentDAO.findByNumberAndStatus(departmentNo, DepartmentStatusEnum.NORMAL.getStatus());

        List<Department> rstList = new ArrayList<>();
        for (Department dpt : list) {
            if (dpt.getGuest() > 0L) {
                continue;
            }
            rstList.add(dpt);
        }
        if (rstList.size() > 0) {
            context.setDepartment(rstList.get(0));
        } else {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "there is not found department by departmentNo, departmentNo:" + departmentNo);
        }
        return context;

    }

    public DepQueryContext getGuestDepartmentByHostDepartmentId(Long departmentId) {

        DepQueryContext context = new DepQueryContext();

        Department department = departmentDAO.findOne(departmentId);

        if (department == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "there is not found guest department by host departmentId, departmentId:" + departmentId);
        }

        if (department.getGuest() > 0L) {
            context.setDepartment(department);
        } else {
            context.setDepartment(depCreateBO.createOrGetGuestDepartment(department));
        }

        return context;
    }

    public DepQueryContext getDepartmentListByOrgId(Long orgId) {

        DepQueryContext context = new DepQueryContext();

        Organization organization = organizationDAO.findOne(orgId);
        if (organization == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误的医院id");
        }

        List<Department> departments = departmentDAO.findByOrganizationIdAndStatus(orgId, DepartmentStatusEnum.NORMAL.getStatus());

        context.setDepartmentList(departments);

        return context;
    }

    public DepQueryContext getAllWithoutDeleteDepartmentByOrgId(Long orgId) {

        DepQueryContext context = new DepQueryContext();

        if (orgId == null || orgId.intValue() < 0) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "医院id为空或者非法！");
        }
        Organization organization = organizationDAO.findOne(orgId);
        if (organization == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "此id对应的医院并不存在啊！！");
        }
        List<Department> departments = departmentDAO.findByOrganizationIdWithStatus(orgId, DepartmentStatusEnum.NORMAL.getStatus());

        context.setDepartmentList(departments);
        context.setOrganization(organization);

        return context;
    }

    private List<Long> getListByString(String ids) {

        String[] strings = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String id : strings) {
            if (id == null || id.trim().isEmpty()) {
                continue;
            }

            // 解析 ID
            try {
                list.add(Long.parseLong(id));
            } catch (Exception e) {
                logger.error(LogInfoGenerator.generateCallInfo("DepQueryBO—>getListByString", "error", "invalid id", "id", id));
            }
        }
        return list;
    }

    public DepQueryContext getDepartmentByQRCode(String departmentQRCode) {

        DepQueryContext context = new DepQueryContext();

        List<Department> list = departmentDAO.findByQrCodeAndStatus(departmentQRCode, DepartmentStatusEnum.NORMAL.getStatus());

        List<Department> rstList = new ArrayList<>();
        for (Department dpt : list) {
            if (dpt.getGuest() > 0L) {
                continue;
            }
            rstList.add(dpt);
        }
        if (rstList.size() > 0) {
            context.setDepartment(rstList.get(0));
        }
        return context;
    }
    public Solar.DepartmentListDTO queryDepartment(Map<String, String> map) {

        DepQueryContext context = depQueryContextFactory.buildQueryDepartmentContext(map);

        queryDepartmentList(context);

        return departmentDTOFactory.buildDepartmentListDTO(context);
    }

    private DepQueryContext queryDepartmentList(DepQueryContext context) {

        /**
         * 1. dpt ids size > 0, 取出所有科室
         * 2. org ids size > 0, 第一步结果过滤 orgIds
         * 3. 上一步结果过滤 status
         */
        List<Department> departments;
        List<Long> organizationIds = context.getOrganizationIdsList();
        List<Long> departmentIds = context.getDepartmentIdsList();

        if (departmentIds.size() > 0) {
            departments = departmentDAO.findAll(departmentIds);
            if (organizationIds.size() > 0) {
                departments = departments.stream().filter(d -> organizationIds.contains(d.getOrganizationId())).collect(Collectors.toList());
            }
        } else {
            departments = departmentDAO.findByOrganizationIdIn(organizationIds);
        }

        //考虑到可能存在访客科室，要将访客科室过滤掉
        List<Department> hostDepartments = departments
            .stream()
            .filter(department -> department.getGuest() == 0)
            .collect(Collectors.toList());

        context.setDepartmentList(hostDepartments);

        filterDepartmentByStatus(context);

        return context;
    }

    private void filterDepartmentByStatus(DepQueryContext context) {

        List<Department> departmentList = context.getDepartmentList();

        List<Integer> statusList = context.getDepartmentStatusEnumList().stream().map(DepartmentStatusEnum::getStatus).collect(Collectors.toList());

        departmentList = departmentList.stream().filter(d -> statusList.contains(d.getStatus())).collect(Collectors.toList());

        context.setDepartmentList(departmentList);
    }
}
