package com.youhujia.solar.domain.department.delete;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.DepartmentStatusEnum;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/18.
 */
@Component
public class DepDeleteBO {

    @Autowired
    private DepartmentDAO departmentDAO;

    public DepDeleteContext markDeleteDepartmentById(Long departmentId) {

        DepDeleteContext context = new DepDeleteContext();

        if (departmentId == null || departmentId < 0) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！科室id为空或者非法!");
        }
        Department department = departmentDAO.findOne(departmentId);
        if (department == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！错误的科室id!");
        }
        department.setStatus(DepartmentStatusEnum.DELETED.getStatus());
        department = departmentDAO.save(department);

        context.setDepartment(department);
        return context;
    }
}
