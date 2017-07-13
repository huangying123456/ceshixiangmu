package com.youhujia.solar.organization.query;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.DepartmentStatusEnum;
import com.youhujia.halo.solar.OrganizationStatusEnum;
import com.youhujia.halo.util.LogInfoGenerator;
import com.youhujia.solar.area.Area;
import com.youhujia.solar.area.AreaDAO;
import com.youhujia.solar.common.SolarExceptionCodeEnum;
import com.youhujia.solar.department.Department;
import com.youhujia.solar.department.DepartmentDAO;
import com.youhujia.solar.organization.Organization;
import com.youhujia.solar.organization.OrganizationDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrgQueryBO {

    @Autowired
    private OrgQueryContextFactory queryContextFactory;
    @Autowired
    private OrganizationDAO organizationDAO;
    @Autowired
    private DepartmentDAO departmentDAO;
    @Autowired
    private AreaDAO areaDAO;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public OrgQueryContext findAll() {

        List<Organization> organizations = organizationDAO.findByStatus(DepartmentStatusEnum.NORMAL.getStatus());

        OrgQueryContext queryContext = new OrgQueryContext();

        queryContext.setOrganizationList(organizations);

        return queryContext;
    }

    public OrgQueryContext getAllSellOrganization() {
        List<Organization> organizations = organizationDAO.findByVersionIsNotNullAndStatus(DepartmentStatusEnum.NORMAL.getStatus());

        OrgQueryContext queryContext = new OrgQueryContext();

        queryContext.setOrganizationList(organizations);

        return queryContext;
    }

    public OrgQueryContext getOrganizationById(Long organizationId) {

        OrgQueryContext queryContext = new OrgQueryContext();

        queryContext.setOrganization(organizationDAO.findOne(organizationId));

        return queryContext;
    }

    public OrgQueryContext getDepartmentsByOrganizationId(Long organizationId) {

        OrgQueryContext queryContext = new OrgQueryContext();

        List<Department> departmentList = departmentDAO.findByOrganizationIdAndGuestAndStatus(organizationId, 0L, DepartmentStatusEnum.NORMAL.getStatus());

        queryContext.setDepartmentList(departmentList);

        return queryContext;
    }

    public OrgQueryContext getAllDepartmentsByOrganizationId(Long organizationId) {

        OrgQueryContext queryContext = new OrgQueryContext();

        List<Department> departmentList = departmentDAO.findByOrganizationIdAndStatus(organizationId, DepartmentStatusEnum.NORMAL.getStatus());

        queryContext.setDepartmentList(departmentList);

        return queryContext;
    }

    public OrgQueryContext getDepartmentsByOrganizationIds(String organizationIds) {
        OrgQueryContext queryContext = queryContextFactory.buildDepartmentsQueryContext(organizationIds);

        List<Department> departmentList = departmentDAO.findByOrganizationIdInAndStatus(queryContext.getIds(), DepartmentStatusEnum.NORMAL.getStatus());

        queryContext.setDepartmentList(departmentList);

        return queryContext;
    }

    public OrgQueryContext getOrganizationListByAreaId(Long areaId) {

        OrgQueryContext queryContext = new OrgQueryContext();

        Area area = areaDAO.findOne(areaId);
        if (area == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "地理区域信息id有误!");
        }
        List<Organization> organizationList = organizationDAO.findByAreaIdAndStatus(areaId, OrganizationStatusEnum.NORMAL.getStatus());

        queryContext.setOrganizationList(organizationList);
        return queryContext;
    }

    public OrgQueryContext getAllWithoutDeleteOrgListByAreaId(Long adId, Integer draw, Integer length, Integer start) {

        OrgQueryContext queryContext = new OrgQueryContext();

        if (adId == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！地理区域id不能为空!");
        }
        Area area = areaDAO.findOne(adId);
        if (area == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！错误的地理区域id!");
        }
        if (length < 0 || start < 0) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！参数错误!");
        }
        List<Organization> organizationList = organizationDAO.findbyAreaIdWithStatus(adId, OrganizationStatusEnum.NORMAL.getStatus());

        queryContext.setOrganizationList(organizationList);
        queryContext.setDraw(draw);
        queryContext.setLength(length);
        queryContext.setStart(start);
        queryContext.setArea(area);

        return queryContext;
    }

    public OrgQueryContext findAllOrganizationAndDepartment(Map<String, String> map) {
        OrgQueryContext context = queryContextFactory.buildQueryContext(map);
        checkParams(context);
        Pageable pageable = new PageRequest(context.getIndex().intValue(), context.getSize().intValue(), Sort.Direction.DESC, "id");
        Page<Organization> organizationPage = organizationDAO.queryOrganizations(pageable);
        context.setOrganizationList(organizationPage.getContent());

        return context;
    }

    private void checkParams(OrgQueryContext context) {
        if (context.getIndex() == null || context.getIndex() < 0) {
            throw new YHJException(SolarExceptionCodeEnum.PARAM_ERROR, "Index is required and must >= 0");
        }

        if (context.getSize() == null || context.getSize() <= 0) {
            throw new YHJException(SolarExceptionCodeEnum.PARAM_ERROR, "Size is required and must > 0");
        }

    }

    public OrgQueryContext buildOrgQueryContext(String organizationIds) {
        OrgQueryContext queryContext = new OrgQueryContext();
        String[] strIds = organizationIds.split(",");
        List<Long> orgIdList = new ArrayList<>();
        if(strIds.length != 0){
            for (String id : strIds) {
                try {
                    orgIdList.add(Long.parseLong(id));
                }catch (Exception e){
                    logger.error(LogInfoGenerator.generateCallInfo("OrgQueryBO—>findOrganizationByIds", "error", "invalid id", "organizationIds", organizationIds));
                }
            }
        }
        queryContext.setOrganizationList(organizationDAO.findAll(orgIdList));

        return queryContext;
    }
}
