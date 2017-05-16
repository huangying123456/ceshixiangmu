package com.youhujia.solar.domain.nurse;

import com.youhujia.halo.yolar.Yolar;
import com.youhujia.solar.domain.nurse.query.NurseQueryBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/23.
 */
@Component
public class NurseBO {

    @Autowired
    NurseQueryBO nurseQueryBO;

    public Yolar.NurseListDTO findActiveNursesInHostAndGuestDepartmentByHostDepartmentId(Long departmentId) {
        return nurseQueryBO.findActiveNursesInHostAndGuestDepartmentByHostDepartmentId(departmentId);
    }

}
