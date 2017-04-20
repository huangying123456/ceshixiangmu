package com.youhujia.solar.domain.component.query.requestManagerRight;

import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.DepartmentStatusEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.halo.yolar.Yolar;
import com.youhujia.halo.yolar.YolarClientWrap;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by ljm on 2017/4/18.
 */

@Component
public class RequestBO {

    @Autowired
    DepartmentDAO departmentDAO;
    @Autowired
    YolarClientWrap yolarClientWrap;

    public COMMON.SimpleResponse requestManagementRight(Long departmentId, Solar.RequestManagementRightOption option) {

        RequestContext context = buildRequestContext(departmentId, option);

        finishRequestRight(context);
        return COMMON.SimpleResponse.newBuilder()
                .setResult(COMMON.Result.newBuilder().setDisplaymsg("success").setCode(200).build()).build();
    }

    @Transactional
    private void finishRequestRight(RequestContext context) {
        departmentDAO.save(context.getDepartment());
    }

    private RequestContext buildRequestContext(Long departmentId, Solar.RequestManagementRightOption option) {

        RequestContext context = new RequestContext();

        if (departmentId != option.getDepartmentId()) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "参数非法");
        }
        Department department = departmentDAO.findOne(departmentId);
        if (department == null) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "科室不存在");
        }
        if (department.getStatus() != DepartmentStatusEnum.TEMPLATE.getType().byteValue()) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "科室应该处于模板科室模式");
        }

        Yolar.NurseDTO nurseDTO = yolarClientWrap.getNurseById(option.getNurseId());

        if (nurseDTO.getNurse() == null) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "护士不存在");
        }
        if (!nurseDTO.getNurse().getActive()) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "护士审核尚未通过");
        }
        if (option.getPictureList().size() == 0) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "请上传资质照片，作为凭证");
        }

        department.setStatus(DepartmentStatusEnum.SELF_MANAGEMENT.getType().byteValue());

        context.setDepartment(department);

        return context;
    }


}
