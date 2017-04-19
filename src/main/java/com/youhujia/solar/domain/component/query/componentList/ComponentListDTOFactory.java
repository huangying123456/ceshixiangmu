package com.youhujia.solar.domain.component.query.componentList;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.youhujia.halo.hdfragments.HDFragments;
//import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
//import com.youhujia.halo.solar.ComponentTypeEnum;
//import com.youhujia.halo.solar.Solar;
//import com.youhujia.solar.domain.common.SolarHalper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * Created by ljm on 2017/4/17.
// */
//@Component
public class ComponentListDTOFactory {
//    @Autowired
//    HDFragmentsServiceWrap hdFragmentsServiceWrap;
//
//    public Solar.ComponentListDataListDTO buildComponentListDTO(ComponentListQueryContext componentListQueryContext) {
//
//        Solar.ComponentListDataListDTO.Builder componentListDataListDTO = Solar.ComponentListDataListDTO.newBuilder();
//        List<HDFragments.TagListDTO> tagListDTOList = componentListQueryContext.getTagListDTOList();
//
//        if (tagListDTOList.size() > 0) {
//            tagListDTOList.stream().forEach(tagListDTO -> {
//
//                Solar.ComponentListData.Builder componentListData = Solar.ComponentListData.newBuilder();
//                tagListDTO.getData().getTagsList().stream().forEach(tag -> {
//
//                    Solar.Component.Builder componentBuild = Solar.Component.newBuilder();
//                    HDFragments.TagPropertiesDTO tagPropertiesDTO = hdFragmentsServiceWrap.getTagPropertiesByTagId(tag.getId());
//
//                    if (tag.getName().equals(ComponentTypeEnum.DIRECT.getName())) {
//                        componentBuild.setType(ComponentTypeEnum.DIRECT.getName());
//                        componentBuild.setDirect(transform2DirectComponent(tag, tagPropertiesDTO));
//                        componentListData.addComponent(componentBuild);
//                    }
//
//                    if (tag.getName().equals(ComponentTypeEnum.ARTICLE_DISEASE_GROUP.getName())) {
//                        componentBuild.setType(ComponentTypeEnum.ARTICLE_DISEASE_GROUP.getName());
//                        componentBuild.setArticleDiseaseGroup(transform2ArticleGroupComponent(tag, tagPropertiesDTO));
//                        componentListData.addComponent(componentBuild);
//                    }
//
//                    if (tag.getName().equals(ComponentTypeEnum.SELF_EVALUATION.getName())) {
//                        componentBuild.setType(ComponentTypeEnum.SELF_EVALUATION.getName());
//                        componentBuild.setSelfEvaluation(transform2SelfEvaluationComponent(tag, tagPropertiesDTO));
//                        componentListData.addComponent(componentBuild);
//
//                    }
//
//                    if (tag.getName().equals(ComponentTypeEnum.Recom.getName())) {
//                        componentBuild.setType(ComponentTypeEnum.Recom.getName());
//                        componentBuild.setRecom(transform2RecomComponent(tag, tagPropertiesDTO));
//                        componentListData.addComponent(componentBuild);
//                    }
//
//                    if (tag.getName().equals(ComponentTypeEnum.SERVICE_ITEM.getName())) {
//                        componentBuild.setType(ComponentTypeEnum.SERVICE_ITEM.getName());
//                        componentBuild.setServiceItem(transform2ServiceItem(tag, tagPropertiesDTO));
//                        componentListData.addComponent(componentBuild);
//                    }
//
//
//                });
//                componentListDataListDTO.addComponentListData(componentListData.build());
//            });
//        }
//        return componentListDataListDTO.build();
//    }
//
//
//    private Solar.ServiceItem transform2ServiceItem(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
//        Solar.ServiceItem.Builder serviceItem = Solar.ServiceItem.newBuilder();
//
//        if (tag.hasName()) {
//            serviceItem.setTitle(tag.getName());
//        }
//
//        if (tag.hasId()) {
//            serviceItem.setComponentId(tag.getId());
//        }
//
//        if (tag.hasCreatorId()) {
//            serviceItem.setCreatorId(tag.getCreatorId());
//        }
//
//        if (tag.hasDptId()) {
//            serviceItem.setDepartmentId(tag.getDptId());
//        }
//
//        if (tagPropertiesDTO.getData().getPropertiesList().size() > 0) {
//            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
//                if (tagProperty.hasValue()) {
//                    JSONObject jsonObject = JSON.parseObject(tagProperty.getValue());
//                    serviceItem.setRank(Long.parseLong(jsonObject.get("rank").toString()));
//                    String[] str = jsonObject.get("serviceItemId").toString().split(",");
//                    for (String serviceItemId : str) {
//                        serviceItem.addServiceItemId(Long.parseLong(serviceItemId));
//                    }
//                }
//            });
//        }
//        return serviceItem.build();
//    }
//
//    private Solar.Recom transform2RecomComponent(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
//        Solar.Recom.Builder recomBuild = Solar.Recom.newBuilder();
//
//        if (tag.hasName()) {
//            recomBuild.setTitle(tag.getName());
//        }
//
//        if (tag.hasId()) {
//            recomBuild.setComponentId(tag.getId());
//        }
//
//        if (tag.hasCreatorId()) {
//            recomBuild.setCreatorId(tag.getCreatorId());
//        }
//
//        if (tag.hasDptId()) {
//            recomBuild.setDepartmentId(tag.getDptId());
//        }
//
//        if (tagPropertiesDTO.getData().getPropertiesList().size() > 0) {
//            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
//                if (tagProperty.hasValue()) {
//                    JSONObject jsonObject = JSON.parseObject(tagProperty.getValue());
//                    recomBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));
//                    String[] str = jsonObject.get("articleId").toString().split(",");
//                    for (String articleId : str) {
//                        recomBuild.addArticleId(Long.parseLong(articleId));
//                    }
//                }
//            });
//        }
//        return recomBuild.build();
//    }
//
//    private Solar.SelfEvaluation transform2SelfEvaluationComponent(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
//        Solar.SelfEvaluation.Builder selfEvaluationBuild = Solar.SelfEvaluation.newBuilder();
//
//        if (tag.hasName()) {
//            selfEvaluationBuild.setTitle(tag.getName());
//        }
//
//        if (tag.hasId()) {
//            selfEvaluationBuild.setComponentId(tag.getId());
//        }
//
//        if (tag.hasCreatorId()) {
//            selfEvaluationBuild.setCreatorId(tag.getCreatorId());
//        }
//
//        if (tag.hasDptId()) {
//            selfEvaluationBuild.setDepartmentId(tag.getDptId());
//        }
//
//        if (tagPropertiesDTO.getData().getPropertiesList().size() > 0) {
//            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
//                if (tagProperty.hasValue()) {
//                    JSONObject jsonObject = JSON.parseObject(tagProperty.getValue());
//                    selfEvaluationBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));
//                    String[] str = jsonObject.get("selfEvaluationId").toString().split(",");
//                    for (String selfEvaluationId : str) {
//                        selfEvaluationBuild.addSelfEvaluationId(Long.parseLong(selfEvaluationId));
//                    }
//                }
//            });
//        }
//        return selfEvaluationBuild.build();
//    }
//
//    private Solar.ArticleDiseaseGroup transform2ArticleGroupComponent(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
//        Solar.ArticleDiseaseGroup.Builder articleDiseaseGroupBuild = Solar.ArticleDiseaseGroup.newBuilder();
//
//        if (tag.hasName()) {
//            articleDiseaseGroupBuild.setTitle(tag.getName());
//        }
//
//        if (tag.hasId()) {
//            articleDiseaseGroupBuild.setComponentId(tag.getId());
//        }
//
//        if (tag.hasCreatorId()) {
//            articleDiseaseGroupBuild.setCreatorId(tag.getCreatorId());
//        }
//
//        if (tag.hasDptId()) {
//            articleDiseaseGroupBuild.setDepartmentId(tag.getDptId());
//        }
//
//        if (tagPropertiesDTO.getData().getPropertiesList().size() > 0) {
//            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
//                if (tagProperty.hasValue()) {
//                    JSONObject jsonObject = JSON.parseObject(tagProperty.getValue());
//                    articleDiseaseGroupBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));
//                    articleDiseaseGroupBuild.addGroup(SolarHalper.parseToSolarGroup(jsonObject.toString()));
//                }
//            });
//        }
//        return articleDiseaseGroupBuild.build();
//    }
//
//    private Solar.Direct transform2DirectComponent(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
//        Solar.Direct.Builder directBuild = Solar.Direct.newBuilder();
//
//        if (tag.hasName()) {
//            directBuild.setTitle(tag.getName());
//        }
//        if (tag.hasId()) {
//            directBuild.setComponentId(tag.getId());
//        }
//        if (tag.hasCreatorId()) {
//            directBuild.setCreatorId(tag.getCreatorId());
//        }
//        if (tag.hasDptId()) {
//            directBuild.setDepartmentId(tag.getDptId());
//        }
//        if (tagPropertiesDTO.getData().getPropertiesList().size() > 0) {
//            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
//                if (tagProperty.hasValue()) {
//                    JSONObject jsonObject = JSONObject.parseObject(tagProperty.getValue());
//                    directBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));
//                    directBuild.addUnit(SolarHalper.parseToSolarUnit(jsonObject.toString()));
//                }
//            });
//        }
//        return directBuild.build();
//    }
//
}
