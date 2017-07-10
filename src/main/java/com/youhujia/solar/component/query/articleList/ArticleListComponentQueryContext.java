package com.youhujia.solar.component.query.articleList;

import com.youhujia.halo.hdfragments.HDFragments;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */

@Component
public class ArticleListComponentQueryContext {

    private HDFragments.TagDTO tagDTO;

    public HDFragments.TagDTO getTagDTO() {
        return tagDTO;
    }

    public void setTagDTO(HDFragments.TagDTO tagDTO) {
        this.tagDTO = tagDTO;
    }

}
