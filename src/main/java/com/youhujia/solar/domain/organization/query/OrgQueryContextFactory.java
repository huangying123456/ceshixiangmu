package com.youhujia.solar.domain.organization.query;

import com.youhujia.halo.solar.SolarOrganizationQueryEnum;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrgQueryContextFactory {

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
}
