package com.youhujia.solar.domain.department.query;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import com.youhujia.solar.domain.department.create.DepCreateBO;
import com.youhujia.solar.domain.department.delete.DepDeleteContext;
import com.youhujia.solar.domain.organization.Organization;
import com.youhujia.solar.domain.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class DepQueryBO {

    @Autowired
    private DepartmentDAO departmentDAO;
    @Autowired
    private OrganizationDAO organizationDAO;
    @Autowired
    private DepCreateBO depCreateBO;

    public static String IS_CHECKED = "1";

    public static String IS_DELETED = "-1";

    public DepQueryContext getDepartmentById(Long departmentId) {

        DepQueryContext context = new DepQueryContext();

        if (departmentId == null || departmentId < 0) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！科室id为空或者非法!！");
        }
        Department department = departmentDAO.findOne(departmentId);
        if (department == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！错误的科室id！");
        }
        context.setDepartment(department);
        return context;

    }

    public DepQueryContext getDepartmentByNo(String departmentNo) {

        DepQueryContext context = new DepQueryContext();

        List<Department> list = departmentDAO.findByNumberAndStatus(departmentNo, new Byte(IS_CHECKED));

        List<Department> rstList = new ArrayList<>();
        for (Department dpt : list) {
            if (dpt.getGuest()) {
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

        //------------------感觉此处用findOne有点诡异，待确认------
        Department department = departmentDAO.findOne(departmentId);

        if (department == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "there is not found guest department by host departmentId, departmentId:" + departmentId);
        }

        if (department.getGuest()) {
            context.setDepartment(department);
        } else {
            context.setDepartment(departmentDAO.save(depCreateBO.createOrGetGuestDepartment(department)));
        }
        context.setDepartment(department);

        return context;
    }

    public DepQueryContext getDepartmentListByOrgId(Long orgId) {

        DepQueryContext context = new DepQueryContext();

        Organization organization = organizationDAO.findOne(orgId);
        if (organization == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误的医院id");
        }

        List<Department> departments = departmentDAO.findByOrganizationIdAndStatus(orgId, new Byte(IS_CHECKED));

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
        List<Department> departments = departmentDAO.findByOrganizationIdWithStatus(orgId, new Byte(IS_DELETED));

        context.setDepartmentList(departments);
        context.setOrganization(organization);

        return context;
    }

    public DepQueryContext getDepartmentListByIds(String departmentIds) {
        DepQueryContext context = new DepQueryContext();
        String[] ids = departmentIds.split(",");
        if (ids.length == 0) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "参数为空");
        }
        List<Department> departments = new ArrayList<>();
        for (String departmentId : ids) {
            Department department = departmentDAO.findOne(Long.parseLong(departmentId));
            if(department != null){
                departments.add(department);
            }
        }
        context.setDepartmentList(departments);

        return context;
    }
}
