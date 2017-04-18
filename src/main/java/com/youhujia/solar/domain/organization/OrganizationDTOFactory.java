package com.youhujia.solar.domain.organization;

import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.organization.query.OrgQueryContext;
import com.youhujia.solar.domain.organization.create.OrgCreateContext;
import com.youhujia.solar.domain.organization.update.OrgUpdateContext;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrganizationDTOFactory {

    public Solar.OrganizationDTO buildCreateDTO(OrgCreateContext createContext) {
        // TODO: 2017/4/17
        return null;
    }

    public Solar.OrganizationListDTO buildFindAllDTO(OrgQueryContext context) {

        // TODO: 2017/4/17
        return null;
    }

    public Solar.OrganizationListDTO buildOrganizationListDTO(OrgQueryContext context) {

        // TODO: 2017/4/17
        return null;
    }

    public Solar.OrganizationDTO buildOrganizationDTO(OrgQueryContext context) {

        // TODO: 2017/4/17
        return null;
    }

    public Solar.DepartmentListDTO buildDepartmentListDTO(OrgQueryContext context) {

        // TODO: 2017/4/17
        return null;
    }

    public Solar.OrganizationDTO buildUpdateOrganizationDTO(OrgUpdateContext context) {

        // TODO: 2017/4/17
        return null;
    }
}
