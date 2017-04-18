package com.youhujia.solar.domain.component.query.selfEvaluation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */

@Component

public class SelfEvaluationComponentQueryContextFactory {

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    public SelfEvaluationComponentQueryContext buildSelfEvaluationComponentQueryContext(HDFragments.TagDTO tagDTO) {
        SelfEvaluationComponentQueryContext context = new SelfEvaluationComponentQueryContext();
        HDFragments.TagPropertiesDTO tagPropertiesDTO = hdFragmentsServiceWrap.getTagPropertiesByTagId(tagDTO.getData().getTag().getId());
        context.setSelfEvaluationComponent(transform2SelfEvaluationComponent(tagDTO.getData().getTag(),tagPropertiesDTO));
        return context;
    }

    private Solar.SelfEvaluationComponentDTO transform2SelfEvaluationComponent(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {

        Solar.SelfEvaluation.Builder selfEvaluationBuild = Solar.SelfEvaluation.newBuilder();
        Solar.SelfEvaluationComponentDTO.Builder selfEvaluationComponentDTO = Solar.SelfEvaluationComponentDTO.newBuilder();

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
                    String[] str = jsonObject.get("selfEvaluationId").toString().split(",");
                    for (String selfEvaluationId : str) {
                        selfEvaluationBuild.addSelfEvaluationId(Long.parseLong(selfEvaluationId));
                    }
                }
            });
        }
        return selfEvaluationComponentDTO
                .setSelfEvaluation(selfEvaluationBuild.build())
                .setResult(COMMON.Result.newBuilder().setCode(200).setDisplaymsg("success")).build();
    }

}
