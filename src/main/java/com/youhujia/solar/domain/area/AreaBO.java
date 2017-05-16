package com.youhujia.solar.domain.area;

import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.area.query.AreaQueryBO;
import com.youhujia.solar.domain.area.query.AreaQueryContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/19.
 */
@Component
public class AreaBO {

    @Autowired
    private AreaQueryBO areaQueryBO;

    @Autowired
    private AreaDTOFactory areaDTOFactory;

    public Solar.MapDTO getAreas() {

        AreaQueryContext context = areaQueryBO.getAreas();

        return areaDTOFactory.buildGetAreasDTO(context);
    }
}
