package com.youhujia.solar.domain.organization.query;

import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class QueryBO {

    @Autowired
    private QueryContextFactory queryContextFactory;

    public QueryContext findAll() {

        // TODO: 2017/4/17
        return null;
    }

    public QueryContext getOrganizationById(Long organizationId) {

        // TODO: 2017/4/17
        return null;
    }

    public QueryContext getDepartmentsByOrganizationId(Long organizationId) {


        // TODO: 2017/4/17
        return null;
    }

    public QueryContext getAllDepartmentsByOrganizationId(Long organizationId) {

        // TODO: 2017/4/17
        return null;
    }
}
