package com.youhujia.solar.department;

import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.department.create.DepCreateContext;
import com.youhujia.solar.department.delete.DepDeleteContext;
import com.youhujia.solar.department.query.DepQueryContext;
import com.youhujia.solar.department.update.DepUpdateContext;
import com.youhujia.solar.organization.Organization;
import com.youhujia.solar.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.youhujia.halo.util.ResponseUtil.resultOK;
/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class DepartmentDTOFactory {

    @Autowired
    private OrganizationDAO organizationDAO;

    public Solar.DepartmentDTO buildCreateDepartmentDTO(DepCreateContext context) {

        Department department = context.getDepartment();

        Solar.DepartmentDTO departmentDTO = toDepartmentDTO(department);

        return departmentDTO;
    }

    public Solar.DepartmentDTO buildGetDepartmentByIdDTO(DepQueryContext context) {

        Department department = context.getDepartment();
        if (department == null) {
            return Solar.DepartmentDTO.newBuilder().setResult(COMMON.Result.newBuilder().setCode(0).setSuccess(true).build()).build();
        } else {

            Solar.DepartmentDTO departmentDTO = toDepartmentDTO(department);

            return departmentDTO;
        }
    }

    public Solar.DepartmentListDTO buildGetDepartmentListByIdsDTO(DepQueryContext context) {

        List<Department> list = context.getDepartmentList();

        Solar.DepartmentListDTO departmentListDTO = toDepartmentListDTO(list);

        return departmentListDTO;
    }

    public Solar.DepartmentDTO buildGetDepartmentByNoDTO(DepQueryContext context) {

        Department department = context.getDepartment();

        Solar.DepartmentDTO departmentDTO = toDepartmentDTO(department);

        return departmentDTO;
    }

    public Solar.DepartmentDTO buildGetGuestDepartmentByHostDepartmentIdDTO(DepQueryContext context) {

        Department department = context.getDepartment();

        Solar.DepartmentDTO departmentDTO = toDepartmentDTO(department);

        return departmentDTO;
    }

    public Solar.DepartmentDTO buildUpdateDepartmentDTO(DepUpdateContext context) {

        Department department = context.getDepartment();

        Solar.DepartmentDTO departmentDTO = toDepartmentDTO(department);

        return departmentDTO;
    }

    public Solar.LBSDepartmentDTO buildGetDepartmentListByOrgIdDTO(DepQueryContext context) {

        Solar.LBSDepartmentDTO.Builder depBuilder = Solar.LBSDepartmentDTO.newBuilder();
        COMMON.Result.Builder resultBuilder = COMMON.Result.newBuilder();
        Solar.LBSDepartmentDataDTO.Builder dataBuilder = Solar.LBSDepartmentDataDTO.newBuilder();

        for (Department d : context.getDepartmentList()) {
            Solar.LBSDepartmentInfo.Builder builder = Solar.LBSDepartmentInfo.newBuilder();
            builder.setId(d.getId());
            builder.setName(d.getName());
            dataBuilder.addInfos(builder);
        }
        return depBuilder.setData(dataBuilder).setResult(resultBuilder.setCode(0).setSuccess(true).setMsg("success")).build();
    }

    public Solar.ManagerDepartmentListDTO buildGetAllWithoutDeleteDepartmentByOrgIdDTO(DepQueryContext context) {
        Solar.ManagerDepartmentListDTO.Builder listBuilder = Solar.ManagerDepartmentListDTO.newBuilder();
        COMMON.Result.Builder resultBuilder = COMMON.Result.newBuilder();

        Solar.ManagerDepartmentListDataDTO.Builder dataBuilder = Solar.ManagerDepartmentListDataDTO.newBuilder();
        for (Department d : context.getDepartmentList()) {
            Solar.ManagerDepartment.Builder builder = Solar.ManagerDepartment.newBuilder();
            builder.setId(d.getId());
            builder.setOrganizationId(context.getOrganization().getId());
            builder.setName(d.getName());
            builder.setOrganizationName(context.getOrganization().getName());
            if (d.getCreatedAt() != null) {
                builder.setCreatedAt(d.getCreatedAt().getTime());
            }
            builder.setStatus(d.getStatus());
            if (d.getNumber() != null) {
                builder.setNumber(d.getNumber());
            }
            if (d.getImgUrl() != null) {
                builder.setImgUrl(d.getImgUrl());
            }
            if (d.getMayContact() != null) {
                builder.setMayContact(d.getMayContact());
            }
            if (d.getClassificationType() != null) {
                builder.setClassificationType(d.getClassificationType());
            }
            dataBuilder.addManagerDepartments(builder);
        }
        return listBuilder.setData(dataBuilder).setResult(resultBuilder.setCode(0).setSuccess(true).setMsg("success")).build();
    }

    public Solar.ManagerDepartmentDTO buildMarkDeleteDepartmentById(DepDeleteContext context) {

        Solar.ManagerDepartmentDTO.Builder builder = Solar.ManagerDepartmentDTO.newBuilder();
        COMMON.Result.Builder resultBuilder = COMMON.Result.newBuilder();

        return builder.setResult(resultBuilder.setCode(0).setSuccess(true).setMsg("success")).build();
    }

    private Solar.DepartmentDTO toDepartmentDTO(Department department) {

        Solar.DepartmentDTO.Builder DTOBuilder = Solar.DepartmentDTO.newBuilder();

        DTOBuilder.setDepartment(buildDepartment(department))
                .setResult(COMMON.Result.newBuilder().setCode(0).setSuccess(true).build());
        return DTOBuilder.build();
    }

    public Solar.DepartmentListDTO toDepartmentListDTO(List<Department> list) {

        Solar.DepartmentListDTO.Builder builder = Solar.DepartmentListDTO.newBuilder();
        list.stream().forEach(department -> {
            builder.addDepartment(buildDepartment(department));
        });

        return builder.setResult(resultOK()).build();
    }

    private Solar.Department buildDepartment(Department department) {

        Solar.Department.Builder builder = Solar.Department.newBuilder();

        builder.setDepartmentId(department.getId())
                .setName(department.getName())
                .setOrganizationId(department.getOrganizationId())
                .setOrganizationName(getOrganization(department.getOrganizationId()).getName());

        if (department.getCreatedAt() != null) {
            builder.setCreatedAt(department.getCreatedAt().getTime());
        }
        if (department.getUpdatedAt() != null) {
            builder.setUpdatedAt(department.getUpdatedAt().getTime());
        }
        if (department.getNumber() != null) {
            builder.setNumber(department.getNumber());
        }
        if (department.getAuthCode() != null) {
            builder.setAuthCode(department.getAuthCode());
        }
        if (department.getStatus() != null) {
            builder.setStatus(department.getStatus());
        }
        if (department.getGuest() != null) {
            builder.setIsGuest(department.getGuest() > 0L);
        }
        if (department.getHostId() != null) {
            builder.setHostId(department.getHostId());
        }
        if (department.getWxSubQRCodeValue() != null) {
            builder.setWxQrcode(department.getWxSubQRCodeValue());
        }
        if (department.getImgUrl() != null) {
            builder.setImgUrl(department.getImgUrl());
        }
        if (department.getMayContact() != null) {
            builder.setMayContact(department.getMayContact());
        }
        if (department.getClassificationType() != null) {
            builder.setClassificationType(department.getClassificationType());
        }
        return builder.build();
    }

    private Organization getOrganization(Long orgId) {

        Organization organization = organizationDAO.findOne(orgId);

        if (organization == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "the department does not belong to any organization");
        }
        return organization;
    }
    public Solar.DepartmentListDTO buildDepartmentListDTO(DepQueryContext context) {

        Solar.DepartmentListDTO.Builder builder = Solar.DepartmentListDTO.newBuilder();
        context.getDepartmentList().stream().forEach(department -> {
            builder.addDepartment(buildDepartment(department));
        });

        return builder.setResult(resultOK()).build();
    }
}
