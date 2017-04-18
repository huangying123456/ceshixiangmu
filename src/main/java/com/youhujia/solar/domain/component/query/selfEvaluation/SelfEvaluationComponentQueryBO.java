package com.youhujia.solar.domain.component.query.selfEvaluation;

import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */

@Component
public class SelfEvaluationComponentQueryBO {
    @Autowired
    SelfEvaluationComponentQueryContextFactory selfEvaluationComponentQueryContextFactory;

    public Solar.SelfEvaluationComponentDTO buildSelfEvaluationDTO(HDFragments.TagDTO tagDTO) {
        SelfEvaluationComponentQueryContext context = selfEvaluationComponentQueryContextFactory.buildSelfEvaluationComponentQueryContext(tagDTO);
        return context.getSelfEvaluationComponent();
    }
}
