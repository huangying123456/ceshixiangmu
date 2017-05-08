package com.youhujia.solar.domain.area;

import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.area.query.AreaQueryContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by huangYing on 2017/4/19.
 */
@Component
public class AreaDTOFactory {

    public Solar.MapDTO buildGetAreasDTO(AreaQueryContext context) {

        COMMON.Result.Builder resultBuilder = COMMON.Result.newBuilder();
        Solar.MapDTO.Builder mapDTOBuilder = Solar.MapDTO.newBuilder();
        Solar.MapDataDTO.Builder mapDataDTOBuilder = Solar.MapDataDTO.newBuilder();
        //开始时是所有的地理区域，随着每次的遍历 会删除遍历出来的数据 对此数据结构的遍历的复杂度会逐渐降低（此数据结构size理论值为3625）
        List<Area> leftAreas = context.getAreaList();
        //拿到所有的省的信息
        List<Area> provinceAreas = new ArrayList<>();
        Iterator<Area> leftIterator = leftAreas.iterator();
        while (leftIterator.hasNext()) {
            Area temp = leftIterator.next();
            if (temp.getParentId() < 0) {
                provinceAreas.add(temp);
                leftIterator.remove();
            }
        }
        //遍历省的信息来寻找其下的城市
        for (Area provinceArea : provinceAreas) {
            Solar.ProvinceData.Builder provinceBuilder = Solar.ProvinceData.newBuilder();
            provinceBuilder.setPId(provinceArea.getId());
            provinceBuilder.setPName(provinceArea.getName());
            Long pId = provinceArea.getId();
            List<Area> cityAreas = new ArrayList<>();
            leftIterator = leftAreas.iterator();
            while (leftIterator.hasNext()) {
                Area temp = leftIterator.next();
                if (temp.getParentId().equals(pId)) {
                    cityAreas.add(temp);
                    leftIterator.remove();
                }
            }
            //遍历所有城市来寻找其下的行政区域划分
            for (Area cityArea : cityAreas) {
                Solar.CityData.Builder cityBuilder = Solar.CityData.newBuilder();
                cityBuilder.setCityId(cityArea.getId());
                cityBuilder.setCityName(cityArea.getName());
                Long cId = cityArea.getId();
                //最后一层遍历 不需要再浪费list来存储信息了
                leftIterator = leftAreas.iterator();
                while (leftIterator.hasNext()) {
                    Area adArea = leftIterator.next();
                    if (adArea.getParentId().equals(cId)) {
                        Solar.AdministrativeDivisionData.Builder adBuilder = Solar.AdministrativeDivisionData.newBuilder();
                        adBuilder.setAdId(adArea.getId());
                        adBuilder.setAdName(adArea.getName());
                        //区域存入城市的protobuf里面
                        cityBuilder.addAdministrativeDivisions(adBuilder);
                        leftIterator.remove();
                    }
                }
                //某个城市的区域信息已经全部存入，它可以回归省了
                provinceBuilder.addCities(cityBuilder);
            }
            //某个省的区域信息已经全部存入，它可以回归DTO了
            mapDataDTOBuilder.addProvinces(provinceBuilder);
        }
        //整理最后的protobuf
        mapDTOBuilder.setData(mapDataDTOBuilder);
        resultBuilder.setCode(0);
        resultBuilder.setSuccess(true);
        resultBuilder.setMsg("成功！请缓存到本地");
        mapDTOBuilder.setResult(resultBuilder);
        return mapDTOBuilder.build();
    }
}
