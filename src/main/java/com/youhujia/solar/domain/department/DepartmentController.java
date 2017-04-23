package com.youhujia.solar.domain.department;

import com.youhujia.halo.common.BaseController;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by huangYing on 2017/4/17.
 */
@RestController
@RequestMapping(value = "/api/solar/v1/departments")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentBO departmentBO;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Solar.DepartmentDTO createDepartment(@RequestBody Solar.DepartmentCreateOption option) {

        try {
            return departmentBO.createDepartment(option);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.GET)
    public Solar.DepartmentDTO getDepartmentById(@PathVariable("departmentId") Long departmentId) {

        try {
            return departmentBO.getDepartmentById(departmentId);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping(value = "/no/{departmentNo}", method = RequestMethod.GET)
    public Solar.DepartmentDTO getDepartmentByNo(@PathVariable("departmentNo") String departmentNo) {
        try {
            return departmentBO.getDepartmentByNo(departmentNo);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping(value = "/{departmentId}/guest", method = RequestMethod.GET)
    public Solar.DepartmentDTO getGuestDepartmentByHostDepartmentId(@PathVariable("departmentId") Long departmentId) {

        try {
            return departmentBO.getGuestDepartmentByHostDepartmentId(departmentId);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Solar.DepartmentListDTO getDepartmentListByIds(@RequestParam("departmentIds") String departmentIds) {

        try {
            return departmentBO.getDepartmentListByIds(departmentIds);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Solar.DepartmentDTO updateDepartment(@RequestBody Solar.DepartmentUpdateOption department) {

        try {
            return departmentBO.updateDepartment(department);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping(value = "/wxQrCode", method = RequestMethod.PATCH)
    public Solar.DepartmentDTO updateDepartmentWxQrCode(@RequestBody Solar.DepartmentOption option) {

        try {
            return departmentBO.updateDepartmentWxQrCode(option);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    //---------------- code for improve department zhushou -------------------//
    @RequestMapping(value = "/get-by-org-id/{orgId}", method = RequestMethod.GET)
    public Solar.LBSDepartmentDTO getDepartmentByOrgIdForZhuShou(@PathVariable("orgId") Long orgId) {

        try {
            return departmentBO.getDepartmentListByOrgId(orgId);
        } catch (Exception e) {
            return handleException(a -> Solar.LBSDepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    //---------------- code for improve department admin -------------------//
    @RequestMapping(value = "/manager-department", method = RequestMethod.GET)
    public Solar.ManagerDepartmentListDTO getAllDepartmentsWithoutDeleteByOrganizationId(@RequestParam("orgId") Long orgId) {

        try {
            return departmentBO.getAllWithoutDeleteDepartmentByOrgId(orgId);
        } catch (Exception e) {
            return handleException(a -> Solar.ManagerDepartmentListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping(value = "/manager-department/{departmentId}", method = RequestMethod.DELETE)
    public Solar.ManagerDepartmentDTO managerDeleteDepartment(@PathVariable("departmentId") Long departmentId) {

        try {
            return departmentBO.markDeleteDepartmentById(departmentId);
        } catch (Exception e) {
            return handleException(a -> Solar.ManagerDepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }
}
