package com.youhujia.solar.domain.component.query.componentList;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.hdfragments.HDFragmentsTagQueryEnum;
import com.youhujia.halo.hdfragments.HDFragmentsTagTypeEnum;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ljm on 2017/4/17.
 */

@Component
public class ComponentListQueryBO {

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    @Autowired
    DepartmentDAO departmentDAO;

    public ComponentListQueryContext batchComponentListByDepartmentIds(String ids) {
        String[] str = ids.split(",");

        if (str.length == 0) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "部门编号为空");
        }
        ComponentListQueryContext context = new ComponentListQueryContext();
        Map<String, String> queryParam = new HashMap<>();
        List<HDFragments.TagListDTO> tagListDTOList = new ArrayList<>();
        for (String departmentId : str) {
            queryParam.put(HDFragmentsTagQueryEnum.DEPARTMENT_ID.getName(), Long.valueOf(departmentId).toString());
            queryParam.put(HDFragmentsTagQueryEnum.TAG_TYPE.getName(), Long.valueOf(HDFragmentsTagTypeEnum.UI_CONFIG.getType()).toString());
            HDFragments.TagListDTO tagListDTO = hdFragmentsServiceWrap.getTags(queryParam);

            if (tagListDTO.getData().getTagsList().size() == 0) {
                Department department = departmentDAO.findOne(Long.parseLong(departmentDAO.findOne(Long.parseLong(departmentId)).getClassificationType()));
                queryParam.put(HDFragmentsTagQueryEnum.DEPARTMENT_ID.getName(), Long.valueOf(department.getId()).toString());
                HDFragments.TagListDTO temPlateTagListDTO = hdFragmentsServiceWrap.getTags(queryParam);
                tagListDTOList.add(temPlateTagListDTO);
                continue;
            }
            tagListDTOList.add(tagListDTO);
        }
        context.setTagListDTOList(tagListDTOList);
        return context;
    }
}
