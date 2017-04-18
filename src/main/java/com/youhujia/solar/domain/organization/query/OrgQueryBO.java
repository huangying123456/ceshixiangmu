package com.youhujia.solar.domain.organization.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrgQueryBO {

    @Autowired
    private OrgQueryContextFactory queryContextFactory;

    public OrgQueryContext findAll() {

        // TODO: 2017/4/17
        return null;
    }

    public OrgQueryContext getOrganizationById(Long organizationId) {

        // TODO: 2017/4/17
        return null;
    }

    public OrgQueryContext getDepartmentsByOrganizationId(Long organizationId) {


        // TODO: 2017/4/17
        return null;
    }

    public OrgQueryContext getAllDepartmentsByOrganizationId(Long organizationId) {

        // TODO: 2017/4/17
        return null;
    }
}
