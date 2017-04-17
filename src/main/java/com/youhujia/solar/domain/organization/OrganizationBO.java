package com.youhujia.solar.domain.organization;

import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.solar.Solar;
import com.youhujia.halo.yolar.Yolar;
import com.youhujia.solar.domain.department.create.CreateBO;
import com.youhujia.solar.domain.department.create.CreateContext;
import com.youhujia.solar.domain.department.query.QueryBO;
import com.youhujia.solar.domain.department.query.QueryContext;
import com.youhujia.solar.domain.department.update.UpdateBO;
import com.youhujia.solar.domain.department.update.UpdateContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrganizationBO {

    @Autowired
    private OrganizationContextFactory organizationContextFactory;

    @Autowired
    private CreateBO createBO;

    @Autowired
    private UpdateBO updateBO;

    @Autowired
    private QueryBO queryBO;

    public Solar.OrganizationDTO create(Solar.OrganizationCreateOption option) {

        CreateContext createContext = createBO.create(option);

        Solar.OrganizationDTO organizationDTO = organizationContextFactory.buildCreateDTO(createContext);

        return organizationDTO;
    }

    public Solar.OrganizationListDTO findAll() {

        QueryContext queryContext = queryBO.findAll();

        Solar.OrganizationListDTO organizationListDTO = organizationContextFactory.buildFindAllDTO(queryContext);

        return organizationListDTO;
    }

    public Solar.OrganizationDTO getOrganizationById(Long organizationId) {

        QueryContext queryContext = queryBO.getOrganizationById(organizationId);

        Solar.OrganizationDTO organizationDTO = organizationContextFactory.buildGetrganizationById(queryContext);

        return organizationDTO;
    }

    public Solar.DepartmentListDTO getDepartmentsByOrganizationId(Long organizationId) {

        QueryContext queryContext = queryBO.getDepartmentsByOrganizationId(organizationId);

        Solar.DepartmentListDTO departmentListDTO= organizationContextFactory.buildGetDepartmentsByOrganizationId(queryContext);

        return departmentListDTO;
    }

    public Solar.DepartmentListDTO getAllDepartmentsByOrganizationId(Long organizationId) {

        QueryContext queryContext = queryBO.getAllDepartmentsByOrganizationId(organizationId);

        Solar.DepartmentListDTO departmentListDTO = organizationContextFactory.buildGetAllDepartmentsByOrganizationId(queryContext);

        return departmentListDTO;
    }

    public Solar.OrganizationDTO updateOrganization(Solar.OrganizationUpdateOption option) {

        UpdateContext updateContext = updateBO.updateOrganization(option);

        Solar.OrganizationDTO organizationDTO= organizationContextFactory.buildUpdateOrganization(updateContext);

        return organizationDTO;
    }


    // TODO: 2017/4/17  code for improve department admin


}