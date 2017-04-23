package com.youhujia.solar.domain.area;

import com.youhujia.halo.common.BaseController;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangYing on 2017/4/19.
 */
@RestController
@RequestMapping(value = "/api/solar/v1/areas")
public class AreaController extends BaseController {

    @Autowired
    private AreaBO areaBO;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Solar.MapDTO getAreas() {
        try {
            return areaBO.getAreas();
        } catch (Exception e) {
            return handleException(a -> Solar.MapDTO.newBuilder().setResult(a).build(), e);
        }
    }
}
