package com.youhujia.solar.domain.department.update;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import com.youhujia.solar.domain.department.create.DepCreateBO;
import com.youhujia.solar.domain.organization.Organization;
import com.youhujia.solar.domain.organization.OrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangYing on 2017/4/17.
 */
@Component
public class DepUpdateBO {

    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private DepCreateBO depCreateBO;

    public DepUpdateContext updateDepartment(Solar.DepartmentUpdateOption option) {

        DepUpdateContext context = new DepUpdateContext();

        Department department = departmentDAO.save(toDepartment(option));

//        Update it's guest department  ------------------访客科室先不管--------
//        Department guestDepartment = depCreateBO.createOrGetGuestDepartment(department);
//        guestDepartment.setOrganizationId(department.getOrganizationId());
//        guestDepartment.setName(department.getName());
//        guestDepartment.setAuthCode(department.getAuthCode());
//        guestDepartment.setStatus(department.getStatus());
//        guestDepartment.setWxSubQRCodeValue(department.getWxSubQRCodeValue());
//        departmentDAO.save(guestDepartment);

        context.setDepartment(department);

        return context;
    }

    private Department toDepartment(Solar.DepartmentUpdateOption option) {

        Department department = departmentDAO.findOne(option.getDepartmentId());

        if (department == null) {
            throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！错误的科室id！");
        }
        if (option.hasOrganizationId()) {
            Organization temp = organizationDAO.findOne(option.getOrganizationId());
            if (temp == null) {
                throw new YHJException(YHJExceptionCodeEnum.OPTION_FORMAT_ERROR, "错误！错误的医院id！");

            } else {
                department.setOrganizationId(option.getOrganizationId());
            }
        }
        if (option.hasName()) {
            department.setName(option.getName());
        }
        if (option.hasNumber()) {
            department.setNumber(option.getNumber());
        }
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


    public DepUpdateContext updateDepartmentWxQrCode(Solar.DepartmentOption option) {

        DepUpdateContext context = new DepUpdateContext();
        Department originDepartment = departmentDAO.findOne(option.getDepartmentId());
        originDepartment.setWxSubQRCodeValue(option.getDepartmentWxQrCode());

        context.setDepartment(departmentDAO.save(originDepartment));
        return context;
    }
}
