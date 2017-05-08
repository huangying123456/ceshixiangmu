package com.youhujia.solar.domain.component.query.requestManagerRight;

import com.youhujia.solar.domain.department.Department;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class RequestContext {

    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
