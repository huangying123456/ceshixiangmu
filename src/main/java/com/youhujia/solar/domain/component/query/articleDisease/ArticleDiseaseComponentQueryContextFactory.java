package com.youhujia.solar.domain.component.query.articleDisease;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.common.SolarHalper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class ArticleDiseaseComponentQueryContextFactory {

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    public ArticleDiseaseComponentQueryContext buildArticleDiseaseComponentQueryContext(HDFragments.TagDTO tagDTO) {

        ArticleDiseaseComponentQueryContext context = new ArticleDiseaseComponentQueryContext();
        HDFragments.TagPropertiesDTO tagPropertiesDTO = hdFragmentsServiceWrap.getTagPropertiesByTagId(tagDTO.getData().getTag().getId());
        context.setArticleDiseaseGroupDTO(transform2ArticleGroupComponent(tagDTO.getData().getTag(),tagPropertiesDTO));
        return context;
    }

    private Solar.ArticleDiseaseGroupDTO transform2ArticleGroupComponent(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
        Solar.ArticleDiseaseGroupDTO.Builder articleDiseaseGroupDTOBuild = Solar.ArticleDiseaseGroupDTO.newBuilder();
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
                    articleDiseaseGroupBuild.addGroup(SolarHalper.parseToSolarGroup(jsonObject.toString()));
                }
            });
        }
        return articleDiseaseGroupDTOBuild
                .setArticleDiseaseGroup(articleDiseaseGroupBuild.build())
                .setResult(COMMON.Result.newBuilder().setCode(200).setDisplaymsg("success")).build();
    }

}
