package com.youhujia.solar.domain.organization.query;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.solar.domain.area.Area;
import com.youhujia.solar.domain.area.AreaDAO;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import com.youhujia.solar.domain.organization.Organization;
import com.youhujia.solar.domain.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public static String IS_CHECKED = "1";

    public static String IS_NOT_CHECKED = "0";

    public static String IS_DELETED = "-1";

    public OrgQueryContext findAll() {

        List<Organization> organizations = organizationDAO.findByStatus(new Byte(IS_CHECKED));

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

        List<Department> departmentList = departmentDAO.findByOrganizationIdAndGuestAndStatus(organizationId, false, new Byte(IS_CHECKED));

        queryContext.setDepartmentList(departmentList);

        return queryContext;
    }

    public OrgQueryContext getAllDepartmentsByOrganizationId(Long organizationId) {

        OrgQueryContext queryContext = new OrgQueryContext();

        List<Department> departmentList = departmentDAO.findByOrganizationIdAndStatus(organizationId, new Byte(IS_CHECKED));

        queryContext.setDepartmentList(departmentList);

        return queryContext;
    }

    public OrgQueryContext getOrganizationListByAreaId(Long areaId) {

        OrgQueryContext queryContext = new OrgQueryContext();

        Area area = areaDAO.findOne(areaId);
        if (area == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "地理区域信息id有误!");
        }
        List<Organization> organizationList = organizationDAO.findByAreaIdAndStatus(areaId, new Byte("1"));

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
        List<Organization> organizationList = organizationDAO.findbyAreaIdWithStatus(adId, new Byte(IS_DELETED));

        queryContext.setOrganizationList(organizationList);
        queryContext.setDraw(draw);
        queryContext.setLength(length);
        queryContext.setStart(start);
        queryContext.setArea(area);

        return queryContext;
    }

}
