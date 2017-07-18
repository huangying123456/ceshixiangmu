package com.youhujia.solar.component.query.articleList;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Lilac
 */

@Component
public class ArticleListComponentQueryBO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    public ArticleListComponentQueryContext getArticleListComponentById(Long componentId) {
        ArticleListComponentQueryContext context = new ArticleListComponentQueryContext();
        context.setTagDTO(getTagById(componentId));
        return context;
    }

    private HDFragments.TagDTO getTagById(Long componentId) {
        String where = "ArticleListComponentQueryBO -> getTagById:";
        HDFragments.TagDTO tagDTO = hdFragmentsServiceWrap.getTagById(componentId);
        if (tagDTO.getData().getTag() == null) {
            logger.info(where + "组件不存在，componentId为:" + componentId);
        }
        return tagDTO;
    }
}
