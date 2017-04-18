package com.youhujia.solar.domain.component;

import com.youhujia.halo.common.BaseController;
import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ljm on 2017/4/17.
 */
@RestController
@RequestMapping("/api/solar/v1/department")

public class ComponentController extends BaseController {

    @Autowired
    ComponentBO componentBO;

    @RequestMapping("/component")
    public Solar.ComponentListDataListDTO batchComponentListByDepartmentIds(@RequestBody Solar.DepartmentIdListOption option) {

        try {
            return componentBO.batchComponentListByDepartmentIds(option);
        } catch (Exception e) {
            return handleException(a -> Solar.ComponentListDataListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping("/disease-component/{componentId}")
    public Solar.ArticleDiseaseGroupDTO getDiseaseComponentById(@PathVariable("componentId") Long componentId) {

        try {
            return componentBO.getDiseaseComponentById(componentId);
        } catch (Exception e) {
            return handleException(a -> Solar.ArticleDiseaseGroupDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping("/{departmentId}/self-evaluation-component/{componentId}")
    public Solar.SelfEvaluationComponentDTO getSelfEvaluationComponentById(@PathVariable("componentId") Long componentId) {

        try {
            return componentBO.getSelfEvaluationComponentById(componentId);
        } catch (Exception e) {
            return handleException(a -> Solar.SelfEvaluationComponentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping("/{departmentId}/serviceItem/{componentId}")
    public Solar.ServiceItemComponentDTO getServiceItemComponentById(@PathVariable("componentId") Long componentId) {

        try {
            return componentBO.getServiceItemComponentById(componentId);
        } catch (Exception e) {
            return handleException(a -> Solar.ServiceItemComponentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping("/{departmentId}/recom/{componentId}")
    public Solar.RecomComponentDTO getRecomComponentById(@PathVariable("componentId") Long componentId) {

        try {
            return componentBO.getRecomComponentById(componentId);
        } catch (Exception e) {
            return handleException(a -> Solar.RecomComponentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping("/request-management-right")
    public COMMON.SimpleResponse requestManagementRight(@PathVariable("departmentId") Long departmentId, @RequestBody Solar.RequestManagementRightOption option) {

        try {
            return componentBO.requestManagementRight(departmentId,option);
        } catch (Exception e) {
            return handleException(a -> COMMON.SimpleResponse.newBuilder().setResult(a).build(), e);
        }
    }

}
