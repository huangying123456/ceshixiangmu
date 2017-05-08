package com.youhujia.solar.domain.organization.create;

import com.youhujia.solar.domain.organization.Organization;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrgCreateContext {
    private Organization organization;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
