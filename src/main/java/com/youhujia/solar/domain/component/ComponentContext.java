package com.youhujia.solar.domain.component;

import com.youhujia.halo.hdfragments.HDFragments;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ljm on 2017/4/18.
 */

@Component
public class ComponentContext {

    private List<HDFragments.TagListDTO> tagListDTOList;

    private Long componentId;

    public List<HDFragments.TagListDTO> getTagListDTOList() {
        return tagListDTOList;
    }

    public void setTagListDTOList(List<HDFragments.TagListDTO> tagListDTOList) {
        this.tagListDTOList = tagListDTOList;
    }

    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }
}
