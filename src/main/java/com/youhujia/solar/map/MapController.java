package com.youhujia.solar.map;

import com.youhujia.halo.common.BaseController;
import com.youhujia.halo.common.COMMON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ljm on 2017/4/18.
 */

@RestController
@RequestMapping(value = "/api/solar/v1/map")
public class MapController extends BaseController {

    @Autowired
    MapBO mapBO;

    /**
     * 录入地理信息（圣阳爬虫数据）
     *
     * @return
     */
    @RequestMapping(value = "/all-in", method = RequestMethod.GET)
    public COMMON.Result resetAreaInfo() {
        return mapBO.resetAreaInfo();
    }

    /**
     * 将科室信息从文件导入到数据库
     *
     * @return
     */
    @RequestMapping(value = "/department-bride", method = RequestMethod.GET)
    public COMMON.Result setOrganizationDepartmentFromFile() {
        return mapBO.setOrganizationDepartmentFromFile();
    }

    /**
     * 从文件里的信息，更新科室信息
     *
     * @return
     */
    @RequestMapping(value = "/department-wife", method = RequestMethod.GET)
    public COMMON.Result updateOrganizationDepartmentFromFile() {
        return mapBO.updateOrganizationDepartmentFromFile();
    }
}
