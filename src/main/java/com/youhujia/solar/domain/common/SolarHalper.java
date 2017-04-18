package com.youhujia.solar.domain.common;

import com.googlecode.protobuf.format.JsonFormat;
import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.Solar;

/**
 * Created by ljm on 2017/4/17.
 */
public class SolarHalper {


    public static Solar.Direct.Unit parseToSolarUnit(String json) {
        Solar.Direct.Unit.Builder builder = Solar.Direct.Unit.newBuilder();
        if (json == null || json.trim().equalsIgnoreCase("")) {
            return builder.build();
        }
        try {
            JsonFormat.merge(json, builder);
            return builder.build();
        } catch (JsonFormat.ParseException pe) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "数据格式化异常");
        }

    }

    public static Solar.ArticleDiseaseGroup.Group parseToSolarGroup(String json) {
        Solar.ArticleDiseaseGroup.Group.Builder builder = Solar.ArticleDiseaseGroup.Group.newBuilder();
        if (json == null || json.trim().equalsIgnoreCase("")) {
            return builder.build();
        }
        try {
            JsonFormat.merge(json, builder);
            return builder.build();
        } catch (JsonFormat.ParseException pe) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "数据格式化异常");
        }

    }
}
