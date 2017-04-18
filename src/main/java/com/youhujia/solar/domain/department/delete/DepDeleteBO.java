package com.youhujia.solar.domain.department.delete;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huangYing on 2017/4/18.
 */
public class DepDeleteBO {

    @Autowired
    private DepartmentDAO departmentDAO;

    public static String IS_DELETED = "-1";


    public DepDeleteContext markDeleteDepartmentById(Long departmentId) {

        DepDeleteContext context = new DepDeleteContext();

        if (departmentId == null || departmentId < 0) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！科室id为空或者非法!");
        }
        Department department = departmentDAO.findOne(departmentId);
        if (department == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！错误的科室id!");
        }
        department.setStatus(new Byte(IS_DELETED));
        department = departmentDAO.save(department);

        context.setDepartment(department);
        return context;
    }
}
