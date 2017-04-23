package com.youhujia.solar.domain.department;

import com.youhujia.halo.solar.Solar;
import com.youhujia.halo.yolar.Yolar;
import com.youhujia.solar.domain.department.create.DepCreateBO;
import com.youhujia.solar.domain.department.create.DepCreateContext;
import com.youhujia.solar.domain.department.delete.DepDeleteBO;
import com.youhujia.solar.domain.department.delete.DepDeleteContext;
import com.youhujia.solar.domain.department.query.DepQueryBO;
import com.youhujia.solar.domain.department.query.DepQueryContext;
import com.youhujia.solar.domain.department.update.DepUpdateBO;
import com.youhujia.solar.domain.department.update.DepUpdateContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class DepartmentBO {

    @Autowired
    private DepCreateBO depCreateBO;

    @Autowired
    private DepQueryBO depQueryBo;

    @Autowired
    private DepUpdateBO depUpdateBO;

    @Autowired
    private DepDeleteBO depDeleteBO;

    @Autowired
    private DepartmentDTOFactory departmentFactory;

    public Solar.DepartmentDTO createDepartment(Solar.DepartmentCreateOption option) {

        DepCreateContext depCreateContext = depCreateBO.createDepartment(option);

        Solar.DepartmentDTO departmentDTO = departmentFactory.buildCreateDepartmentDTO(depCreateContext);

        return departmentDTO;
    }

    public Solar.DepartmentDTO getDepartmentById(Long departmentId) {

        DepQueryContext context = depQueryBo.getDepartmentById(departmentId);

        Solar.DepartmentDTO departmentDTO = departmentFactory.buildGetDepartmentByIdDTO(context);

        return departmentDTO;
    }

    public Solar.DepartmentDTO getDepartmentByNo(String departmentNo) {

        DepQueryContext context = depQueryBo.getDepartmentByNo(departmentNo);

        Solar.DepartmentDTO departmentDTO = departmentFactory.buildGetDepartmentByNoDTO(context);

        return departmentDTO;
    }

    public Solar.DepartmentListDTO getDepartmentListByIds(String departmentIds) {
        DepQueryContext context = depQueryBo.getDepartmentListByIds(departmentIds);
        Solar.DepartmentListDTO departmentListDTO = departmentFactory.toDepartmentListDTO(context.getDepartmentList());
        return departmentListDTO;
    }

    public Solar.DepartmentDTO getGuestDepartmentByHostDepartmentId(Long departmentId) {

        DepQueryContext context = depQueryBo.getGuestDepartmentByHostDepartmentId(departmentId);

        Solar.DepartmentDTO departmentDTO = departmentFactory.buildGetGuestDepartmentByHostDepartmentIdDTO(context);

        return departmentDTO;
    }

    public Solar.DepartmentDTO updateDepartment(Solar.DepartmentUpdateOption department) {

        DepUpdateContext context = depUpdateBO.updateDepartment(department);

        Solar.DepartmentDTO departmentDTO = departmentFactory.buildUpdateDepartmentDTO(context);

        return departmentDTO;
    }


    //------------------- service for improve department zhushou ---------------------//

    public Solar.LBSDepartmentDTO getDepartmentListByOrgId(Long orgId) {

        DepQueryContext context = depQueryBo.getDepartmentListByOrgId(orgId);

        return departmentFactory.buildGetDepartmentListByOrgIdDTO(context);
    }

    //------------------- service for improve department admin --------------------//
    public Solar.ManagerDepartmentListDTO getAllWithoutDeleteDepartmentByOrgId(Long orgId) {

        DepQueryContext context = depQueryBo.getAllWithoutDeleteDepartmentByOrgId(orgId);

        return departmentFactory.buildGetAllWithoutDeleteDepartmentByOrgIdDTO(context);
    }

    public Solar.ManagerDepartmentDTO markDeleteDepartmentById(Long departmentId) {

        DepDeleteContext context = depDeleteBO.markDeleteDepartmentById(departmentId);

        return departmentFactory.buildMarkDeleteDepartmentById(context);
    }

    public Solar.DepartmentDTO updateDepartmentWxQrCode(Solar.DepartmentOption option) {
        DepUpdateContext context = depUpdateBO.updateDepartmentWxQrCode(option);
        return departmentFactory.buildUpdateDepartmentDTO(context);
    }
}
