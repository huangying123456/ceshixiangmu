package com.youhujia.solar.domain.organization;

import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.organization.create.CreateBO;
import com.youhujia.solar.domain.organization.create.CreateContext;
import com.youhujia.solar.domain.organization.query.QueryBO;
import com.youhujia.solar.domain.organization.query.QueryContext;
import com.youhujia.solar.domain.organization.update.UpdateBO;
import com.youhujia.solar.domain.organization.update.UpdateContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrganizationBO {

    @Autowired
    private OrganizationDTOFactory organizationDTOFactory;

    @Autowired
    private CreateBO createBO;

    @Autowired
    private UpdateBO updateBO;

    @Autowired
    private QueryBO queryBO;

    public Solar.OrganizationDTO create(Solar.OrganizationCreateOption option) {

        CreateContext createContext = createBO.create(option);

        Solar.OrganizationDTO organizationDTO = organizationDTOFactory.buildCreateDTO(createContext);

        return organizationDTO;
    }

    public Solar.OrganizationListDTO findAll() {

        QueryContext queryContext = queryBO.findAll();

        Solar.OrganizationListDTO organizationListDTO = organizationDTOFactory.buildOrganizationListDTO(queryContext);

        return organizationListDTO;
    }

    public Solar.OrganizationDTO getOrganizationById(Long organizationId) {

        QueryContext queryContext = queryBO.getOrganizationById(organizationId);

        Solar.OrganizationDTO organizationDTO = organizationDTOFactory.buildOrganizationDTO(queryContext);

        return organizationDTO;
    }

    public Solar.DepartmentListDTO getDepartmentsByOrganizationId(Long organizationId) {

        QueryContext queryContext = queryBO.getDepartmentsByOrganizationId(organizationId);

        Solar.DepartmentListDTO departmentListDTO= organizationDTOFactory.buildDepartmentListDTO(queryContext);

        return departmentListDTO;
    }

    public Solar.DepartmentListDTO getAllDepartmentsByOrganizationId(Long organizationId) {

        QueryContext queryContext = queryBO.getAllDepartmentsByOrganizationId(organizationId);

        Solar.DepartmentListDTO departmentListDTO = organizationDTOFactory.buildDepartmentListDTO(queryContext);

        return departmentListDTO;
    }

    public Solar.OrganizationDTO updateOrganization(Solar.OrganizationUpdateOption option) {

        UpdateContext updateContext = updateBO.updateOrganization(option);

        Solar.OrganizationDTO organizationDTO= organizationDTOFactory.buildUpdateOrganizationDTO(updateContext);

        return organizationDTO;
    }


    // TODO: 2017/4/17  code for improve department admin


}