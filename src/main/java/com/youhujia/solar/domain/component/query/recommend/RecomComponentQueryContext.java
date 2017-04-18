package com.youhujia.solar.domain.component.query.recommend;

import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.solar.Solar;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class RecomComponentQueryContext {

    private HDFragments.TagDTO tagDTO;

    private Solar.RecomComponentDTO recomComponentDTO;

    public HDFragments.TagDTO getTagDTO() {
        return tagDTO;
    }

    public void setTagDTO(HDFragments.TagDTO tagDTO) {
        this.tagDTO = tagDTO;
    }

    public Solar.RecomComponentDTO getRecomComponentDTO() {
        return recomComponentDTO;
    }

    public void setRecomComponentDTO(Solar.RecomComponentDTO recomComponentDTO) {
        this.recomComponentDTO = recomComponentDTO;
    }
}
