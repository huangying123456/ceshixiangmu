package com.youhujia.solar.domain.organization.query;

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
}
