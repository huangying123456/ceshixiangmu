package com.youhujia.solar.domain.nurse;

import com.youhujia.halo.common.BaseController;
import com.youhujia.halo.yolar.Yolar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ljm on 2017/4/23.
 */

@RestController
@RequestMapping(value = "/api/solar/v1/nurse")
public class NurseController extends BaseController {

    @Autowired
    NurseBO nurseBO;

    @RequestMapping(value = "/{departmentId}/host-guest/nurses", method = RequestMethod.GET)
    public Yolar.NurseListDTO findActiveNursesInHostAndGuestDepartmentByHostDepartmentId(@PathVariable("departmentId") Long departmentId) {
        try {
            return nurseBO.findActiveNursesInHostAndGuestDepartmentByHostDepartmentId(departmentId);
        } catch (Exception e) {
            return handleException(a -> Yolar.NurseListDTO.newBuilder().setResult(a).build(), e);
        }
    }

}
