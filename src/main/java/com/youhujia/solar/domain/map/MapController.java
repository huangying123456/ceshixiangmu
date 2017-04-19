package com.youhujia.solar.domain.map;

import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ljm on 2017/4/18.
 */

@RestController
@RequestMapping(value = "/api/solar/v1/map")
public class MapController {

    @Autowired
    MapBO mapBO;

    @RequestMapping(value = "/all-in", method = RequestMethod.GET)
    public COMMON.Result resetAreaInfo() {
        return mapBO.resetAreaInfo();
    }

    @RequestMapping(value = "/areas", method = RequestMethod.GET)
    public Solar.MapDTO getAreas() {
        return mapBO.getMapSet();
    }

    @RequestMapping(value = "/department-bride", method = RequestMethod.GET)
    public COMMON.Result setOrganizationDepartmentFromFile() {
        return mapBO.setOrganizationDepartmentFromFile();
    }

    @RequestMapping(value = "/department-wife", method = RequestMethod.GET)
    public COMMON.Result updateOrganizationDepartmentFromFile() {
        return mapBO.updateOrganizationDepartmentFromFile();
    }
}
