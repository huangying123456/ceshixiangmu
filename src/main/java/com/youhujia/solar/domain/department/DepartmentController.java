package com.youhujia.solar.domain.department;

import com.youhujia.halo.common.BaseController;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by huangYing on 2017/4/17.
 */
@RestController
@RequestMapping(value = "api/solar/v1/department")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentBO departmentBO;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Solar.DepartmentDTO createDepartment(@RequestBody Solar.DepartmentDTO department) {

        try {
            return departmentBO.createDepartment(department);
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

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Solar.DepartmentDTO updateDepartment(@RequestBody Solar.DepartmentDTO department) {

        try {
            return departmentBO.updateDepartment(department);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }


}
