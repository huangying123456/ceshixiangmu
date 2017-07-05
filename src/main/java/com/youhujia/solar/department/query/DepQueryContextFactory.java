package com.youhujia.solar.department.query;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.DepartmentStatusEnum;
import com.youhujia.halo.solar.SolarDepartmentQueryEnum;
import com.youhujia.solar.common.SolarExceptionCodeEnum;
import com.youhujia.solar.department.Department;
import com.youhujia.solar.department.DepartmentDAO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class DepQueryContextFactory {

    @Resource
    private DepartmentDAO departmentDAO;


    public DepQueryContext buildQueryDepartmentContext(Map<String, String> map) {

        DepQueryContext queryContext = new DepQueryContext();

        String departmentIds = null;
        String organizationIds = null;
        String status = null;

        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                SolarDepartmentQueryEnum param = SolarDepartmentQueryEnum.getByName(entry.getKey());
                switch (param) {
                    case STATUS:
                        status = (entry.getValue());
                        break;
                    case DEPARTMENT_IDS:
                        departmentIds = entry.getValue();
                        break;
                    case ORGANIZATION_IDS:
                        organizationIds = entry.getValue();
                        break;
                    default:
                        break;
                }
            }

            if (departmentIds != null) {
                String[] departmentStrArr = departmentIds.split(",");
                List<Long> departmentList = new ArrayList<>();
                for (String s : departmentStrArr) {
                    departmentList.add(new Long(s));
                }
                queryContext.setDepartmentIdsList(departmentList);
            }

            if (organizationIds != null) {
                String[] organizationStrArr = organizationIds.split(",");
                List<Long> organizationList = new ArrayList<>();
                for (String s : organizationStrArr) {
                    organizationList.add(new Long(s));
                }
                queryContext.setOrganizationIdsList(organizationList);
            }
            if (status != null) {
                String[] statusStrArr = status.split(",");
                List<DepartmentStatusEnum> statusList = new ArrayList<>();
                for (String s : statusStrArr) {
                    statusList.add(DepartmentStatusEnum.getByStatus(Long.valueOf(s)));
                }
                queryContext.setDepartmentStatusEnumList(statusList);
            }
        } catch (Exception e) {
            throw new YHJException(SolarExceptionCodeEnum.PARAM_ERROR);
        }

        checkQueryParam(queryContext);

        computeDepartmentList(queryContext);

        return queryContext;
    }

    private void checkQueryParam(DepQueryContext context) {
        if (context.getDepartmentIdsList() == null || context.getDepartmentIdsList().size() < 1) {
            if (context.getOrganizationIdsList() == null || context.getOrganizationIdsList().size() < 1) {
                throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "organizationIds departmentIds不能全为空！");
            }
        }
    }

    private DepQueryContext computeDepartmentList(DepQueryContext context) {

        /**
         * 1. dpt ids size > 0, 取出所有科室
         * 2. org ids size > 0, 第一步结果过滤 orgIds
         * 3. 上一步结果过滤 status
         */
        List<Department> departments;

        List<Long> organizationIds = context.getOrganizationIdsList();

        List<Long> departmentIds = context.getDepartmentIdsList();

        if (departmentIds != null && departmentIds.size() > 0) {
            if (organizationIds != null && organizationIds.size() > 0) {
                departments = computeDeptsByOrgIdsAndDeptIds(organizationIds, departmentIds);
            } else {
                departments = computeDeptsByDeptIds(departmentIds);
            }
        } else {
            departments = computeDepartmentsByOrgIds(organizationIds);
        }
        context.setDepartmentListWithoutStatus(departments);

        context.setDepartmentList(computeDepartmentWithStatus(context));

        return context;
    }

    private List<Department> computeDepartmentsByOrgIds(List<Long> orgIds) {

        List<Department> departments = new ArrayList<>();

        List<Department> DepartmentsByOrgIds;

        for (Long l : orgIds) {
            DepartmentsByOrgIds = departmentDAO.findByOrganizationId(l);
            if (DepartmentsByOrgIds != null || DepartmentsByOrgIds.size() > 0) {
                for (Department department : DepartmentsByOrgIds) {
                    departments.add(department);
                }
            }
        }
        return departments;
    }

    private List<Department> computeDeptsByOrgIdsAndDeptIds(List<Long> orgIds, List<Long> deptIds) {

        List<Department> departments = new ArrayList<>();
        for (Long id : deptIds) {
            Department department = departmentDAO.findOne(id);
            if (orgIds.contains(department.getOrganizationId())) {
                departments.add(department);
            }
        }
        return departments;
    }

    private List<Department> computeDeptsByDeptIds(List<Long> deptIds) {

        List<Department> departments = new ArrayList<>();
        for (Long id : deptIds) {
            Department department = departmentDAO.findOne(id);
            departments.add(department);
        }
        return departments;
    }

    private List<Department> computeDepartmentWithStatus(DepQueryContext context) {

        List<Department> departments = new ArrayList<>();

        if (context.getDepartmentStatusEnumList() != null && context.getDepartmentStatusEnumList().size() > 0) {
            for (Department department : context.getDepartmentListWithoutStatus()) {
                //如果部门的status是enum中的一个
                if (context.getDepartmentStatusEnumList().contains(DepartmentStatusEnum.getByStatus(Long.valueOf(department.getStatus())))) {

                    departments.add(department);
                }
            }
        } else {
            departments = context.getDepartmentListWithoutStatus();
        }

        return departments;

    }


}
