package com.youhujia.solar.domain.component.query.requestManagerRight;

import com.youhujia.halo.common.COMMON;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */

@Component
public class RequestBO {

    @Autowired
    DepartmentDAO departmentDAO;

    public COMMON.SimpleResponse buildRequsetResult(Department department) {
        departmentDAO.save(department);
        return COMMON.SimpleResponse.newBuilder()
                .setResult(COMMON.Result.newBuilder().setDisplaymsg("success").setCode(200).build()).build();
    }
}
