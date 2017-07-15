package com.youhujia.solar.organization.query;

import com.youhujia.halo.solar.SolarOrganizationQueryEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrgQueryContextFactory {
    private Logger logger  = LoggerFactory.getLogger(getClass());

    public OrgQueryContext buildQueryContext(Map<String, String> map) {

        OrgQueryContext context = new OrgQueryContext();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            SolarOrganizationQueryEnum param = SolarOrganizationQueryEnum.getByName(entry.getKey());
            String value = entry.getValue();
            switch (param) {
                case INDEX:
                    context.setIndex(Long.valueOf(value));
                    break;
                case SIZE:
                    context.setSize(Long.valueOf(value));
                    break;
                default:
                    break;
            }
        }
        return context;
    }

    public OrgQueryContext buildDepartmentsQueryContext(String organizationIds) {
        OrgQueryContext context = new OrgQueryContext();
        List<Long> list = new ArrayList<>();


        String[] idArr = organizationIds.split(",");
        if (idArr.length <= 0){
            context.setIds(list);
            return context;
        }

        for (String idStr : idArr){
            try {
                Long adminId = Long.parseLong(idStr);
                list.add(adminId);
            } catch (NumberFormatException e) {
                logger.error("getDepartmentsByOrganizationIds, organizationIds not is number,organizationIds is:" + organizationIds);
            }
        }
        context.setIds(list);
        return context;
    }
}
