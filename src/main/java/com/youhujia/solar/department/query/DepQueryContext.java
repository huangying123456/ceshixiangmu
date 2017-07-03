package com.youhujia.solar.department.query;

import com.youhujia.solar.department.Department;
import com.youhujia.solar.organization.Organization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
public class DepQueryContext {

    private Department department;
    private List<Department> departmentList;
    private Organization organization;

    private Long status;
    private List<Long> departmentIdsList = new ArrayList<>();
    private List<Long> organizationIdsList = new ArrayList<>();
    private List<Long> organizationsWithStatus;

    public List<Long> getOrganizationsWithStatus() {
        return organizationsWithStatus;
    }

    public void setOrganizationsWithStatus(List<Long> organizationsWithStatus) {
        this.organizationsWithStatus = organizationsWithStatus;
    }

    public List<Long> getDepartmentIdsList() {
        return departmentIdsList;
    }

    public void setDepartmentIdsList(List<Long> departmentIdsList) {
        this.departmentIdsList = departmentIdsList;
    }

    public List<Long> getOrganizationIdsList() {
        return organizationIdsList;
    }

    public void setOrganizationIdsList(List<Long> organizationIdsList) {
        this.organizationIdsList = organizationIdsList;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

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
