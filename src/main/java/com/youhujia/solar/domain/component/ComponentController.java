package com.youhujia.solar.domain.component;

import com.youhujia.halo.common.BaseController;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ljm on 2017/4/17.
 */
@RestController
@RequestMapping("/api/solar/v1/department")

public class ComponentController extends BaseController {

    @Autowired
    ComponentBO componentBO;

    /**
     * 根据科室id批量获取科室组件列表
     *
     * @param ids(由科室id组成的字符串，如departmentId=1,2,3)
     * @return
     */
    @RequestMapping("/component")
    public Solar.ComponentListDataListDTO batchComponentListByDepartmentIds(@RequestParam("departmentIds") String ids) {

        try {
            return componentBO.batchComponentListByDepartmentIds(ids);
        } catch (Exception e) {
            return handleException(a -> Solar.ComponentListDataListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 获取文章组组件
     *
     * @param componentId
     * @return
     */
    //todo
    @RequestMapping("/{departmentId}/articleGroup-component/{componentId}")
    public Solar.ArticleGroupDTO getArticleGroupComponentById(@PathVariable("componentId") Long componentId) {

        try {
            return componentBO.getArticleGroupComponentById(componentId);
        } catch (Exception e) {
            return handleException(a -> Solar.ArticleGroupDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 获取自测组件
     *
     * @param componentId
     * @return
     */
    @RequestMapping("/{departmentId}/self-evaluation-component/{componentId}")
    public Solar.SelfEvaluationComponentDTO getSelfEvaluationComponentById(@PathVariable("componentId") Long componentId) {

        try {
            return componentBO.getSelfEvaluationComponentById(componentId);
        } catch (Exception e) {
            return handleException(a -> Solar.SelfEvaluationComponentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 获取服务组件
     *
     * @param componentId
     * @return
     */
    @RequestMapping("/{departmentId}/serviceItem/{componentId}")
    public Solar.ServiceItemComponentDTO getServiceItemComponentById(@PathVariable("componentId") Long componentId) {

        try {
            return componentBO.getServiceItemComponentById(componentId);
        } catch (Exception e) {
            return handleException(a -> Solar.ServiceItemComponentDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 获取推荐组件
     *
     * @param componentId
     * @return
     */
    @RequestMapping("/{departmentId}/recom/{componentId}")
    public Solar.RecomComponentDTO getRecomComponentById(@PathVariable("componentId") Long componentId) {

        try {
            return componentBO.getRecomComponentById(componentId);
        } catch (Exception e) {
            return handleException(a -> Solar.RecomComponentDTO.newBuilder().setResult(a).build(), e);
        }
    }

}
