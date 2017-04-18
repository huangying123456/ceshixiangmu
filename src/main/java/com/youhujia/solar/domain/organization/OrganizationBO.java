package com.youhujia.solar.domain.organization;

import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.organization.create.OrgCreateBO;
import com.youhujia.solar.domain.organization.create.OrgCreateContext;
import com.youhujia.solar.domain.organization.delete.OrgDeleteBO;
import com.youhujia.solar.domain.organization.delete.OrgDeleteContext;
import com.youhujia.solar.domain.organization.query.OrgQueryBO;
import com.youhujia.solar.domain.organization.query.OrgQueryContext;
import com.youhujia.solar.domain.organization.update.OrgUpdateBO;
import com.youhujia.solar.domain.organization.update.OrgUpdateContext;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrganizationBO {

    @Autowired
    private OrganizationDTOFactory organizationDTOFactory;

    @Autowired
    private OrgCreateBO createBO;

    @Autowired
    private OrgUpdateBO updateBO;

    @Autowired
    private OrgQueryBO queryBO;

    @Autowired
    private OrgDeleteBO orgDeleteBO;

    public Solar.OrganizationDTO create(Solar.OrganizationCreateOption option) {

        OrgCreateContext createContext = createBO.create(option);

        Solar.OrganizationDTO organizationDTO = organizationDTOFactory.buildCreateDTO(createContext);

        return organizationDTO;
    }

    public Solar.OrganizationListDTO findAll() {

        OrgQueryContext queryContext = queryBO.findAll();

        Solar.OrganizationListDTO organizationListDTO = organizationDTOFactory.buildOrganizationListDTO(queryContext);

        return organizationListDTO;
    }

    public Solar.OrganizationDTO getOrganizationById(Long organizationId) {

        OrgQueryContext queryContext = queryBO.getOrganizationById(organizationId);

        Solar.OrganizationDTO organizationDTO = organizationDTOFactory.buildOrganizationDTO(queryContext);

        return organizationDTO;
    }

    public Solar.DepartmentListDTO getDepartmentsByOrganizationId(Long organizationId) {

        OrgQueryContext queryContext = queryBO.getDepartmentsByOrganizationId(organizationId);

        Solar.DepartmentListDTO departmentListDTO = organizationDTOFactory.buildDepartmentListDTO(queryContext);

        return departmentListDTO;
    }

    public Solar.DepartmentListDTO getAllDepartmentsByOrganizationId(Long organizationId) {

        OrgQueryContext queryContext = queryBO.getAllDepartmentsByOrganizationId(organizationId);

        Solar.DepartmentListDTO departmentListDTO = organizationDTOFactory.buildDepartmentListDTO(queryContext);

        return departmentListDTO;
    }

    public Solar.OrganizationDTO updateOrganization(Solar.OrganizationUpdateOption option) {

        OrgUpdateContext updateContext = updateBO.updateOrganization(option);

        Solar.OrganizationDTO organizationDTO = organizationDTOFactory.buildUpdateOrganizationDTO(updateContext);

        return organizationDTO;
    }

    public Solar.LBSOrganizationDTO getOrganizationListByAreaId(Long areaId) {

        OrgQueryContext queryContext = queryBO.getOrganizationListByAreaId(areaId);

        return organizationDTOFactory.buildGetOrganizationListByAreaIdDTO(queryContext);
    }

    public Solar.ManagerOrganizationListDTO getAllWithoutDeleteOrgListByAreaId(Long adId, Integer draw, Integer length, Integer start) {

        OrgQueryContext queryContext = queryBO.getAllWithoutDeleteOrgListByAreaId(adId,draw,length,start);

        return organizationDTOFactory.buildGetAllWithoutDeleteOrgListByAreaIdDTO(queryContext);
    }

    public Solar.ManagerOrganizationDTO markDeleteOrganizationById(Long orgId){

        OrgDeleteContext context = orgDeleteBO.markDeleteOrganizationById(orgId);

        return organizationDTOFactory.buildMarkDeleteOrganizationByIdDTO(context);
    }
}