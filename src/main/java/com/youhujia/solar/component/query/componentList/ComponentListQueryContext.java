package com.youhujia.solar.component.query.componentList;

import com.youhujia.halo.hdfragments.HDFragments;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ljm on 2017/4/17.
 */
@Component
public class ComponentListQueryContext {


    private HDFragments.TagDTO tagDTO;

    private List<HDFragments.TagListDTO> tagListDTOList;

    public HDFragments.TagDTO getTagDTO() {
        return tagDTO;
    }

    public void setTagDTO(HDFragments.TagDTO tagDTO) {
        this.tagDTO = tagDTO;
    }

    public List<HDFragments.TagListDTO> getTagListDTOList() {
        return tagListDTOList;
    }

    public void setTagListDTOList(List<HDFragments.TagListDTO> tagListDTOList) {
        this.tagListDTOList = tagListDTOList;
    }

}
