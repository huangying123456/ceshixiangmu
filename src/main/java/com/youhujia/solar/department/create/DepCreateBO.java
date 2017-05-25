package com.youhujia.solar.department.create;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.solar.DepartmentStatusEnum;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.department.Department;
import com.youhujia.solar.department.DepartmentDAO;
import com.youhujia.solar.organization.Organization;
import com.youhujia.solar.organization.OrganizationDAO;
import com.youhujia.solar.wxQrcode.WechatQRCodeBO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
@Service
public class DepCreateBO {

    @Resource
    private DepartmentDAO departmentDAO;

    @Resource
    private OrganizationDAO organizationDAO;

    @Resource
    private WechatQRCodeBO wechatQRCodeBO;

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
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "医院id为空");
        } else {
            Organization organization = organizationDAO.findOne(option.getOrganizationId());
            if (organization == null) {
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
            department.setGuest(option.getIsGuest() ? 1L : 0L);
        } else {
            department.setGuest(0L);
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
            department.setStatus(option.getStatus());
        } else {
            department.setStatus(DepartmentStatusEnum.UNAUTHORIZED.getStatus());
        }
        if (option.hasMayContact()) {
            department.setMayContact(option.getMayContact());
        }
        if (option.hasClassificationType()) {
            department.setClassificationType(option.getClassificationType());
        }
        department.setCreatedAt(new Timestamp(new Date().getTime()));
        department.setUpdatedAt(new Timestamp(new Date().getTime()));
        return department;
    }

    /**
     * Create guest department
     */
    public Department createOrGetGuestDepartment(Department department) {
        // department is a Guest Department
        if (department.getGuest() > 0L) {
            return department;
        }

        // department is Host department
        List<Department> guestDepartmentList = departmentDAO.findByHostIdAndStatus(department.getId(), DepartmentStatusEnum.NORMAL.getStatus());

        if (guestDepartmentList.size() > 0) {
            return guestDepartmentList.get(0);
        } else {
            // Create a guest of host department
            Department guest = new Department();
            BeanUtils.copyProperties(department, guest);
            guest.setId(null);
            guest.setGuest(1L);
            guest.setHostId(department.getId());
            guest.setCreatedAt(null);
            guest.setUpdatedAt(null);

            return departmentDAO.save(guest);
        }
    }
}