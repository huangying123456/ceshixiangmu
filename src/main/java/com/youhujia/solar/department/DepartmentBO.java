package com.youhujia.solar.department;

import com.google.zxing.WriterException;
import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.solar.Solar;
import com.youhujia.solar.component.query.requestManagerRight.RequestBO;
import com.youhujia.solar.department.create.DepCreateBO;
import com.youhujia.solar.department.create.DepCreateContext;
import com.youhujia.solar.department.delete.DepDeleteBO;
import com.youhujia.solar.department.delete.DepDeleteContext;
import com.youhujia.solar.department.query.DepQueryBO;
import com.youhujia.solar.department.query.DepQueryContext;
import com.youhujia.solar.department.query.DepQueryContextFactory;
import com.youhujia.solar.department.update.DepUpdateBO;
import com.youhujia.solar.department.update.DepUpdateContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * Created by huangYing on 2017/4/17.
 */
@Service
public class DepartmentBO {

    @Resource
    private DepCreateBO depCreateBO;

    @Resource
    private DepQueryBO depQueryBO;

    @Resource
    private DepUpdateBO depUpdateBO;

    @Resource
    private DepDeleteBO depDeleteBO;

    @Resource
    private RequestBO requestBO;

    @Resource
    private DepartmentDTOFactory departmentFactory;

    @Resource
    private DepQueryContextFactory depQueryContextFactory;

    public Solar.DepartmentDTO createDepartment(Solar.DepartmentCreateOption option) {

        DepCreateContext depCreateContext = depCreateBO.createDepartment(option);

        Solar.DepartmentDTO departmentDTO = departmentFactory.buildCreateDepartmentDTO(depCreateContext);

        return departmentDTO;
    }

    public Solar.DepartmentDTO getDepartmentById(Long departmentId) throws IOException, WriterException {

        DepQueryContext context = depQueryBO.getDepartmentById(departmentId);

        Solar.DepartmentDTO departmentDTO = departmentFactory.buildGetDepartmentByIdDTO(context);

        return departmentDTO;
    }

    public Solar.DepartmentListDTO getDepartmentListByIds(String ids) {

        DepQueryContext context = depQueryBO.getDepartmentListByIds(ids);

        Solar.DepartmentListDTO departmentListDTO = departmentFactory.buildGetDepartmentListByIdsDTO(context);

        return departmentListDTO;
    }

    public Solar.DepartmentDTO getDepartmentByNo(String departmentNo) {

        DepQueryContext context = depQueryBO.getDepartmentByNo(departmentNo);

        Solar.DepartmentDTO departmentDTO = departmentFactory.buildGetDepartmentByNoDTO(context);

        return departmentDTO;
    }

    public Solar.DepartmentDTO getGuestDepartmentByHostDepartmentId(Long departmentId) {

        DepQueryContext context = depQueryBO.getGuestDepartmentByHostDepartmentId(departmentId);

        Solar.DepartmentDTO departmentDTO = departmentFactory.buildGetGuestDepartmentByHostDepartmentIdDTO(context);

        return departmentDTO;
    }

    public Solar.DepartmentDTO updateDepartment(Solar.DepartmentUpdateOption department) {

        DepUpdateContext context = depUpdateBO.updateDepartment(department);

        Solar.DepartmentDTO departmentDTO = departmentFactory.buildUpdateDepartmentDTO(context);

        return departmentDTO;
    }


    //------------------- service for improve department zhushou ---------------------//

    public Solar.LBSDepartmentDTO getDepartmentListByOrgId(Long orgId) {

        DepQueryContext context = depQueryBO.getDepartmentListByOrgId(orgId);

        return departmentFactory.buildGetDepartmentListByOrgIdDTO(context);
    }

    //------------------- service for improve department admin --------------------//
    public Solar.ManagerDepartmentListDTO getAllWithoutDeleteDepartmentByOrgId(Long orgId) {

        DepQueryContext context = depQueryBO.getAllWithoutDeleteDepartmentByOrgId(orgId);

        return departmentFactory.buildGetAllWithoutDeleteDepartmentByOrgIdDTO(context);
    }

    public Solar.ManagerDepartmentDTO markDeleteDepartmentById(Long departmentId) {

        DepDeleteContext context = depDeleteBO.markDeleteDepartmentById(departmentId);

        return departmentFactory.buildMarkDeleteDepartmentById(context);
    }

    public Solar.DepartmentDTO updateDepartmentWxQrCode(Solar.DepartmentOption option) {
        DepUpdateContext context = depUpdateBO.updateDepartmentWxQrCode(option);
        return departmentFactory.buildUpdateDepartmentDTO(context);
    }

    public COMMON.SimpleResponse requestManagementRight(Long departmentId, Solar.RequestManagementRightOption option) {
        return requestBO.requestManagementRight(departmentId, option);
    }

    public Solar.DepartmentDTO getDepartmentByQRCode(String departmentQRCode) {
        DepQueryContext context = depQueryBO.getDepartmentByQRCode(departmentQRCode);

        return departmentFactory.buildGetDepartmentByIdDTO(context);
    }
}
