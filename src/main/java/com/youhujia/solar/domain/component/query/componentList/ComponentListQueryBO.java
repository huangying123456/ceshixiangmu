package com.youhujia.solar.domain.component.query.componentList;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.hdfragments.TagQueryEnum;
import com.youhujia.halo.hdfragments.TagTypeEnum;
import com.youhujia.halo.solar.Solar;
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

    public ComponentListQueryContext batchComponentListByDepartmentIds(Solar.DepartmentIdListOption option) {

        if (option.getDepartmentIdList().size() == 0) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "部门编号为空");
        }
        ComponentListQueryContext context = new ComponentListQueryContext();
        Map<String, String> queryParam = new HashMap<>();
        List<HDFragments.TagListDTO> tagListDTOList = new ArrayList<>();
        option.getDepartmentIdList().stream().forEach(departmentId -> {

            queryParam.put(TagQueryEnum.DEPARTMENT_ID.getName(), Long.valueOf(departmentId).toString());
            queryParam.put(TagQueryEnum.TAG_TYPE.getName(), Long.valueOf(TagTypeEnum.UI_VIEW.getType()).toString());
            HDFragments.TagListDTO tagListDTO = hdFragmentsServiceWrap.getTags(queryParam);
            tagListDTOList.add(tagListDTO);

        });
        context.setTagListDTOList(tagListDTOList);
        return context;
    }
}
