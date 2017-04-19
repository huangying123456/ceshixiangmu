package com.youhujia.solar.domain.component.query.articleDisease;

import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.solar.Solar;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class ArticleDiseaseComponentQueryContext {

    private HDFragments.TagDTO tagDTO;

    public HDFragments.TagDTO getTagDTO() {
        return tagDTO;
    }

    public void setTagDTO(HDFragments.TagDTO tagDTO) {
        this.tagDTO = tagDTO;
    }
}
