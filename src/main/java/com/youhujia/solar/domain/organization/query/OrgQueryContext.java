package com.youhujia.solar.domain.organization.query;

import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.organization.Organization;

import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
public class OrgQueryContext {
    private Organization organization;
    private List<Organization> organizationList;
    private List<Department> departmentList;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
}
