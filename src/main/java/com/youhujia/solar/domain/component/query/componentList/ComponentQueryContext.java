package com.youhujia.solar.domain.component.query.componentList;

import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.solar.Solar;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ljm on 2017/4/17.
 */
@Component
public class ComponentQueryContext {

    private HDFragments.TagListDTO tagListDTO;

    private HDFragments.TagDTO tagDTO;

    private List<HDFragments.TagListDTO> tagListDTOList;

    private Solar.DepartmentIdListOption departmentIdListOption;

    private Solar.ComponentListDataListDTO componentListDataListDTO;

    public HDFragments.TagListDTO getTagListDTO() {
        return tagListDTO;
    }

    public void setTagListDTO(HDFragments.TagListDTO tagListDTO) {
        this.tagListDTO = tagListDTO;
    }

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

    public Solar.DepartmentIdListOption getDepartmentIdListOption() {
        return departmentIdListOption;
    }

    public void setDepartmentIdListOption(Solar.DepartmentIdListOption departmentIdListOption) {
        this.departmentIdListOption = departmentIdListOption;
    }

    public Solar.ComponentListDataListDTO getComponentListDataListDTO() {
        return componentListDataListDTO;
    }

    public void setComponentListDataListDTO(Solar.ComponentListDataListDTO componentListDataListDTO) {
        this.componentListDataListDTO = componentListDataListDTO;
    }
}
