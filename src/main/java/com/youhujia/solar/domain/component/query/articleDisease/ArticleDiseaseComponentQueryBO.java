package com.youhujia.solar.domain.component.query.articleDisease;

import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class ArticleDiseaseComponentQueryBO {

    @Autowired
    ArticleDiseaseComponentQueryContextFactory articleDiseaseComponentQueryFactory;

    public Solar.ArticleDiseaseGroupDTO buildArticleDiseaseGroupDTO(HDFragments.TagDTO tagDTO) {
        ArticleDiseaseComponentQueryContext context = articleDiseaseComponentQueryFactory.buildArticleDiseaseComponentQueryContext(tagDTO);
        return context.getArticleDiseaseGroupDTO();
    }

}
