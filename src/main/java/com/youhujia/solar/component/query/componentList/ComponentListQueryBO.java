package com.youhujia.solar.component.query.componentList;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.hdfragments.HDFragmentsTagQueryEnum;
import com.youhujia.halo.hdfragments.HDFragmentsTagTypeEnum;
import com.youhujia.solar.department.Department;
import com.youhujia.solar.department.DepartmentDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by ljm on 2017/4/17.
 */

@Component
public class ComponentListQueryBO {

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    @Autowired
    DepartmentDAO departmentDAO;

    public static <T> String parseCollectionToString(Iterable<T> iterable) {
        StringBuilder stringBuilder = new StringBuilder();
        if (iterable != null) {
            iterable.forEach(t -> stringBuilder.append(t).append(","));
        }
        return stringBuilder.length() > 0 ? stringBuilder.substring(0, stringBuilder.length() - 1) : stringBuilder.toString();
    }

    public ComponentListQueryContext batchComponentListByDepartmentIds(String ids) {

        String[] str = ids.split(",");

        if (str.length == 0) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "部门编号为空");
        }

        ComponentListQueryContext context = new ComponentListQueryContext();
        Map<Long, List<HDFragments.Tag>> dtpIdTagsDic = new HashMap<>();

        HDFragments.TagListDTO tagListDTO = getTagListByDeptIdsAndType(ids);
        // 如果该科室有ui配置，直接获取它所属的科室ui
        if (tagListDTO.getData().getTagsList().size() != 0) {
            computeUITagByDptId(dtpIdTagsDic, tagListDTO);
        }
        // 如果该科室没有ui配置，获取它所属的模板科室ui
        List<Long> all = parseStringToLongList(ids);
        computeDptIdsNoUI(tagListDTO, all);
        if (all.size() != 0) {
            Map<Long, List<HDFragments.Tag>> dic = getTagListDTOByTemplateDptIds(all);
            if (dic.entrySet().size() != 0) {
                dtpIdTagsDic.putAll(dic);
            }
        }
        buildTagAndProperty(dtpIdTagsDic, context);
        buildComponentTypeCategoryIdDic(context);
        return context;
    }

    private void buildComponentTypeCategoryIdDic(ComponentListQueryContext context) {
        Map<String, Long> componentNameCategoryIdDic = context.getTagIdCategoryTagDic().values().stream()
                .collect(Collectors.toMap(HDFragments.Tag::getName, HDFragments.Tag::getId));
        context.setComponentNameCategoryIdDic(componentNameCategoryIdDic);
    }

    private void buildTagAndProperty(Map<Long, List<HDFragments.Tag>> dtpIdTagsDic, ComponentListQueryContext context) {
        List<Long> tagIds = dtpIdTagsDic.values().stream()
                .flatMap(Collection::stream)
                .map(HDFragments.Tag::getId)
                .collect(Collectors.toList());
        List<Long> categoryTagIds = dtpIdTagsDic.values().stream()
                .flatMap(Collection::stream)
                .filter(tag -> tag.getLevel1() != 0)
                .map(HDFragments.Tag::getLevel1)
                .distinct()
                .collect(Collectors.toList());
        tagIds.addAll(categoryTagIds);


        HDFragments.TagsAndPropertiesDTO tagsAndPropertiesDTO = hdFragmentsServiceWrap.getTagsAndPropertiesByTagIds(parseCollectionToString(tagIds));
        Map<Long, HDFragments.TagAndProperty> tagIdTagAndPropertyDic = tagsAndPropertiesDTO.getTagsList().stream()
                .collect(Collectors.toMap(tagAndProperty -> tagAndProperty.getTag().getId(), Function.identity()));


        Map<Long, List<HDFragments.TagAndProperty>> dtpIdTagAndPropertyListDic = new HashMap<>();
        dtpIdTagsDic.forEach((k, v) -> {
            List<HDFragments.TagAndProperty> tagAndPropertys = new ArrayList<>();
            v.forEach(tag -> {
                tagAndPropertys.add(tagIdTagAndPropertyDic.get(tag.getId()));
            });
            dtpIdTagAndPropertyListDic.put(k, tagAndPropertys);
        });
        context.setDtpIdTagAndPropertyListDic(dtpIdTagAndPropertyListDic);


        Map<Long, HDFragments.Tag> tagIdCategoryTagDic = tagsAndPropertiesDTO.getTagsList().stream()
                .map(HDFragments.TagAndProperty::getTag)
                .filter(tag -> categoryTagIds.contains(tag.getId()))
                .collect(Collectors.toMap(HDFragments.Tag::getId, Function.identity()));
        context.setTagIdCategoryTagDic(tagIdCategoryTagDic);
    }

    // 构建模板科室Id
    private void computeDptIdsNoUI(HDFragments.TagListDTO tagListDTO, List<Long> all) {
        List<Long> dptIdWithUI = tagListDTO.getData().getTagsList().stream()
                .map(tag -> tag.getDptId())
                .distinct()
                .collect(Collectors.toList());

        for (int i = 0; i < dptIdWithUI.size(); i++) {
            if (all.contains(dptIdWithUI.get(i))) {
                all.remove(dptIdWithUI.get(i));
            }
        }
    }

    // 根据科室Id获取UI配置
    private void computeUITagByDptId(Map<Long, List<HDFragments.Tag>> dtpIdTagDic,
                                     HDFragments.TagListDTO tagListDTO) {
        Map<Long, List<HDFragments.Tag>> dic = tagListDTO.getData().getTagsList()
                .stream().collect(Collectors.groupingBy(HDFragments.Tag::getDptId));
        dtpIdTagDic.putAll(dic);
    }

    // 根据模板科室Id获取UI配置
    private void computeUITagByTempateDptId(List<HDFragments.TagListDTO> ret,
                                            Map<Long, HDFragments.Tag> dptIdTagDic) {
        dptIdTagDic.entrySet().stream().forEach(dptIdTag -> {
            HDFragments.TagListDTO.Builder tagListDTOBd = HDFragments.TagListDTO.newBuilder();
            HDFragments.TagListData.Builder tagDataDTO = HDFragments.TagListData.newBuilder();
            HDFragments.Tag.Builder tagBd = HDFragments.Tag.newBuilder();
            BeanUtils.copyProperties(dptIdTag, tagBd);
            tagBd.setDptId(dptIdTag.getKey());
            tagDataDTO.addTags(tagBd.build());
            tagListDTOBd.setData(tagDataDTO.build());
            ret.add(tagListDTOBd.build());
        });
    }

    private Map<Long, List<HDFragments.Tag>> getTagListDTOByTemplateDptIds(List<Long> all) {
        List<Department> dpts = departmentDAO.findByIdIn(all);
        Map<String, List<Department>> templateDptIdAndDptIdDic = dpts.stream()
                .collect(Collectors.groupingBy(Department::getClassificationType));

        HDFragments.TagListDTO tagListDTO = getTagListByDeptIdsAndType(parseCollectionToString(templateDptIdAndDptIdDic.keySet()));
        Map<Long, List<HDFragments.Tag>> templateDptIdTagsdic = tagListDTO.getData().getTagsList()
                .stream().collect(Collectors.groupingBy(HDFragments.Tag::getDptId));

        Map<Long, List<HDFragments.Tag>> dptIdTagsDic = new HashMap<>();

        templateDptIdTagsdic.forEach((k, v) -> {
            List<Department> list = templateDptIdAndDptIdDic.get(k.toString());
            list.forEach(department -> dptIdTagsDic.put(department.getId(), v));
        });

        return dptIdTagsDic;
    }

    private HDFragments.TagListDTO getTagListByDeptIdsAndType(String dptIds) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put(HDFragmentsTagQueryEnum.DEPARTMENT_IDS.getName(), dptIds);
        queryParam.put(HDFragmentsTagQueryEnum.TAG_TYPE.getName(), Long.valueOf(HDFragmentsTagTypeEnum.UI_CONFIG.getType()).toString());
        return hdFragmentsServiceWrap.getTagsByDepIds(queryParam);
    }

    private HDFragments.TagListDTO getTagListByDeptIdAndType(Long departmentId) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put(HDFragmentsTagQueryEnum.DEPARTMENT_ID.getName(), Long.valueOf(departmentId).toString());
        queryParam.put(HDFragmentsTagQueryEnum.TAG_TYPE.getName(), Long.valueOf(HDFragmentsTagTypeEnum.UI_CONFIG.getType()).toString());
        return hdFragmentsServiceWrap.getTags(queryParam);
    }

    public List<Long> parseStringToLongList(String string) {
        List<Long> resultList = new ArrayList<>();
        if (string != null && string.trim().length() > 0) {
            String[] strArr = string.split(",");
            for (String temp : strArr) {
                temp = temp.trim();
                if (temp.length() > 0) {
                    resultList.add(Long.parseLong(temp));
                }
            }
        }
        return resultList;
    }

}
