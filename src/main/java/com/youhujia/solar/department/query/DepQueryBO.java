package com.youhujia.solar.department.query;

import com.google.zxing.WriterException;
import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.DepartmentStatusEnum;
import com.youhujia.solar.department.Department;
import com.youhujia.solar.department.DepartmentDAO;
import com.youhujia.solar.department.create.DepCreateBO;
import com.youhujia.solar.organization.Organization;
import com.youhujia.solar.organization.OrganizationDAO;
import com.youhujia.solar.wxQrcode.WechatQRCodeBO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
@Service
public class DepQueryBO {

    @Resource
    private DepartmentDAO departmentDAO;
    @Resource
    private OrganizationDAO organizationDAO;
    @Resource
    private DepCreateBO depCreateBO;

    @Resource
    private WechatQRCodeBO wechatQRCodeBO;

    public DepQueryContext getDepartmentById(Long departmentId) throws IOException, WriterException {

        DepQueryContext context = new DepQueryContext();

        Department department = departmentDAO.findOne(departmentId);

        if (department == null) {
            return context;
        }

        if (StringUtils.isEmpty(department.getWxSubQRCodeValue())
                || department.getWxSubQRCodeValue().contains("http://")) {
            department.setWxSubQRCodeValue(wechatQRCodeBO.generateWxSubQRCodeBase64Image(departmentId));
            department = departmentDAO.save(department);
        }

        context.setDepartment(department);
        return context;

    }

    public DepQueryContext getDepartmentListByIds(String ids) {

        DepQueryContext context = new DepQueryContext();

        if (ids == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "departmentIds 为空");
        }
        List<Department> departmentList = departmentDAO.findAll(getListByString(ids));
        if (departmentList == null || departmentList.size() <= 0) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！错误的科室id！");
        }
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
        for (String strings1 : strings) {
            list.add(Long.parseLong(strings1));
        }
        return list;
    }
}
