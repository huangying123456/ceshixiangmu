package com.youhujia.solar.domain.area.query;

import com.youhujia.solar.domain.area.Area;
import com.youhujia.solar.domain.area.AreaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huangYing on 2017/4/19.
 */
@Component
public class AreaQueryBO {

    @Autowired
    private AreaDAO areaDAO;

    public AreaQueryContext getAreas() {

        AreaQueryContext context = new AreaQueryContext();

        List<Area> leftAreas = areaDAO.findAll();

        context.setAreaList(leftAreas);

        return context;
    }
}
