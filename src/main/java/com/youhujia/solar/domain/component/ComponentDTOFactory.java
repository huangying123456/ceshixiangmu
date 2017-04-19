package com.youhujia.solar.domain.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.solar.ComponentTypeEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.common.SolarHalper;
import com.youhujia.solar.domain.component.query.articleDisease.ArticleDiseaseComponentQueryContext;
import com.youhujia.solar.domain.component.query.componentList.ComponentListQueryContext;
import com.youhujia.solar.domain.component.query.recommend.RecomComponentQueryContext;
import com.youhujia.solar.domain.component.query.selfEvaluation.SelfEvaluationComponentQueryContext;
import com.youhujia.solar.domain.component.query.serviceItem.ServiceItemComponentQueryContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ljm on 2017/4/19.
 */

@Component
public class ComponentDTOFactory {

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    public Solar.ComponentListDataListDTO buildComponentListDTO(ComponentListQueryContext context) {
        Solar.ComponentListDataListDTO.Builder componentListDataListDTO = Solar.ComponentListDataListDTO.newBuilder();
        List<HDFragments.TagListDTO> tagListDTOList = context.getTagListDTOList();

        if (tagListDTOList.size() > 0) {
            tagListDTOList.stream().forEach(tagListDTO -> {

                Solar.ComponentListData.Builder componentListData = Solar.ComponentListData.newBuilder();
                tagListDTO.getData().getTagsList().stream().forEach(tag -> {

                    Solar.Component.Builder componentBuild = Solar.Component.newBuilder();

                    if (tag.getName().equals(ComponentTypeEnum.DIRECT.getName())) {
                        componentBuild.setType(ComponentTypeEnum.DIRECT.getName());
                        componentBuild.setDirect(transform2DirectComponent(tag, getTagPropertiesByTagId(tag.getId())));
                        componentListData.addComponent(componentBuild);
                    }

                    if (tag.getName().equals(ComponentTypeEnum.ARTICLE_DISEASE_GROUP.getName())) {
                        componentBuild.setType(ComponentTypeEnum.ARTICLE_DISEASE_GROUP.getName());
                        componentBuild.setArticleDiseaseGroup(transform2ArticleGroupComponent(tag, getTagPropertiesByTagId(tag.getId())));
                        componentListData.addComponent(componentBuild);
                    }

                    if (tag.getName().equals(ComponentTypeEnum.SELF_EVALUATION.getName())) {
                        componentBuild.setType(ComponentTypeEnum.SELF_EVALUATION.getName());
                        componentBuild.setSelfEvaluation(transform2SelfEvaluationComponent(tag, getTagPropertiesByTagId(tag.getId())));
                        componentListData.addComponent(componentBuild);

                    }

                    if (tag.getName().equals(ComponentTypeEnum.Recom.getName())) {
                        componentBuild.setType(ComponentTypeEnum.Recom.getName());
                        componentBuild.setRecom(transform2RecomComponent(tag, getTagPropertiesByTagId(tag.getId())));
                        componentListData.addComponent(componentBuild);
                    }

                    if (tag.getName().equals(ComponentTypeEnum.SERVICE_ITEM.getName())) {
                        componentBuild.setType(ComponentTypeEnum.SERVICE_ITEM.getName());
                        componentBuild.setServiceItem(transform2ServiceItem(tag, getTagPropertiesByTagId(tag.getId())));
                        componentListData.addComponent(componentBuild);
                    }


                });
                componentListDataListDTO.addComponentListData(componentListData.build());
            });
        }
        return componentListDataListDTO.build();
    }


    private Solar.ServiceItem transform2ServiceItem(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
        Solar.ServiceItem.Builder serviceItem = Solar.ServiceItem.newBuilder();

        if (tag.hasName()) {
            serviceItem.setTitle(tag.getName());
        }

        if (tag.hasId()) {
            serviceItem.setComponentId(tag.getId());
        }

        if (tag.hasCreatorId()) {
            serviceItem.setCreatorId(tag.getCreatorId());
        }

        if (tag.hasDptId()) {
            serviceItem.setDepartmentId(tag.getDptId());
        }

        if (tagPropertiesDTO.getData().getPropertiesList().size() > 0) {
            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
                if (tagProperty.hasValue()) {
                    JSONObject jsonObject = JSON.parseObject(tagProperty.getValue());
                    serviceItem.setRank(Long.parseLong(jsonObject.get("rank").toString()));
                    jsonObject.getJSONArray("serviceItemId").stream().forEach(serviceItemId -> {
                        serviceItem.addServiceItemId(Long.parseLong(serviceItemId.toString()));
                    });
                }
            });
        }
        return serviceItem.build();
    }

    private Solar.Recom transform2RecomComponent(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
        Solar.Recom.Builder recomBuild = Solar.Recom.newBuilder();

        if (tag.hasName()) {
            recomBuild.setTitle(tag.getName());
        }

        if (tag.hasId()) {
            recomBuild.setComponentId(tag.getId());
        }

        if (tag.hasCreatorId()) {
            recomBuild.setCreatorId(tag.getCreatorId());
        }

        if (tag.hasDptId()) {
            recomBuild.setDepartmentId(tag.getDptId());
        }

        if (tagPropertiesDTO.getData().getPropertiesList().size() > 0) {
            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
                if (tagProperty.hasValue()) {
                    JSONObject jsonObject = JSON.parseObject(tagProperty.getValue());
                    recomBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));
                    jsonObject.getJSONArray("articleId").stream().forEach(articleId -> {
                        recomBuild.addArticleId(Long.parseLong(articleId.toString()));
                    });
                }
            });
        }
        return recomBuild.build();
    }

    private Solar.SelfEvaluation transform2SelfEvaluationComponent(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
        Solar.SelfEvaluation.Builder selfEvaluationBuild = Solar.SelfEvaluation.newBuilder();

        if (tag.hasName()) {
            selfEvaluationBuild.setTitle(tag.getName());
        }

        if (tag.hasId()) {
            selfEvaluationBuild.setComponentId(tag.getId());
        }

        if (tag.hasCreatorId()) {
            selfEvaluationBuild.setCreatorId(tag.getCreatorId());
        }

        if (tag.hasDptId()) {
            selfEvaluationBuild.setDepartmentId(tag.getDptId());
        }

        if (tagPropertiesDTO.getData().getPropertiesList().size() > 0) {
            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
                if (tagProperty.hasValue()) {
                    JSONObject jsonObject = JSON.parseObject(tagProperty.getValue());
                    selfEvaluationBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));
                    jsonObject.getJSONArray("selfEvaluationId").stream().forEach(selfEvaluationId -> {
                        selfEvaluationBuild.addSelfEvaluationId(Long.parseLong(selfEvaluationId.toString()));
                    });
                }
            });
        }
        return selfEvaluationBuild.build();
    }

    private Solar.ArticleDiseaseGroup transform2ArticleGroupComponent(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
        Solar.ArticleDiseaseGroup.Builder articleDiseaseGroupBuild = Solar.ArticleDiseaseGroup.newBuilder();

        if (tag.hasName()) {
            articleDiseaseGroupBuild.setTitle(tag.getName());
        }

        if (tag.hasId()) {
            articleDiseaseGroupBuild.setComponentId(tag.getId());
        }

        if (tag.hasCreatorId()) {
            articleDiseaseGroupBuild.setCreatorId(tag.getCreatorId());
        }

        if (tag.hasDptId()) {
            articleDiseaseGroupBuild.setDepartmentId(tag.getDptId());
        }

        if (tagPropertiesDTO.getData().getPropertiesList().size() > 0) {
            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
                if (tagProperty.hasValue()) {
                    JSONObject jsonObject = JSON.parseObject(tagProperty.getValue());
                    articleDiseaseGroupBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));
                    jsonObject.getJSONArray("group").stream().forEach(json -> {
                        articleDiseaseGroupBuild.addGroup(SolarHalper.parseToSolarGroup(json.toString()));
                    });
                }
            });
        }
        return articleDiseaseGroupBuild.build();
    }

    private Solar.Direct transform2DirectComponent(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
        Solar.Direct.Builder directBuild = Solar.Direct.newBuilder();

        if (tag.hasName()) {
            directBuild.setTitle(tag.getName());
        }
        if (tag.hasId()) {
            directBuild.setComponentId(tag.getId());
        }
        if (tag.hasCreatorId()) {
            directBuild.setCreatorId(tag.getCreatorId());
        }
        if (tag.hasDptId()) {
            directBuild.setDepartmentId(tag.getDptId());
        }
        if (tagPropertiesDTO.getData().getPropertiesList().size() > 0) {
            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
                if (tagProperty.hasValue()) {
                    JSONObject jsonObject = JSONObject.parseObject(tagProperty.getValue());
                    directBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));
                    jsonObject.getJSONArray("unit").stream().forEach(json -> {
                        directBuild.addUnit(SolarHalper.parseToSolarUnit(json.toString()));
                    });
                }
            });
        }
        return directBuild.build();
    }

    public Solar.ArticleDiseaseGroupDTO buildArticleDiseaseGroupDTO(ArticleDiseaseComponentQueryContext context) {

        HDFragments.TagDTO tagDTO = context.getTagDTO();
        Solar.ArticleDiseaseGroupDTO.Builder articleDiseaseGroupDTOBuild = Solar.ArticleDiseaseGroupDTO.newBuilder();
        return articleDiseaseGroupDTOBuild
                .setArticleDiseaseGroup(transform2ArticleGroupComponent(tagDTO.getData().getTag(), getTagPropertiesByTagId(tagDTO.getData().getTag().getId())))
                .setResult(COMMON.Result.newBuilder().setCode(200).setDisplaymsg("success")).build();
    }


    public Solar.SelfEvaluationComponentDTO buildSelfEvaluationDTO(SelfEvaluationComponentQueryContext context) {

        HDFragments.TagDTO tagDTO = context.getTagDTO();
        Solar.SelfEvaluationComponentDTO.Builder selfEvaluationComponentBuild = Solar.SelfEvaluationComponentDTO.newBuilder();
        return selfEvaluationComponentBuild
                .setSelfEvaluation(transform2SelfEvaluationComponent(tagDTO.getData().getTag(), getTagPropertiesByTagId(tagDTO.getData().getTag().getId())))
                .setResult(COMMON.Result.newBuilder().setCode(200).setDisplaymsg("success")).build();
    }

    public Solar.ServiceItemComponentDTO buildServiceItemDTO(ServiceItemComponentQueryContext context) {
        HDFragments.TagDTO tagDTO = context.getTagDTO();
        Solar.ServiceItemComponentDTO.Builder serviceItemBuild = Solar.ServiceItemComponentDTO.newBuilder();
        return serviceItemBuild.
                setServiceItem(transform2ServiceItem(tagDTO.getData().getTag(), getTagPropertiesByTagId(tagDTO.getData().getTag().getId())))
                .setResult(COMMON.Result.newBuilder().setCode(200).setDisplaymsg("success")).build();

    }

    public Solar.RecomComponentDTO buildRecomComponentDTO(RecomComponentQueryContext context) {
        HDFragments.TagDTO tagDTO = context.getTagDTO();
        Solar.RecomComponentDTO.Builder recomBuild = Solar.RecomComponentDTO.newBuilder();
        return recomBuild
                .setRecom(transform2RecomComponent(tagDTO.getData().getTag(), getTagPropertiesByTagId(tagDTO.getData().getTag().getId())))
                .setResult(COMMON.Result.newBuilder().setDisplaymsg("success").setCode(200)).build();
    }

    private HDFragments.TagPropertiesDTO getTagPropertiesByTagId(Long tagId) {
        return hdFragmentsServiceWrap.getTagPropertiesByTagId(tagId);
    }
}
