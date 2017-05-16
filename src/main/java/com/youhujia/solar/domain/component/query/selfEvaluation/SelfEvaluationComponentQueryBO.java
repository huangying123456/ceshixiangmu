package com.youhujia.solar.domain.component.query.selfEvaluation;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.solar.ComponentTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */

@Component
public class SelfEvaluationComponentQueryBO {
    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    public SelfEvaluationComponentQueryContext getSelfEvaluationComponentById(Long componentId) {
        SelfEvaluationComponentQueryContext context = new SelfEvaluationComponentQueryContext();
        context.setTagDTO(getTagById(componentId));
        return context;
    }

    private HDFragments.TagDTO getTagById(Long componentId) {
        HDFragments.TagDTO tagDTO = hdFragmentsServiceWrap.getTagById(componentId);
        if (tagDTO.getData().getTag() == null) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "该组件不存在");
        }
//        if(!tagDTO.getData().getTag().getName().equals(ComponentTypeEnum.SELF_EVALUATION.getName())){
//            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "组件类别不符");
//        }
        return tagDTO;
    }
}
