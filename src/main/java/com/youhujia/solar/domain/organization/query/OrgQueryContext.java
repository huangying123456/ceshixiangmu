package com.youhujia.solar.domain.organization.query;

import com.youhujia.solar.domain.area.Area;
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
    private Area area;
    private Integer draw;
    private Integer length;
    private Integer start;

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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
