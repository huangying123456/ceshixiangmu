package com.youhujia.solar.domain.organization.query;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.DepartmentStatusEnum;
import com.youhujia.halo.solar.OrganizationStatusEnum;
import com.youhujia.solar.domain.area.Area;
import com.youhujia.solar.domain.area.AreaDAO;
import com.youhujia.solar.domain.common.SolarExceptionCodeEnum;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import com.youhujia.solar.domain.organization.Organization;
import com.youhujia.solar.domain.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

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

    public OrgQueryContext findAll() {

        List<Organization> organizations = organizationDAO.findByStatus(DepartmentStatusEnum.NORMAL.getStatus());

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

        List<Department> departmentList = departmentDAO.findByOrganizationIdAndGuestAndStatus(organizationId, false, DepartmentStatusEnum.NORMAL.getStatus());

        queryContext.setDepartmentList(departmentList);

        return queryContext;
    }

    public OrgQueryContext getAllDepartmentsByOrganizationId(Long organizationId) {

        OrgQueryContext queryContext = new OrgQueryContext();

        List<Department> departmentList = departmentDAO.findByOrganizationIdAndStatus(organizationId, DepartmentStatusEnum.NORMAL.getStatus());

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
}
