package com.youhujia.solar.component.query.componentList;

import com.youhujia.halo.hdfragments.HDFragments;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by ljm on 2017/4/17.
 */
@Component
public class ComponentListQueryContext {
    private HDFragments.TagDTO tagDTO;
    private List<HDFragments.TagListDTO> tagListDTOList;
    private Map<Long,List<HDFragments.TagAndProperty>> dtpIdTagAndPropertyListDic;
    private Map<Long,HDFragments.Tag> tagIdCategoryTagDic;
    private Map<String,Long> ComponentNameCategoryIdDic;

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

    public Map<Long, List<HDFragments.TagAndProperty>> getDtpIdTagAndPropertyListDic() {
        return dtpIdTagAndPropertyListDic;
    }

    public void setDtpIdTagAndPropertyListDic(Map<Long, List<HDFragments.TagAndProperty>> dtpIdTagAndPropertyListDic) {
        this.dtpIdTagAndPropertyListDic = dtpIdTagAndPropertyListDic;
    }

    public Map<Long, HDFragments.Tag> getTagIdCategoryTagDic() {
        return tagIdCategoryTagDic;
    }

    public void setTagIdCategoryTagDic(Map<Long, HDFragments.Tag> tagIdCategoryTagDic) {
        this.tagIdCategoryTagDic = tagIdCategoryTagDic;
    }

    public Map<String, Long> getComponentNameCategoryIdDic() {
        return ComponentNameCategoryIdDic;
    }

    public void setComponentNameCategoryIdDic(Map<String, Long> componentNameCategoryIdDic) {
        ComponentNameCategoryIdDic = componentNameCategoryIdDic;
    }
}
