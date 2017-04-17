package com.youhujia.solar.domain.department;

import com.youhujia.halo.solar.Solar;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class DepartmentBO {

    public Solar.DepartmentDTO createDepartment(Solar.DepartmentDTO department) {

    }

    public Solar.DepartmentDTO getDepartmentById(Long departmentId) {

    }

    public Solar.DepartmentDTO getDepartmentByNo(String departmentNo) {

    }

    public Solar.DepartmentDTO getGuestDepartmentByHostDepartmentId(Long departmentId) {


    }

    public Solar.DepartmentDTO updateDepartment(Solar.DepartmentDTO department) {


    }


    //---------------- code for improve department zhushou -------------------//
    public Solar.CreateOrUpdateDepartmentDTO lbsCreateDepartment(Solar.LBSCreateDepartmentOption option) {


    }

    public Solar.LBSDepartmentDTO getDepartmentListByOrgId(Long orgId) {


    }

    //---------------- code for improve department admin -------------------//
    public Solar.ManagerDepartmentListDTO getAllWithoutDeleteDepartmentByOrgId(Long orgId) {


    }

    public Solar.ManagerDepartmentDTO managerCreateDepartment(Solar.CreateOrUpdateDepartmentOption option) {


    }

    public Solar.ManagerDepartmentDTO managerUpdateDepartment(Long departmentId, Solar.CreateOrUpdateDepartmentOption option) {


    }

    public Solar.ManagerDepartmentDTO markDeleteDepartmentById(Long departmentId) {


    }

    public Solar.ManagerDepartmentDTO managerGetDepartmentById(Long departmentId) {


    }
}
