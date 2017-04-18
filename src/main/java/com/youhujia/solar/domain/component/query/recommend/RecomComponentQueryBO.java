package com.youhujia.solar.domain.component.query.recommend;

import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class RecomComponentQueryBO {

    @Autowired
    RecomComponentQueryContextFactory recomComponentQueryContextFactory;

    public Solar.RecomComponentDTO buildRecomComponent(HDFragments.TagDTO tagDTO) {
        RecomComponentQueryContext context = recomComponentQueryContextFactory.buildRecomComponentQueryContext(tagDTO);
        return context.getRecomComponentDTO();
    }
}
