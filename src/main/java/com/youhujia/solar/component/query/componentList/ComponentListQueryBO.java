package com.youhujia.solar.component.query.componentList;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.hdfragments.HDFragmentsTagQueryEnum;
import com.youhujia.halo.hdfragments.HDFragmentsTagTypeEnum;
import com.youhujia.solar.department.Department;
import com.youhujia.solar.department.DepartmentDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
        List<HDFragments.TagListDTO> tagListDTOList = new ArrayList<>();

        for (String departmentId : str) {

            HDFragments.TagListDTO tagListDTO = getTagListByDeptIdAndType(Long.parseLong(departmentId));

            //如果该科室没有ui配置，获取它所属的模板科室ui
            if (tagListDTO.getData().getTagsList().size() == 0) {
                Department realDepartment = departmentDAO.findOne(Long.parseLong(departmentId));

                if (StringUtils.isNotBlank(realDepartment.getClassificationType())) {
                    Department temPlateDepartment = departmentDAO.findOne(Long.parseLong(realDepartment.getClassificationType()));
                    HDFragments.TagListDTO temPlateTagListDTO = getTagListByDeptIdAndType(temPlateDepartment.getId());

                    HDFragments.TagListDTO.Builder tdb = HDFragments.TagListDTO.newBuilder();
                    HDFragments.TagListData.Builder tb = HDFragments.TagListData.newBuilder();

                    temPlateTagListDTO.getData().getTagsList().stream().forEach(tag -> {
                        HDFragments.Tag.Builder tagBuild = HDFragments.Tag.newBuilder();
                        BeanUtils.copyProperties(tag, tagBuild);
                        tagBuild.setDptId(realDepartment.getId());
                        tb.addTags(tagBuild);
                    });
                    tdb.setData(tb.build());
                    tagListDTOList.add(tdb.build());
                }
                //如果该科室没有UI配置，且没有模板科室，跳过该科室
                continue;
            }
            tagListDTOList.add(tagListDTO);
        }
        context.setTagListDTOList(tagListDTOList);
        return context;
    }

    private HDFragments.TagListDTO getTagListByDeptIdAndType(Long departmentId) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put(HDFragmentsTagQueryEnum.DEPARTMENT_ID.getName(), Long.valueOf(departmentId).toString());
        queryParam.put(HDFragmentsTagQueryEnum.TAG_TYPE.getName(), Long.valueOf(HDFragmentsTagTypeEnum.UI_CONFIG.getType()).toString());
        return hdFragmentsServiceWrap.getTags(queryParam);
    }

}
