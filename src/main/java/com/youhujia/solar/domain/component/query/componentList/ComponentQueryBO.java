package com.youhujia.solar.domain.component.query.componentList;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.solar.Solar;
import com.youhujia.halo.yolar.YolarClientWrap;
import com.youhujia.solar.domain.department.DepartmentBO;
import com.youhujia.solar.domain.department.DepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ljm on 2017/4/17.
 */

@Component
public class ComponentQueryBO {

    @Autowired
    ComponentQueryContextFactory componentQueryContextFactory;

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    @Autowired
    DepartmentBO departmentBO;

    @Autowired
    YolarClientWrap yolarClientWrap;

    @Autowired
    DepartmentDAO departmentDAO;

    public Solar.ComponentListDataListDTO buildComponentListDataListDTO(List<HDFragments.TagListDTO> tagListDTOList) {
        ComponentQueryContext componentQueryContext = componentQueryContextFactory.buildComponentQueryContext(tagListDTOList);
        return componentQueryContext.getComponentListDataListDTO();
    }
//
//    public Solar.SelfEvaluationComponentDTO getSelfEvaluationComponentById(Long componentId) {
//
//        componentQueryContext.setTagDTO(getTagById(componentId));
//        return componentQueryContextFactory.buildSelfEvaluationComponent(componentQueryContext);
//    }
//
//    public Solar.ServiceItemComponentDTO getServiceItemComponentById(Long componentId) {
//
//        componentQueryContext.setTagDTO(getTagById(componentId));
//        return componentQueryContextFactory.buildServiceItemComponent(componentQueryContext);
//    }
//
//    public Solar.RecomComponentDTO getRecomComponentById(Long componentId) {
//        componentQueryContext.setTagDTO(getTagById(componentId));
//        return componentQueryContextFactory.buildRecomComponent(componentQueryContext);
//    }
//
//    public COMMON.SimpleResponse requestManagementRight(Long departmentId, Solar.RequestManagementRightOption option) {
//
//        Department department = departmentDAO.findOne(option.getDeparmentId());
//
//        if (department == null) {
//            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "该科室不存在");
//        }
//        if (department.getStatus() != DepartmentStatusEnum.TEMPLATE.getType()) {
//            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "该科室不属于模板科室模式，不能进行此操作");
//        }
//
//        Yolar.NurseDTO nurseDTO = yolarClientWrap.getNurseById(option.getNurseId());
//        if (nurseDTO == null) {
//            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "该护士不存在");
//        }
//        if (!nurseDTO.getNurse().getActive()) {
//            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "该护士身份尚未审核通过");
//        }
//
//        department.setStatus((DepartmentStatusEnum.SELF_MANAGEMENT.getType().byteValue()));
//        departmentDAO.save(department);
//
//        return COMMON.SimpleResponse.newBuilder().setResult(COMMON.Result.newBuilder().setCode(200).setDisplaymsg("success")).build();
//    }
//
//
    private HDFragments.TagDTO getTagById(Long tagId){
        HDFragments.TagDTO tagDTO = hdFragmentsServiceWrap.getTagById(tagId);
        if(tagDTO.getData().getTag() == null){
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER,"该组件不存在");
        }
        return tagDTO;
    }
}
