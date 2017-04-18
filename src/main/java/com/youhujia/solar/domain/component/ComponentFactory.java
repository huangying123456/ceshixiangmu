package com.youhujia.solar.domain.component;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.hdfragments.TagQueryEnum;
import com.youhujia.halo.hdfragments.TagTypeEnum;
import com.youhujia.halo.solar.DepartmentStatusEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.halo.yolar.Yolar;
import com.youhujia.halo.yolar.YolarClientWrap;
import com.youhujia.solar.domain.component.query.articleDisease.ArticleDiseaseComponentQueryContext;
import com.youhujia.solar.domain.component.query.recommend.RecomComponentQueryContext;
import com.youhujia.solar.domain.component.query.requestManagerRight.RequestContext;
import com.youhujia.solar.domain.component.query.selfEvaluation.SelfEvaluationComponentQueryContext;
import com.youhujia.solar.domain.component.query.serviceItem.ServiceItemComponentQueryContext;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class ComponentFactory {

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    @Autowired
    DepartmentDAO departmentDAO;

    @Autowired
    YolarClientWrap yolarClientWrap;

    public ComponentContext buildComponentContext(Solar.DepartmentIdListOption option) {

        if (option.getDepartmentIdList().size() == 0) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "部门编号为空");
        }
        ComponentContext componentContext = new ComponentContext();
        Map<String, String> queryParam = new HashMap<>();
        List<HDFragments.TagListDTO> tagListDTOList = new ArrayList<>();
        option.getDepartmentIdList().stream().forEach(departmentId -> {

            queryParam.put(TagQueryEnum.DEPARTMENT_ID.getName(), Long.valueOf(departmentId).toString());
            queryParam.put(TagQueryEnum.TAG_TYPE.getName(), Long.valueOf(TagTypeEnum.UI_VIEW.getType()).toString());
            HDFragments.TagListDTO tagListDTO = hdFragmentsServiceWrap.getTags(queryParam);
            tagListDTOList.add(tagListDTO);

        });

        componentContext.setTagListDTOList(tagListDTOList);
        return componentContext;
    }

    public ArticleDiseaseComponentQueryContext buildArticleDiseaseComponentQueryContext(Long componentId) {

        ArticleDiseaseComponentQueryContext context = new ArticleDiseaseComponentQueryContext();
        context.setTagDTO(getTagById(componentId));
        return context;
    }

    public SelfEvaluationComponentQueryContext buildSelfEvaluationComponentQueryContext(Long componentId) {
        SelfEvaluationComponentQueryContext context = new SelfEvaluationComponentQueryContext();
        context.setTagDTO(getTagById(componentId));
        return context;
    }

    public ServiceItemComponentQueryContext buildServiceItemComponentQueryContext(Long componentId) {
        ServiceItemComponentQueryContext context = new ServiceItemComponentQueryContext();
        context.setTagDTO(getTagById(componentId));
        return context;
    }

    public RecomComponentQueryContext buildRecomComponentQueryContext(Long componentId) {
        RecomComponentQueryContext context = new RecomComponentQueryContext();
        context.setTagDTO(getTagById(componentId));
        return context;
    }

    private HDFragments.TagDTO getTagById(Long componentId) {
        HDFragments.TagDTO tagDTO = hdFragmentsServiceWrap.getTagById(componentId);
        if (tagDTO.getData().getTag() == null) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "该组件不存在");
        }
        return tagDTO;
    }

    public RequestContext buildRequestContext(Long departmentId, Solar.RequestManagementRightOption option) {
        if (departmentId != option.getDeparmentId()) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "参数非法");
        }
        Department department = departmentDAO.findOne(departmentId);
        if (department == null) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "科室不存在");
        }
        if (department.getStatus() != DepartmentStatusEnum.TEMPLATE.getType()) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "科室应该处于模板科室模式");
        }

        Yolar.NurseDTO nurseDTO = yolarClientWrap.getNurseById(option.getNurseId());
        if(nurseDTO.getNurse() == null){
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "护士不存在");
        }
        if(!nurseDTO.getNurse().getActive()){
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "护士审核尚未通过");
        }
        if(option.getPictureList().size() == 0){
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "请上传资质照片，作为凭证");
        }

        department.setStatus(DepartmentStatusEnum.SELF_MANAGEMENT.getType().byteValue());
        RequestContext context = new RequestContext();
        context.setDepartment(department);

        return context;
    }
}
