package com.youhujia.solar.component;

import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.component.query.articleDisease.ArticleDiseaseComponentQueryBO;
import com.youhujia.solar.component.query.articleDisease.ArticleDiseaseComponentQueryContext;
import com.youhujia.solar.component.query.componentList.ComponentListQueryBO;
import com.youhujia.solar.component.query.componentList.ComponentListQueryContext;
import com.youhujia.solar.component.query.recommend.RecomComponentQueryBO;
import com.youhujia.solar.component.query.recommend.RecomComponentQueryContext;
import com.youhujia.solar.component.query.selfEvaluation.SelfEvaluationComponentQueryBO;
import com.youhujia.solar.component.query.selfEvaluation.SelfEvaluationComponentQueryContext;
import com.youhujia.solar.component.query.serviceItem.ServiceItemComponentQueryBO;
import com.youhujia.solar.component.query.serviceItem.ServiceItemComponentQueryContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class ComponentBO {

    @Autowired
    ComponentListQueryBO componentListQueryBO;
    @Autowired
    ArticleDiseaseComponentQueryBO articleDiseaseComponentQueryBO;
    @Autowired
    SelfEvaluationComponentQueryBO selfEvaluationComponentQueryBO;
    @Autowired
    ServiceItemComponentQueryBO serviceItemComponentQueryBO;
    @Autowired
    RecomComponentQueryBO recomComponentQueryBO;
    @Autowired
    ComponentDTOFactory componentDTOFactory;

    public Solar.ComponentListDataListDTO batchComponentListByDepartmentIds(String ids) {
         ComponentListQueryContext context = componentListQueryBO.batchComponentListByDepartmentIds(ids);
        return componentDTOFactory.buildComponentListDTO(context);
    }

    public Solar.ArticleGroupDTO getArticleGroupComponentById(Long componentId) {
        ArticleDiseaseComponentQueryContext context = articleDiseaseComponentQueryBO.getArticleGroupComponentById(componentId);
        return componentDTOFactory.buildArticleDiseaseGroupDTO(context);
    }

    public Solar.SelfEvaluationComponentDTO getSelfEvaluationComponentById(Long componentId) {
        SelfEvaluationComponentQueryContext context = selfEvaluationComponentQueryBO.getSelfEvaluationComponentById(componentId);
        return componentDTOFactory.buildSelfEvaluationDTO(context);
    }

    public Solar.ServiceItemComponentDTO getServiceItemComponentById(Long componentId) {
        ServiceItemComponentQueryContext context = serviceItemComponentQueryBO.getServiceItemComponentById(componentId);
        return componentDTOFactory.buildServiceItemDTO(context);
    }

    public Solar.RecomComponentDTO getRecomComponentById(Long componentId) {
        RecomComponentQueryContext context = recomComponentQueryBO.getRecomComponentById(componentId);
        return componentDTOFactory.buildRecomComponentDTO(context);
    }

}
