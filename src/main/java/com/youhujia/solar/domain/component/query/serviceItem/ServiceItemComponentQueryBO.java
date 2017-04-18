package com.youhujia.solar.domain.component.query.serviceItem;

import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class ServiceItemComponentQueryBO {

    @Autowired
    ServiceItemComponentQueryContextFactory serviceItemComponentQueryContextFactory;

    public Solar.ServiceItemComponentDTO buildServiceItemComponent(HDFragments.TagDTO tagDTO) {
        ServiceItemComponentQueryContext context = serviceItemComponentQueryContextFactory.buildServiceItemComponentQueryContext(tagDTO);
        return context.getServiceItemComponentDTO();
    }
}
