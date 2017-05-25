package com.youhujia.solar.organization.create;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.OrganizationStatusEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.area.Area;
import com.youhujia.solar.area.AreaDAO;
import com.youhujia.solar.organization.Organization;
import com.youhujia.solar.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrgCreateBO {

    @Autowired
    private OrgCreateContextFactory OrgcreateContextFactory;

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private AreaDAO areaDao;

    public OrgCreateContext create(Solar.OrganizationCreateOption option) {

        checkCreateOrgOption(option);

        Organization organization = toOrganization(option);

        OrgCreateContext orgCreateContext = new OrgCreateContext();

        orgCreateContext.setOrganization(organizationDAO.save(organization));

        return orgCreateContext;
    }

    private void checkCreateOrgOption(Solar.OrganizationCreateOption option) {

        if (!option.hasName()) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "缺少医院名称，请填写科室名称");
        }

        if (!option.hasAreaId()) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "地区id为空");
        } else {
            Area area = areaDao.findOne(option.getAreaId());
            if (area == null) {
                throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "the area was not found in the database ,areaId:" + option.getAreaId());
            }
        }
        if (!option.hasAddress()) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "address is not exist");

        }
    }

    private Organization toOrganization(Solar.OrganizationCreateOption option) {
        Organization organization = new Organization();

        if (option.hasName()) {
            organization.setName(option.getName());
        }
        if (option.hasAddress()) {
            organization.setAddress(option.getAddress());
        }
        if (option.hasStatus()) {
            organization.setStatus((int) option.getStatus());
        } else {
            organization.setStatus(OrganizationStatusEnum.UNAUTHORIZED.getStatus());
        }
        if (option.hasLat()) {
            organization.setLat(new BigDecimal(option.getLat()));
        }
        if (option.hasLon()) {
            organization.setLon(new BigDecimal(option.getLon()));
        }
        if (option.hasAreaId()) {
            organization.setAreaId(option.getAreaId());
        }
        if (option.hasLevel()) {
            organization.setLevel(option.getLevel());
        }
        if (option.hasImgUrl()) {
            organization.setImgUrl(option.getImgUrl());
        }
        organization.setCreatedAt(new Timestamp(new Date().getTime()));
        organization.setUpdatedAt(new Timestamp(new Date().getTime()));
        return organization;
    }
}
