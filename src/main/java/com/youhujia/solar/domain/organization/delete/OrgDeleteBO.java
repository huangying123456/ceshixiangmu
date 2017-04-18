package com.youhujia.solar.domain.organization.delete;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.solar.domain.organization.Organization;
import com.youhujia.solar.domain.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/18.
 */
@Component
public class OrgDeleteBO {

    @Autowired
    private OrganizationDAO organizationDAO;

    public static String IS_DELETED = "-1";

    public OrgDeleteContext markDeleteOrganizationById(Long orgId) {

        OrgDeleteContext context = new OrgDeleteContext();

        if (orgId == null || orgId < 0) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！医院id为空或者非法!");
        }
        Organization organization = organizationDAO.findOne(orgId);
        if (organization == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！错误的医院id!");

        }
        organization.setStatus(new Byte(IS_DELETED));
        organization = organizationDAO.save(organization);

        context.setOrganization(organization);
        return context;
    }
}
