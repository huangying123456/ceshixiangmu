package com.youhujia.solar.domain.component.query.recommend;

import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class RecomComponentDTOFactory {

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    public Solar.RecomComponentDTO buildRecomComponentDTO(RecomComponentQueryContext context) {

//        HDFragments.TagDTO tagDTO = context.getTagDTO();
//        HDFragments.TagPropertiesDTO tagPropertiesDTO = hdFragmentsServiceWrap.getTagPropertiesByTagId(tagDTO.getData().getTag().getId());
//        return transform2RecomComponentDTO(tagDTO.getData().getTag(), tagPropertiesDTO);
        return null;
    }

    private Solar.RecomComponentDTO transform2RecomComponentDTO(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
//        Solar.Recom.Builder recomBuild = Solar.Recom.newBuilder();
//        Solar.RecomComponentDTO.Builder recomComponentBuild = Solar.RecomComponentDTO.newBuilder();
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
//        return recomComponentBuild
//                .setRecom(recomBuild.build())
//                .setResult(COMMON.Result.newBuilder().setCode(200).setDisplaymsg("success")).build();
        return null;
    }

}
