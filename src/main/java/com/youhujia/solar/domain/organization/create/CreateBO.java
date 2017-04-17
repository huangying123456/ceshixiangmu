package com.youhujia.solar.domain.organization.create;

import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.organization.Organization;
import com.youhujia.solar.domain.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class CreateBO {

    @Autowired
    private CreateContextFactory createContextFactory;

    @Autowired
    private OrganizationDAO organizationDAO;

    public Solar.OrganizationDTO create(Solar.OrganizationCreateOption option) {

//        checkCreateOption(option);
        toOrganization(option);
        com.youhujia.solar.domain.department.create.CreateContext createContext = createBO.create(option);

        Solar.OrganizationDTO organizationDTO = organizationContextFactory.buildCreateDTO(createContext);

        return organizationDTO;
    }

    private Organization toOrganization(Solar.OrganizationCreateOption option) {
        Organization organization = new Organization();


        if (option.hasName()) {
            organization.setName(option.getName());
        }
        if (option.hasAddress()) {
            organization.setAddress(option.getAddress());
        }
        if (option.hasStatus()) {
            organization.setStatus((byte) option.getStatus());
        }
        if (option.hasLat()) {
            organization.setLat(new BigDecimal(option.getLat()));
        }
        if (option.hasLon()) {
            organization.setLon(new BigDecimal(option.getLon()));
        }

        return organization;
    }


}
