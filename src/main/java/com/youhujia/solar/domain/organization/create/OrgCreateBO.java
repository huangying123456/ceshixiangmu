package com.youhujia.solar.domain.organization.create;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.halo.yolar.Yolar;
import com.youhujia.solar.domain.area.Area;
import com.youhujia.solar.domain.area.AreaDao;
import com.youhujia.solar.domain.organization.Organization;
import com.youhujia.solar.domain.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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
    private AreaDao areaDao;

    public OrgCreateContext create(Solar.OrganizationCreateOption option) {

        Organization organization = toOrganization(option);

        OrgCreateContext orgCreateContext = new OrgCreateContext();

        orgCreateContext.setOrganization(organizationDAO.save(organization));

        return orgCreateContext;
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
            organization.setStatus((byte) option.getStatus());
        }
        if (option.hasLat()) {
            organization.setLat(new BigDecimal(option.getLat()));
        }
        if (option.hasLon()) {
            organization.setLon(new BigDecimal(option.getLon()));
        }
        if (option.hasAreaId()) {
            Area area = areaDao.findOne(option.getAreaId());
            if (area != null) {
                throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "the area was not found in the database ,areaId:" + option.getAreaId());
            }
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

        return organization;
    }
}
