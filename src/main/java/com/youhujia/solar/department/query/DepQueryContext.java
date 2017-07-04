package com.youhujia.solar.department.query;

import com.youhujia.halo.solar.DepartmentStatusEnum;
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

    private List<Long> departmentIdsList = new ArrayList<>();
    private List<Long> organizationIdsList = new ArrayList<>();
    private List<DepartmentStatusEnum> departmentStatusEnumList = new ArrayList<>();
    private List<Department> departmentListWithoutStatus;

    public List<Department> getDepartmentListWithoutStatus() {
        return departmentListWithoutStatus;
    }

    public void setDepartmentListWithoutStatus(List<Department> departmentListWithoutStatus) {
        this.departmentListWithoutStatus = departmentListWithoutStatus;
    }

    public List<DepartmentStatusEnum> getDepartmentStatusEnumList() {
        return departmentStatusEnumList;
    }

    public void setDepartmentStatusEnumList(List<DepartmentStatusEnum> departmentStatusEnumList) {
        this.departmentStatusEnumList = departmentStatusEnumList;
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
