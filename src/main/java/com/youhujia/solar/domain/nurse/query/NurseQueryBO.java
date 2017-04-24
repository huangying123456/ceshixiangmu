package com.youhujia.solar.domain.nurse.query;

import com.youhujia.halo.util.ResponseUtil;
import com.youhujia.halo.yolar.Yolar;
import com.youhujia.halo.yolar.YolarClientWrap;
import com.youhujia.halo.yolar.YolarNurseQueryEnum;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ljm on 2017/4/23.
 */
@Component
public class NurseQueryBO {

    @Autowired
    DepartmentDAO departmentDAO;
    @Autowired
    YolarClientWrap yolarClientWrap;

    public Yolar.NurseListDTO findActiveNursesInHostAndGuestDepartmentByHostDepartmentId(Long departmentId) {

        List<Department> guestDepartmentList = departmentDAO.findByHostId(departmentId);

        return queryNursesInHostAndGuestDepartment(guestDepartmentList, departmentId);
    }

    private Yolar.NurseListDTO queryNursesInHostAndGuestDepartment(List<Department> guestDepartmentList, Long departmentId) {

        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put(YolarNurseQueryEnum.DEPARTMENT_ID.getName(), Long.valueOf(departmentId).toString());
        paramMap.put(YolarNurseQueryEnum.ACTIVE.getName(), Long.valueOf(1l).toString());

        Yolar.NurseListDTO.Builder builder = Yolar.NurseListDTO.newBuilder();

        Yolar.NurseListDTO nurseListDTO = yolarClientWrap.queryNurse(paramMap);

        if (nurseListDTO.getNurseList().size() != 0) {
            nurseListDTO.getNurseList().stream().forEach(nurse -> {
                builder.addNurse(nurse);
            });
        }

        if (guestDepartmentList.size() != 0) {
            guestDepartmentList.stream().forEach(guestDepartment -> {
                paramMap.put(YolarNurseQueryEnum.DEPARTMENT_ID.getName(), Long.valueOf(guestDepartment.getId()).toString());
                Yolar.NurseListDTO nurseList = yolarClientWrap.queryNurse(paramMap);
                if (nurseList.getNurseList().size() != 0) {
                    nurseList.getNurseList().stream().forEach(nurse -> {
                        builder.addNurse(nurse);
                    });
                }
            });
        }
        return builder.setResult(ResponseUtil.resultOK()).build();
    }
}
