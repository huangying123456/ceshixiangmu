package com.youhujia.solar.domain.department.create;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import com.youhujia.solar.domain.organization.Organization;
import com.youhujia.solar.domain.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class DepCreateBO {

    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private OrganizationDAO organizationDAO;


    public DepCreateContext createDepartment(Solar.DepartmentCreateOption option) {

        checkCreateOption(option);
        DepCreateContext depCreateContext = new DepCreateContext();

        Department department = departmentDAO.save(toDepartment(option));

        // Create guest department at the same time,--------------------cc say 可以先不管访客科室-------------------------
        //createOrGetGuestDepartment(department);

        depCreateContext.setDepartment(department);

        return depCreateContext;
    }

    private void checkCreateOption(Solar.DepartmentCreateOption option) {
        if (!option.hasOrganizationId()) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "科室id为空");
        } else {
            Organization organization = organizationDAO.findOne(option.getOrganizationId());
            if (organization != null) {
                throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "错误的医院id，不能找到对应的医院");
            }
        }
        if (!option.hasName()) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "缺少科室名称，请填写科室名称");
        }
        if (!option.hasNumber()) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "缺少科室编号，请填写科室编号");
        }
    }

    private Department toDepartment(Solar.DepartmentCreateOption option) {

        Department department = new Department();

        department.setOrganizationId(option.getOrganizationId());
        department.setName(option.getName());
        department.setNumber(option.getNumber());

        if (option.hasAuthCode()) {
            department.setAuthCode(option.getAuthCode());
        }
        if (option.hasIsGuest()) {
            department.setGuest(option.getIsGuest());
        }
        if (option.hasHostId()) {
            department.setHostId(option.getHostId());
        }
        if (option.hasWxQrcode()) {
            department.setWxSubQRCodeValue(option.getWxQrcode());
        }
        if (option.hasImgUrl()) {
            department.setImgUrl(option.getImgUrl());
        }
        if (option.hasStatus()) {
            department.setStatus((byte) option.getStatus());
        }
        if (option.hasMayContact()) {
            department.setMayContact(option.getMayContact());
        }
        return department;
    }
}
