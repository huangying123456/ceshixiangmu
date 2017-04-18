package com.youhujia.solar.domain.department.query;

import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.organization.Organization;

import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
public class DepQueryContext {

    private Department department;
    private List<Department> departmentList;
    private Organization organization;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
