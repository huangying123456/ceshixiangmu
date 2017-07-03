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
import java.util.function.Function;
import java.util.stream.Collector;
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

    public ComponentListQueryContext batchComponentListByDepartmentIds(String ids) {

        String[] str = ids.split(",");

        if (str.length == 0) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "部门编号为空");
        }

        ComponentListQueryContext context = new ComponentListQueryContext();
        List<HDFragments.TagListDTO> ret = new ArrayList<>();

        HDFragments.TagListDTO tagListDTO = getTagListByDeptIdsAndType(ids);
        List<Long> all = parseStringToLongList(ids);
        // 如果该科室有ui配置，直接获取它所属的科室ui
        if (tagListDTO.getData().getTagsList().size() != 0) {
            computeUITagByDptId(ret, tagListDTO);
        }
        // 如果该科室没有ui配置，获取它所属的模板科室ui
        computeDptIdsNoUI(tagListDTO, all);
        Map<Long, HDFragments.Tag> dptIdTagDic = getTagListDTOByTemplateDptIds(all);

        if (dptIdTagDic.entrySet().size() != 0) {
            computeUITagByTempateDptId(ret, dptIdTagDic);
        }
        context.setTagListDTOList(ret);
        return context;
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
    private void computeUITagByDptId(List<HDFragments.TagListDTO> ret,
                                     HDFragments.TagListDTO tagListDTO) {
        Map<Long, List<HDFragments.Tag>> dptIdTagsDic = tagListDTO.getData().getTagsList()
                .stream().collect(Collectors.groupingBy(HDFragments.Tag::getDptId));
//        Map<Long, HDFragments.Tag> tagDptIdDic = tagListDTO.getData().getTagsList()
//                .stream().collect(Collectors.toMap(HDFragments.Tag::getDptId, Function.identity()));
        dptIdTagsDic.entrySet().stream().forEach(entry -> {
            entry.getValue().stream().forEach(tag -> {
                HDFragments.TagListDTO.Builder tagListDTOBd = HDFragments.TagListDTO.newBuilder();
                HDFragments.TagListData.Builder tagDataDTO = HDFragments.TagListData.newBuilder();
                tagDataDTO.addTags(tag);
                tagListDTOBd.setData(tagDataDTO.build());
                ret.add(tagListDTOBd.build());
            });
        });
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

    private Map<Long, HDFragments.Tag> getTagListDTOByTemplateDptIds(List<Long> all) {

        List<Department> dpts = departmentDAO.findByIdIn(all);
        List<Long> dptIds = dpts.stream().map(department -> department.getId()).collect(Collectors.toList());
        List<String> templateDptIds = departmentDAO.findByIdIn(all).stream()
                .map(department -> department.getClassificationType()).collect(Collectors.toList());

        Map<Long, HDFragments.Tag> dptIdTagDic = new HashMap();
        HDFragments.TagListDTO tagListDTO = getTagListByDeptIdsAndType(parseCollectionToString(templateDptIds));

        dptIds.stream().forEach(dptId -> {
            tagListDTO.getData().getTagsList().stream().forEach(tag -> {
                if (tag.getDptId() == dptId) {
                    dptIdTagDic.put(dptId, tag);
                }
            });
        });

        return dptIdTagDic;
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

    public static <T> String parseCollectionToString(Iterable<T> iterable) {
        StringBuilder stringBuilder = new StringBuilder();
        if (iterable != null) {
            iterable.forEach(t -> stringBuilder.append(t).append(","));
        }
        return stringBuilder.length() > 0 ? stringBuilder.substring(0, stringBuilder.length() - 1) : stringBuilder.toString();
    }

}
