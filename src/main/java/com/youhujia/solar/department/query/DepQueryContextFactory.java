package com.youhujia.solar.department.query;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.solar.department.Department;
import com.youhujia.solar.department.DepartmentDAO;
import com.youhujia.solar.organization.Organization;
import com.youhujia.solar.organization.OrganizationDAO;
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

    @Resource
    private OrganizationDAO organizationDAO;

    public DepQueryContext buildQueryDepartmentContext(Map<String, String> map) {

        DepQueryContext queryContext = new DepQueryContext();

        String departmentIds = null;
        String organizationIds = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            switch (key) {
                case "status":
                    queryContext.setStatus(new Long(entry.getValue()));
                    break;
                case "departmentIds":
                    departmentIds = entry.getValue();
                    break;
                case "organizationIds":
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

        checkQueryParam(queryContext);

        computeDepartmentList(queryContext);

        return queryContext;
    }

    private void checkQueryParam(DepQueryContext context) {
        if (context.getStatus() == null) {
            if (context.getDepartmentIdsList() == null || context.getDepartmentIdsList().size() < 1) {
                if (context.getOrganizationIdsList() == null || context.getOrganizationIdsList().size() < 1) {
                    throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "查询参数不能全为空！");
                }
            }
        }

        if (context.getStatus() != null) {
            if (context.getStatus().longValue() != 1 && context.getStatus().longValue() != 0) {
                throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "状态非法");
            }
        }


        if (context.getOrganizationIdsList() != null && context.getOrganizationIdsList().size() > 0) {
            for (Long id : context.getOrganizationIdsList()) {
                Organization organization = organizationDAO.findOne(id);
                if (organization == null || organization.getId() == 0) {
                    throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "参数organization id 非法！");
                }
            }
        }


        if (context.getDepartmentIdsList() != null && context.getDepartmentIdsList().size() > 0) {
            for (Long id : context.getDepartmentIdsList()) {
                Department department = departmentDAO.findOne(id);
                if (department == null || department.getId() == 0) {
                    throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "参数department id 非法！");
                }
            }
        }

    }



    private DepQueryContext computeDepartmentList(DepQueryContext context) {

        List<Department> departments = new ArrayList<>();

        List<Department> departmentList = new ArrayList<>();

        List<Long> organizationIds = computeOrganizationWithStatusIds(context);

        List<Long> departmentIds = context.getDepartmentIdsList();

        //分三种情况：1.department和organization都存在。2.department存在，organization不存在。3.organization存在，department不存在
        if (departmentIds != null && departmentIds.size() > 0) {
            for (Long id : departmentIds) {
                Department department = departmentDAO.findOne(id);
                if (organizationIds != null && organizationIds.size() > 0) {
                    if (organizationIds.contains(department.getOrganizationId())) {
                        departmentList.add(department);
                    }
                } else {
                    departmentList.add(department);
                }
            }

        } else {
            for (Long l : organizationIds) {
                departmentList = departmentDAO.findByOrganizationId(l);
                if (departmentList != null || departmentList.size() > 0) {
                    for (Department department : departmentList) {
                        departments.add(department);
                    }
                }
            }
        }
        context.setDepartmentList(departments);
        return context;
    }

    private List<Long> computeOrganizationWithStatusIds(DepQueryContext context) {

        List<Long> organizationWithStatusIds = new ArrayList<>();
        if (context.getStatus() != null && context.getOrganizationIdsList()!=null) {
            for (Long orgId : context.getOrganizationIdsList()) {
                Organization organization = organizationDAO.findOne(orgId);
                if (organization.getStatus().longValue() == context.getStatus().longValue()) {
                    organizationWithStatusIds.add(organization.getId());
                }
            }
            return organizationWithStatusIds;
        } else {
            return context.getOrganizationIdsList();
        }


    }
}
