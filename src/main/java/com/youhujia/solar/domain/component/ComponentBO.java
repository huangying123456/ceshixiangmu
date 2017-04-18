package com.youhujia.solar.domain.component;

import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.component.query.componentList.ComponentQueryBO;
import com.youhujia.solar.domain.component.query.articleDisease.ArticleDiseaseComponentQueryBO;
import com.youhujia.solar.domain.component.query.articleDisease.ArticleDiseaseComponentQueryContext;
import com.youhujia.solar.domain.component.query.recommend.RecomComponentQueryBO;
import com.youhujia.solar.domain.component.query.recommend.RecomComponentQueryContext;
import com.youhujia.solar.domain.component.query.requestManagerRight.RequestBO;
import com.youhujia.solar.domain.component.query.requestManagerRight.RequestContext;
import com.youhujia.solar.domain.component.query.selfEvaluation.SelfEvaluationComponentQueryBO;
import com.youhujia.solar.domain.component.query.selfEvaluation.SelfEvaluationComponentQueryContext;
import com.youhujia.solar.domain.component.query.serviceItem.ServiceItemComponentQueryBO;
import com.youhujia.solar.domain.component.query.serviceItem.ServiceItemComponentQueryContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class ComponentBO {

    @Autowired
    ComponentFactory componentFactory;

    @Autowired
    ComponentQueryBO componentQueryBO;

    @Autowired
    ArticleDiseaseComponentQueryBO articleDiseaseComponentQueryBO;

    @Autowired
    SelfEvaluationComponentQueryBO selfEvaluationComponentQueryBO;

    @Autowired
    ServiceItemComponentQueryBO serviceItemComponentQueryBO;

    @Autowired
    RecomComponentQueryBO recomComponentQueryBO;

    @Autowired
    RequestBO requestBO;

    public Solar.ComponentListDataListDTO batchComponentListByDepartmentIds(Solar.DepartmentIdListOption option) {
        ComponentContext componentContext = componentFactory.buildComponentContext(option);
        return componentQueryBO.buildComponentListDataListDTO(componentContext.getTagListDTOList());
    }

    public Solar.ArticleDiseaseGroupDTO getDiseaseComponentById(Long componentId) {
        ArticleDiseaseComponentQueryContext context = componentFactory.buildArticleDiseaseComponentQueryContext(componentId);
        return articleDiseaseComponentQueryBO.buildArticleDiseaseGroupDTO(context.getTagDTO());
    }

    public Solar.SelfEvaluationComponentDTO getSelfEvaluationComponentById(Long componentId) {
        SelfEvaluationComponentQueryContext context = componentFactory.buildSelfEvaluationComponentQueryContext(componentId);
        return selfEvaluationComponentQueryBO.buildSelfEvaluationDTO(context.getTagDTO());
    }

    public Solar.ServiceItemComponentDTO getServiceItemComponentById(Long componentId) {
        ServiceItemComponentQueryContext context = componentFactory.buildServiceItemComponentQueryContext(componentId);
        return serviceItemComponentQueryBO.buildServiceItemComponent(context.getTagDTO());
    }

    public Solar.RecomComponentDTO getRecomComponentById(Long componentId) {
        RecomComponentQueryContext context = componentFactory.buildRecomComponentQueryContext(componentId);
        return recomComponentQueryBO.buildRecomComponent(context.getTagDTO());
    }

    public COMMON.SimpleResponse requestManagementRight(Long departmentId, Solar.RequestManagementRightOption option) {
        RequestContext context = componentFactory.buildRequestContext(departmentId,option);
        return requestBO.buildRequsetResult(context.getDepartment());

    }
}
