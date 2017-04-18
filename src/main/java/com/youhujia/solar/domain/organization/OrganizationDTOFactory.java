package com.youhujia.solar.domain.organization;

import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.area.Area;
import com.youhujia.solar.domain.area.AreaDAO;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentFactory;
import com.youhujia.solar.domain.organization.create.OrgCreateContext;
import com.youhujia.solar.domain.organization.delete.OrgDeleteContext;
import com.youhujia.solar.domain.organization.query.OrgQueryContext;
import com.youhujia.solar.domain.organization.update.OrgUpdateContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrganizationDTOFactory {

    @Autowired
    private DepartmentFactory departmentFactory;

    @Autowired
    private AreaDAO areaDAO;

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

    public Solar.LBSOrganizationDTO buildGetOrganizationListByAreaIdDTO(OrgQueryContext context) {

        Solar.LBSOrganizationDTO.Builder orgBuilder = Solar.LBSOrganizationDTO.newBuilder();
        COMMON.Result.Builder resultBuilder = COMMON.Result.newBuilder();

        Solar.LBSOrganizationDataDTO.Builder dataBuilder = Solar.LBSOrganizationDataDTO.newBuilder();
        List<Organization> list = context.getOrganizationList();
        for (Organization o : list) {
            Solar.OrganizationInfo.Builder infoBuilder = Solar.OrganizationInfo.newBuilder();
            infoBuilder.setId(o.getId());
            infoBuilder.setName(o.getName());
            dataBuilder.addInfos(infoBuilder);
        }
        return orgBuilder.setData(dataBuilder).setResult(resultBuilder.setCode(0).setSuccess(true).setMsg("success")).build();

    }

    public Solar.ManagerOrganizationListDTO buildGetAllWithoutDeleteOrgListByAreaIdDTO(OrgQueryContext context) {

        Solar.ManagerOrganizationListDTO.Builder listBuilder = Solar.ManagerOrganizationListDTO.newBuilder();
        Solar.ManagerOrganizationListDataDTO.Builder dataBuilder = Solar.ManagerOrganizationListDataDTO.newBuilder();
        COMMON.Result.Builder resultBuilder = COMMON.Result.newBuilder();

        List<Organization> organizationList = context.getOrganizationList();
        Integer draw = context.getDraw();
        Integer length = context.getLength();
        Integer start = context.getStart();
        Area area = context.getArea();

        Collections.sort(organizationList, new Comparator<Organization>() {
            @Override
            public int compare(Organization o1, Organization o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        if (start > organizationList.size()) {
            return listBuilder.setResult(resultBuilder.setSuccess(false).setCode(-1).setDisplaymsg("错误！参数错误！")).build();
        }

        listBuilder.setDraw(draw);

        if (start + length < organizationList.size()) {
            listBuilder.setRecordsFiltered(length);
        } else {
            listBuilder.setRecordsFiltered(organizationList.size() - start);
        }
        listBuilder.setRecordsTotal(organizationList.size());

        String adName = area.getName();
        area = areaDAO.findOne(area.getParentId());
        String cName = area.getName();
        area = areaDAO.findOne(area.getParentId());
        String pName = area.getName();
        for (int i = start; i < organizationList.size() && i < length; i++) {
            Organization o = organizationList.get(i);
            Solar.ManagerOrganization.Builder builder = Solar.ManagerOrganization.newBuilder();
            builder.setId(o.getId());
            builder.setName(o.getName());
            builder.setStatus(o.getStatus());
            if (o.getLevel() != null) {
                builder.setLevel(o.getLevel());
            }
            if (o.getLat() != null) {
                builder.setLat(o.getLat().doubleValue());
            }
            if (o.getLon() != null) {
                builder.setLon(o.getLon().doubleValue());
            }
            if (o.getAddress() != null) {
                builder.setAddress(o.getAddress());
            }
            if (o.getCreatedAt() != null) {
                builder.setCreatedAt(o.getCreatedAt().getTime());
            }
            builder.setAd(adName);
            builder.setCity(cName);
            builder.setProvince(pName);
            builder.setAreaId(area.getId());
            dataBuilder.addManagerOrganizations(builder);
        }
        return listBuilder.setData(dataBuilder).setResult(resultBuilder.setCode(0).setSuccess(true).setMsg("success")).build();
    }

    public Solar.ManagerOrganizationDTO buildMarkDeleteOrganizationByIdDTO(OrgDeleteContext context) {

        Solar.ManagerOrganizationDTO.Builder builder = Solar.ManagerOrganizationDTO.newBuilder();

        return builder.setResult(COMMON.Result.newBuilder().setCode(0).setSuccess(true).build()).build();
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
