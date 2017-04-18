package com.youhujia.solar.domain.organization;

import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentFactory;
import com.youhujia.solar.domain.organization.create.OrgCreateContext;
import com.youhujia.solar.domain.organization.query.OrgQueryContext;
import com.youhujia.solar.domain.organization.update.OrgUpdateContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrganizationDTOFactory {

    @Autowired
    private DepartmentFactory departmentFactory;

    public Solar.OrganizationDTO buildCreateDTO(OrgCreateContext context) {

        Organization organization = context.getOrganization();

        Solar.OrganizationDTO organizationDTO = toOrganizationDTO(organization);

        return organizationDTO;
    }

    public Solar.OrganizationListDTO buildOrganizationListDTO(OrgQueryContext context) {

        List<Organization> list = context.getOrganizationList();

        Solar.OrganizationListDTO organizationListDTO = toOrganizationListDTO(list);

        return organizationListDTO;
    }

    public Solar.OrganizationDTO buildOrganizationDTO(OrgQueryContext context) {

        Organization organization = context.getOrganization();

        Solar.OrganizationDTO organizationDTO = toOrganizationDTO(organization);

        return organizationDTO;
    }

    public Solar.DepartmentListDTO buildDepartmentListDTO(OrgQueryContext context) {

        List<Department> list = context.getDepartmentList();

        Solar.DepartmentListDTO listDTO = departmentFactory.toDepartmentListDTO(list);
        return listDTO;
    }

    public Solar.OrganizationDTO buildUpdateOrganizationDTO(OrgUpdateContext context) {

        Organization organization = context.getOrganization();

        Solar.OrganizationDTO organizationDTO = toOrganizationDTO(organization);

        return organizationDTO;
    }


    private Solar.OrganizationDTO toOrganizationDTO(Organization organization) {

        Solar.OrganizationDTO.Builder organizationDTOBuilder = Solar.OrganizationDTO.newBuilder();

        organizationDTOBuilder.setOrganization(buildOrganization(organization))
            .setResult(COMMON.Result.newBuilder().setCode(0).setSuccess(true).build());

        return organizationDTOBuilder.build();
    }

    private Solar.OrganizationListDTO toOrganizationListDTO(List<Organization> list) {

        Solar.OrganizationListDTO.Builder organizationListDTOBuilder = Solar.OrganizationListDTO.newBuilder();

        if (list.size() == 0) {
            return organizationListDTOBuilder.build();
        }
        list.stream().forEach(organization -> {
            organizationListDTOBuilder.addOrganization(buildOrganization(organization));
        });

        organizationListDTOBuilder.setResult(COMMON.Result.newBuilder().setCode(0).setSuccess(true).build());
        return organizationListDTOBuilder.build();
    }

    private Solar.Organization buildOrganization(Organization organization) {
        Solar.Organization.Builder builder = Solar.Organization.newBuilder();

        builder.setId(organization.getId())
            .setStatus(organization.getStatus());

        if (organization.getName() != null) {
            builder.setName(organization.getName());
        }
        if (organization.getAddress() != null) {
            builder.setAddress(organization.getAddress());
        }
        if (organization.getLat() != null) {
            builder.setLat(organization.getLat().toString());
        }
        if (organization.getLon() != null) {
            builder.setLon(organization.getLon().toString());
        }
        if (organization.getCreatedAt() != null) {
            builder.setCreatedAt(organization.getCreatedAt().getTime());
        }
        if (organization.getUpdatedAt() != null) {
            builder.setUpdatedAt(organization.getUpdatedAt().getTime());
        }
        return builder.build();
    }
}
