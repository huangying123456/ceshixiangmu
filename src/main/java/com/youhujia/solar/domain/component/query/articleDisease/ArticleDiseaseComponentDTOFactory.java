package com.youhujia.solar.domain.component.query.articleDisease;

import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class ArticleDiseaseComponentDTOFactory {

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    public Solar.ArticleDiseaseGroupDTO buildArticleDiseaseGroupDTO(ArticleDiseaseComponentQueryContext articleDiseaseComponentQueryContext) {

//        HDFragments.TagDTO tagDTO = articleDiseaseComponentQueryContext.getTagDTO();
//        HDFragments.TagPropertiesDTO tagPropertiesDTO = hdFragmentsServiceWrap.getTagPropertiesByTagId(tagDTO.getData().getTag().getId());
//        return transform2ArticleGroupComponent(tagDTO.getData().getTag(),tagPropertiesDTO);
        return null;
    }

    private Solar.ArticleDiseaseGroupDTO transform2ArticleGroupComponent(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
//        Solar.ArticleDiseaseGroupDTO.Builder articleDiseaseGroupDTOBuild = Solar.ArticleDiseaseGroupDTO.newBuilder();
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
//        return articleDiseaseGroupDTOBuild
//                .setArticleDiseaseGroup(articleDiseaseGroupBuild.build())
//                .setResult(COMMON.Result.newBuilder().setCode(200).setDisplaymsg("success")).build();
        return null;
    }

}
