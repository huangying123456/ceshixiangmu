package com.youhujia.solar.domain.component.query.selfEvaluation;

import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.solar.Solar;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */

@Component
public class SelfEvaluationComponentQueryContext {

    private HDFragments.TagDTO tagDTO;

    private Solar.SelfEvaluationComponentDTO selfEvaluationComponent;

    public HDFragments.TagDTO getTagDTO() {
        return tagDTO;
    }

    public void setTagDTO(HDFragments.TagDTO tagDTO) {
        this.tagDTO = tagDTO;
    }

    public Solar.SelfEvaluationComponentDTO getSelfEvaluationComponent() {
        return selfEvaluationComponent;
    }

    public void setSelfEvaluationComponent(Solar.SelfEvaluationComponentDTO selfEvaluationComponent) {
        this.selfEvaluationComponent = selfEvaluationComponent;
    }
}
