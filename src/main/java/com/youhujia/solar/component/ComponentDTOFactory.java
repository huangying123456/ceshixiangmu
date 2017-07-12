package com.youhujia.solar.component;

import com.alibaba.fastjson.JSONObject;
import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.solar.ComponentTypeEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.halo.util.ResponseUtil;
import com.youhujia.solar.common.SolarHelper;
import com.youhujia.solar.component.query.articleDisease.ArticleDiseaseComponentQueryContext;
import com.youhujia.solar.component.query.articleList.ArticleListComponentQueryContext;
import com.youhujia.solar.component.query.componentList.ComponentListQueryContext;
import com.youhujia.solar.component.query.recommend.RecomComponentQueryContext;
import com.youhujia.solar.component.query.selfEvaluation.SelfEvaluationComponentQueryContext;
import com.youhujia.solar.component.query.serviceItem.ServiceItemComponentQueryContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ljm on 2017/4/19.
 */

@Component
public class ComponentDTOFactory {
    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    public Solar.ComponentListDataListDTO buildComponentListDTO(ComponentListQueryContext context) {
        Solar.ComponentListDataListDTO.Builder componentListDataListDTO = Solar.ComponentListDataListDTO.newBuilder();
        Map<Long, List<HDFragments.TagAndProperty>> dtpIdTagAndPropertyListDic = context.getDtpIdTagAndPropertyListDic();
        dtpIdTagAndPropertyListDic.forEach((k, v) -> {
            Solar.ComponentListData.Builder componentListData = Solar.ComponentListData.newBuilder();
            componentListData.setDepartmentId(k);
            componentListData.addAllComponent(buildComponentList(v, context));
            componentListDataListDTO.addComponentListData(componentListData);
        });
        return componentListDataListDTO.setResult(ResponseUtil.resultOK()).build();
    }

    private List<Solar.Component> buildComponentList(List<HDFragments.TagAndProperty> tagAndProperties, ComponentListQueryContext context) {
        List<Solar.Component> list = new ArrayList<>();
        tagAndProperties.forEach(tagAndProperty -> {
            Solar.Component.Builder componentBuild = Solar.Component.newBuilder();
            if (tagAndProperty.getPropertiesList().size() != 0) {

                tagAndProperty.getPropertiesList().forEach(tagProperty -> {
                    JSONObject jsonObject = JSONObject.parseObject(tagProperty.getValue());
                    Integer categoryId = (Integer) jsonObject.get("categoryId");

                    if (context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.ARTICLE_GROUP.getName()) != null &&
                            context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.ARTICLE_GROUP.getName()) == categoryId.longValue()) {
                        componentBuild.setType(ComponentTypeEnum.ARTICLE_GROUP.getName());
                        componentBuild.setArticleGroup(transform2ArticleGroupComponent(tagAndProperty.getTag(), jsonObject));
                    }
                    if (context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.DIRECT.getName()) != null &&
                            context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.DIRECT.getName()) == categoryId.longValue()) {
                        componentBuild.setType(ComponentTypeEnum.DIRECT.getName());
                        componentBuild.setDirect(transform2DirectComponent(tagAndProperty.getTag(), jsonObject));
                    }
                    if (context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.SELF_EVALUATION.getName()) != null &&
                            context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.SELF_EVALUATION.getName()) == categoryId.longValue()) {
                        componentBuild.setType(ComponentTypeEnum.SELF_EVALUATION.getName());
                        componentBuild.setSelfEvaluation(transform2SelfEvaluationComponent(tagAndProperty.getTag(), jsonObject));
                    }
                    if (context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.SERVICE_ITEM.getName()) != null &&
                            context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.SERVICE_ITEM.getName()) == categoryId.longValue()) {
                        componentBuild.setType(ComponentTypeEnum.SERVICE_ITEM.getName());
                        componentBuild.setServiceItem(transform2ServiceItem(tagAndProperty.getTag(), jsonObject));
                    }
                    if (context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.Recom.getName()) != null &&
                            context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.Recom.getName()) == categoryId.longValue()) {
                        componentBuild.setType(ComponentTypeEnum.Recom.getName());
                        componentBuild.setRecom(transform2RecomComponent(tagAndProperty.getTag(), jsonObject));
                    }
                    if (context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.ARTICLE_LIST.getName()) != null &&
                            context.getComponentNameCategoryIdDic().get(ComponentTypeEnum.ARTICLE_LIST.getName()) == categoryId.longValue()) {
                        componentBuild.setType(ComponentTypeEnum.ARTICLE_LIST.getName());
                        componentBuild.setArticleList(transform2ArticleListComponent(tagAndProperty.getTag(), jsonObject));
                    }
                });
            }
            list.add(componentBuild.build());
        });
        return list;
    }

    private Solar.Recom transform2RecomComponent(HDFragments.Tag tag, JSONObject jsonObject) {
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
        recomBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));

        JSONObject.parseArray(jsonObject.get("articleId").toString()).stream().forEach(articleId -> {
            recomBuild.addArticleId(Long.parseLong(articleId.toString()));
        });

        return recomBuild.build();
    }

    private Solar.ServiceItem transform2ServiceItem(HDFragments.Tag tag, JSONObject jsonObject) {
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

        serviceItem.setRank(Long.parseLong(jsonObject.get("rank").toString()));

        JSONObject.parseArray(jsonObject.get("serviceItemId").toString()).stream().forEach(serviceItemId -> {
            serviceItem.addServiceItemId(Long.parseLong(serviceItemId.toString()));
        });

        return serviceItem.build();
    }

    private Solar.SelfEvaluation transform2SelfEvaluationComponent(HDFragments.Tag tag, JSONObject jsonObject) {

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

        selfEvaluationBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));

        JSONObject.parseArray(jsonObject.get("selfEvaluationId").toString()).stream().forEach(selfEvaluationId -> {
            selfEvaluationBuild.addSelfEvaluationId(Long.parseLong(selfEvaluationId.toString()));
        });
        return selfEvaluationBuild.build();
    }

    private Solar.Direct transform2DirectComponent(HDFragments.Tag tag, JSONObject jsonObject) {

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
        directBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));

        JSONObject.parseArray(jsonObject.get("unit").toString()).stream().forEach(unit -> {
            directBuild.addUnit(SolarHelper.parseToSolarUnit(unit.toString()));
        });
        return directBuild.build();
    }

    private Solar.ArticleGroup transform2ArticleGroupComponent(HDFragments.Tag tag, JSONObject jsonObject) {

        Solar.ArticleGroup.Builder articleDiseaseGroupBuild = Solar.ArticleGroup.newBuilder();

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

        articleDiseaseGroupBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));

        JSONObject.parseArray(jsonObject.get("group").toString()).stream().forEach(group -> {
            articleDiseaseGroupBuild.addGroup(SolarHelper.parseToSolarGroup(group.toString()));
        });
        return articleDiseaseGroupBuild.build();
    }

    public Solar.ArticleGroupDTO buildArticleDiseaseGroupDTO(ArticleDiseaseComponentQueryContext context) {

        HDFragments.TagDTO tagDTO = context.getTagDTO();
        Solar.ArticleGroupDTO.Builder articleDiseaseGroupDTOBuild = Solar.ArticleGroupDTO.newBuilder();

        HDFragments.TagPropertiesDTO tagPropertiesDTO = getTagPropertiesByTagId(tagDTO.getData().getTag().getId());

        if (tagPropertiesDTO.getData().getPropertiesList().size() != 0) {
            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
                JSONObject jsonObject = JSONObject.parseObject(tagProperty.getValue());
                HDFragments.TagDTO tag = hdFragmentsServiceWrap.getTagById(Long.parseLong(jsonObject.get("categoryId").toString()));
                if (!tag.getData().getTag().getName().equals(ComponentTypeEnum.ARTICLE_GROUP.getName())) {
                    throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "组件类别不符");
                }
                articleDiseaseGroupDTOBuild.setArticleGroup(transform2ArticleGroupComponent(tagDTO.getData().getTag(), jsonObject));
            });
        }
        return articleDiseaseGroupDTOBuild.setResult(ResponseUtil.resultOK()).build();
    }


    public Solar.SelfEvaluationComponentDTO buildSelfEvaluationDTO(SelfEvaluationComponentQueryContext context) {

        HDFragments.TagDTO tagDTO = context.getTagDTO();
        Solar.SelfEvaluationComponentDTO.Builder selfEvaluationComponentBuild = Solar.SelfEvaluationComponentDTO.newBuilder();

        HDFragments.TagPropertiesDTO tagPropertiesDTO = getTagPropertiesByTagId(tagDTO.getData().getTag().getId());

        if (tagPropertiesDTO.getData().getPropertiesList().size() != 0) {
            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
                JSONObject jsonObject = JSONObject.parseObject(tagProperty.getValue());
                HDFragments.TagDTO tag = hdFragmentsServiceWrap.getTagById(Long.parseLong(jsonObject.get("categoryId").toString()));
                if (!tag.getData().getTag().getName().equals(ComponentTypeEnum.SELF_EVALUATION.getName())) {
                    throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "组件类别不符");
                }
                selfEvaluationComponentBuild.setSelfEvaluation(transform2SelfEvaluationComponent(tagDTO.getData().getTag(), jsonObject));
            });
        }
        return selfEvaluationComponentBuild.setResult(ResponseUtil.resultOK()).build();
    }

    public Solar.ServiceItemComponentDTO buildServiceItemDTO(ServiceItemComponentQueryContext context) {
        HDFragments.TagDTO tagDTO = context.getTagDTO();
        Solar.ServiceItemComponentDTO.Builder serviceItemBuild = Solar.ServiceItemComponentDTO.newBuilder();

        HDFragments.TagPropertiesDTO tagPropertiesDTO = getTagPropertiesByTagId(tagDTO.getData().getTag().getId());

        if (tagPropertiesDTO.getData().getPropertiesList().size() != 0) {
            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
                JSONObject jsonObject = JSONObject.parseObject(tagProperty.getValue());
                HDFragments.TagDTO tag = hdFragmentsServiceWrap.getTagById(Long.parseLong(jsonObject.get("categoryId").toString()));
                if (!tag.getData().getTag().getName().equals(ComponentTypeEnum.SERVICE_ITEM.getName())) {
                    throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "组件类别不符");
                }
                serviceItemBuild.setServiceItem(transform2ServiceItem(tagDTO.getData().getTag(), jsonObject));
            });
        }
        return serviceItemBuild.setResult(ResponseUtil.resultOK()).build();

    }

    public Solar.RecomComponentDTO buildRecomComponentDTO(RecomComponentQueryContext context) {
        HDFragments.TagDTO tagDTO = context.getTagDTO();
        Solar.RecomComponentDTO.Builder recomBuild = Solar.RecomComponentDTO.newBuilder();

        HDFragments.TagPropertiesDTO tagPropertiesDTO = getTagPropertiesByTagId(tagDTO.getData().getTag().getId());

        if (tagPropertiesDTO.getData().getPropertiesList().size() != 0) {
            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
                JSONObject jsonObject = JSONObject.parseObject(tagProperty.getValue());
                HDFragments.TagDTO tag = hdFragmentsServiceWrap.getTagById(Long.parseLong(jsonObject.get("categoryId").toString()));
                if (!tag.getData().getTag().getName().equals(ComponentTypeEnum.Recom.getName())) {
                    throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "组件类别不符");
                }
                recomBuild.setRecom(transform2RecomComponent(tagDTO.getData().getTag(), jsonObject));
            });
        }
        return recomBuild.setResult(ResponseUtil.resultOK()).build();
    }

    private HDFragments.TagPropertiesDTO getTagPropertiesByTagId(Long tagId) {
        return hdFragmentsServiceWrap.getTagPropertiesByTagId(tagId);
    }


    public Solar.ArticleListComponentDTO buildArticleListComponentDTO(ArticleListComponentQueryContext context) {
        HDFragments.TagDTO tagDTO = context.getTagDTO();
        Solar.ArticleListComponentDTO.Builder articleListComponentBuild = Solar.ArticleListComponentDTO.newBuilder();
        if (tagDTO != null && tagDTO.getData().getTag() != null) {
            HDFragments.TagPropertiesDTO tagPropertiesDTO = getTagPropertiesByTagId(tagDTO.getData().getTag().getId());

            if (tagPropertiesDTO.getData().getPropertiesList().size() != 0) {
                tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
                    JSONObject jsonObject = JSONObject.parseObject(tagProperty.getValue());
                    HDFragments.TagDTO tag = hdFragmentsServiceWrap.getTagById(Long.parseLong(jsonObject.get("categoryId").toString()));
                    if (!tag.getData().getTag().getName().equals(ComponentTypeEnum.ARTICLE_LIST.getName())) {
                        throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "组件类别不符");
                    }
                    articleListComponentBuild.setArticleList(transform2ArticleListComponent(tagDTO.getData().getTag(), jsonObject));
                });
            }
        }
        return articleListComponentBuild.setResult(ResponseUtil.resultOK()).build();
    }

    private Solar.ArticleList transform2ArticleListComponent(HDFragments.Tag tag, JSONObject jsonObject) {

        Solar.ArticleList.Builder articleListBuild = Solar.ArticleList.newBuilder();

        if (tag.hasName()) {
            articleListBuild.setTitle(tag.getName());
        }

        if (tag.hasId()) {
            articleListBuild.setComponentId(tag.getId());
        }

        if (tag.hasCreatorId()) {
            articleListBuild.setCreatorId(tag.getCreatorId());
        }

        if (tag.hasDptId()) {
            articleListBuild.setDepartmentId(tag.getDptId());
        }

        if (StringUtils.isNotBlank(jsonObject.get("articleId").toString())) {
            JSONObject.parseArray(jsonObject.get("articleId").toString()).stream().forEach(articleId -> {
                articleListBuild.addArticleId(Long.parseLong(articleId.toString()));
            });
        }

        articleListBuild.setRank(Long.parseLong(jsonObject.get("rank").toString()));

        return articleListBuild.build();
    }
}
