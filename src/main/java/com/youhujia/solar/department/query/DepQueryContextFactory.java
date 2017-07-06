package com.youhujia.solar.department.query;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.DepartmentStatusEnum;
import com.youhujia.halo.solar.SolarDepartmentQueryEnum;
import com.youhujia.solar.common.SolarExceptionCodeEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class DepQueryContextFactory {


    public DepQueryContext buildQueryDepartmentContext(Map<String, String> map) {

        DepQueryContext queryContext = new DepQueryContext();

        List<Long> departmentIds = new ArrayList<>();
        List<Long> organizationIds = new ArrayList<>();
        List<DepartmentStatusEnum> statusList = new ArrayList<>();

        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                SolarDepartmentQueryEnum param = SolarDepartmentQueryEnum.getByName(entry.getKey());
                String[] entries = entry.getValue().split(",");
                switch (param) {
                    case STATUS:
                        Arrays.stream(entries).forEach(status -> statusList.add(DepartmentStatusEnum.getByStatus(Integer.valueOf(status))));
                        break;
                    case DEPARTMENT_IDS:
                        Arrays.stream(entries).forEach(deptId -> departmentIds.add(Long.valueOf(deptId)));
                        break;
                    case ORGANIZATION_IDS:
                        Arrays.stream(entries).forEach(orgId -> organizationIds.add(Long.valueOf(orgId)));
                        break;
                    default:
                        break;
                }
            }

            queryContext.setDepartmentIdsList(departmentIds);
            queryContext.setOrganizationIdsList(organizationIds);
            queryContext.setDepartmentStatusEnumList(statusList);
        } catch (Exception e) {
            throw new YHJException(SolarExceptionCodeEnum.PARAM_ERROR);
        }

        checkQueryParam(queryContext);

        return queryContext;
    }

    private void checkQueryParam(DepQueryContext context) {
        if (context.getDepartmentIdsList().size() < 1) {
            if (context.getOrganizationIdsList().size() < 1) {
                throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "organizationIds departmentIds不能全为空！");
            }
        }
    }
}
