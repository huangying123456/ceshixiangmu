package com.youhujia.solar.domain.area.query;

import com.youhujia.solar.domain.area.Area;

import java.util.List;

/**
 * Created by huangYing on 2017/4/19.
 */
public class AreaQueryContext {

    private List<Area> areaList;

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
