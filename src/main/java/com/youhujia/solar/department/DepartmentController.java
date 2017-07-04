package com.youhujia.solar.department;

import com.youhujia.halo.common.BaseController;
import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * Created by huangYing on 2017/4/17.
 */
@RestController
@RequestMapping(value = "/api/solar/v1/departments")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentBO departmentBO;

    /**
     * 创建科室
     * @param option
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Solar.DepartmentDTO createDepartment(@RequestBody Solar.DepartmentCreateOption option) {

        try {
            return departmentBO.createDepartment(option);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 根据科室id获取科室信息
     * @param departmentId
     * @return
     */
    @RequestMapping(value = "/{departmentId}", method = RequestMethod.GET)
    public Solar.DepartmentDTO getDepartmentById(@PathVariable("departmentId") Long departmentId) {

        try {
            return departmentBO.getDepartmentById(departmentId);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 根据科室id批量获取科室信息
     * @param ids
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Solar.DepartmentListDTO getDepartmentListByIds(@RequestParam("departmentIds") String ids) {

        try {
            return departmentBO.getDepartmentListByIds(ids);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 根据科室编号获取科室信息
     * @param departmentNo
     * @return
     */
    @RequestMapping(value = "/no/{departmentNo}", method = RequestMethod.GET)
    public Solar.DepartmentDTO getDepartmentByNo(@PathVariable("departmentNo") String departmentNo) {
        try {
            return departmentBO.getDepartmentByNo(departmentNo);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 根据中科室id获取访客科室
     * @param departmentId
     * @return
     */
    @RequestMapping(value = "/{departmentId}/guest", method = RequestMethod.GET)
    public Solar.DepartmentDTO getGuestDepartmentByHostDepartmentId(@PathVariable("departmentId") Long departmentId) {

        try {
            return departmentBO.getGuestDepartmentByHostDepartmentId(departmentId);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 更新科室信息
     * @param department
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Solar.DepartmentDTO updateDepartment(@RequestBody Solar.DepartmentUpdateOption department) {

        try {
            return departmentBO.updateDepartment(department);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 更新科室微信二维码
     * @param option
     * @return
     */
    @RequestMapping(value = "/wxQrCode", method = RequestMethod.PATCH)
    public Solar.DepartmentDTO updateDepartmentWxQrCode(@RequestBody Solar.DepartmentOption option) {

        try {
            return departmentBO.updateDepartmentWxQrCode(option);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 科室管理员申请开通科室后台管理权限
     * @param departmentId
     * @param option
     * @return
     */
    @RequestMapping("/{departmentId}/request-management-right")
    public COMMON.SimpleResponse requestManagementRight(@PathVariable("departmentId") Long departmentId, @RequestBody Solar.RequestManagementRightOption option) {

        try {
            return departmentBO.requestManagementRight(departmentId, option);
        } catch (Exception e) {
            return handleException(a -> COMMON.SimpleResponse.newBuilder().setResult(a).build(), e);
        }
    }

    //---------------- code for improve department zhushou -------------------//

    /**
     * 根据机构id获取科室（助手端）
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/get-by-org-id/{orgId}", method = RequestMethod.GET)
    public Solar.LBSDepartmentDTO getDepartmentByOrgIdForZhuShou(@PathVariable("orgId") Long orgId) {

        try {
            return departmentBO.getDepartmentListByOrgId(orgId);
        } catch (Exception e) {
            return handleException(a -> Solar.LBSDepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    //---------------- code for improve department admin -------------------//

    /**
     * 管理员根据机构id获取科室
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/manager-department", method = RequestMethod.GET)
    public Solar.ManagerDepartmentListDTO getAllDepartmentsWithoutDeleteByOrganizationId(@RequestParam("orgId") Long orgId) {

        try {
            return departmentBO.getAllWithoutDeleteDepartmentByOrgId(orgId);
        } catch (Exception e) {
            return handleException(a -> Solar.ManagerDepartmentListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 管理员删除科室
     * @param departmentId
     * @return
     */
    @RequestMapping(value = "/manager-department/{departmentId}", method = RequestMethod.DELETE)
    public Solar.ManagerDepartmentDTO managerDeleteDepartment(@PathVariable("departmentId") Long departmentId) {

        try {
            return departmentBO.markDeleteDepartmentById(departmentId);
        } catch (Exception e) {
            return handleException(a -> Solar.ManagerDepartmentDTO.newBuilder().setResult(a).build(), e);
        }
    }
    /**
     * QueryDepartment
     */
    @RequestMapping(value = "/query-department", method = RequestMethod.GET)
    public Solar.DepartmentListDTO queryDepartment(@RequestParam Map<String, String> map) {

        try {
            return departmentBO.queryDepartment(map);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentListDTO.newBuilder().setResult(a).build(), e);
        }
    }
}
