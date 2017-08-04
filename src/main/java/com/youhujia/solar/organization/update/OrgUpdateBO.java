package com.youhujia.solar.organization.update;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.area.Area;
import com.youhujia.solar.area.AreaDAO;
import com.youhujia.solar.organization.Organization;
import com.youhujia.solar.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class OrgUpdateBO {

    @Autowired
    private AreaDAO areaDao;

    @Autowired
    private OrganizationDAO organizationDAO;

    public OrgUpdateContext updateOrganization(Solar.OrganizationUpdateOption option) {

        Organization organization = toOrganization(option);

        OrgUpdateContext orgUpdateContext = new OrgUpdateContext();

        orgUpdateContext.setOrganization(organizationDAO.save(organization));

        return orgUpdateContext;
    }

    private Organization toOrganization(Solar.OrganizationUpdateOption option) {

        Organization organization = organizationDAO.findOne(option.getId());
        if (organization == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "修改的科室不存在，OrganizationUpdateOption：" + option.getAreaId());
        }
        if (option.hasName()) {
            organization.setName(option.getName());
        }
        if (option.hasAddress()) {
            organization.setAddress(option.getAddress());
        }
        if (option.hasStatus()) {
            organization.setStatus((int) option.getStatus());
        }
        if (option.hasLat()) {
            organization.setLat(new BigDecimal(option.getLat()));
        }
        if (option.hasLon()) {
            organization.setLon(new BigDecimal(option.getLon()));
        }
        if (option.hasAreaId()) {
            Area area = areaDao.findOne(option.getAreaId());
            if (area == null) {
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
        if (option.hasCode()) {
            organization.setCode(option.getCode());
        }
        if (option.hasVersion()) {
            organization.setVersion(option.getVersion());
        }
        if (option.hasExpiredAt()) {
            organization.setExpiredAt(new Timestamp(option.getExpiredAt()));
        }

        organization.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return organization;
    }
}
