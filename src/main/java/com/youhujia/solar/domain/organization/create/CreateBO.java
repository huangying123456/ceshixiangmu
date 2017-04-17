package com.youhujia.solar.domain.organization.create;

import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class CreateBO {

    @Autowired
    private CreateContextFactory createContextFactory;

    @Autowired
    private OrganizationDAO organizationDAO;

    public CreateContext create(Solar.OrganizationCreateOption option) {

        // TODO: 2017/4/17
        return null;
    }


}
