package com.youhujia.solar.organization;

import com.youhujia.halo.common.BaseController;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * Created by huangYing on 2017/4/17.
 */
@RestController
@RequestMapping(value = "/api/solar/v1/organizations")
public class OrganizationController extends BaseController {

    @Autowired
    private OrganizationBO organizationBO;

    /**
     * 创建新机构
     *
     * @param option
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Solar.OrganizationDTO create(@RequestBody Solar.OrganizationCreateOption option) {
        try {
            return organizationBO.create(option);
        } catch (Exception e) {
            return handleException(r -> Solar.OrganizationDTO.newBuilder().setResult(r).build(), e);
        }
    }

    /**
     * 获取所有的机构
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Solar.OrganizationListDTO findAllOrganization() {
        try {
            return organizationBO.findAll();
        } catch (Exception e) {
            return handleException(r -> Solar.OrganizationListDTO.newBuilder().setResult(r).build(), e);
        }
    }

    /**
     * 获取所有销售构建的机构
     *
     * @return
     */
    @RequestMapping(value = "/sell", method = RequestMethod.GET)
    public Solar.OrganizationListDTO getAllSellOrganization() {
        try {
            return organizationBO.getAllSellOrganization();
        } catch (Exception e) {
            return handleException(r -> Solar.OrganizationListDTO.newBuilder().setResult(r).build(), e);
        }
    }
    /**
     * 根据ids获取机构列表，ids=1，2，3
     *
     * @return
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    public Solar.OrganizationListDTO findOrganizationByIds(@RequestParam("organizationIds") String organizationIds) {
        try {
            return organizationBO.findOrganizationByIds(organizationIds);
        } catch (Exception e) {
            return handleException(r -> Solar.OrganizationListDTO.newBuilder().setResult(r).build(), e);
        }
    }

    /**
     * 获取单个机构的信息
     *
     * @param organizationId
     * @return
     */
    @RequestMapping(value = "{organizationId}", method = RequestMethod.GET)
    public Solar.OrganizationDTO getOrganizationById(@PathVariable("organizationId") Long organizationId) {
        try {
            return organizationBO.getOrganizationById(organizationId);
        } catch (Exception e) {
            return handleException(r -> Solar.OrganizationDTO.newBuilder().setResult(r).build(), e);
        }
    }

    /**
     * 获取机构下的所有科室（主科室&已认证的）
     *
     * @param organizationId
     * @return
     */
    @RequestMapping(value = "/{organizationId}/departments", method = RequestMethod.GET)
    public Solar.DepartmentListDTO getDepartmentsByOrganizationId(@PathVariable("organizationId") Long organizationId) {

        try {
            return organizationBO.getDepartmentsByOrganizationId(organizationId);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 获取机构下的所有科室（主科室&已认证的）
     *
     * @param organizationId
     * @return
     */
    @RequestMapping(value = "/{organizationId}/departments/all", method = RequestMethod.GET)
    public Solar.DepartmentListDTO getAllDepartmentsByOrganizationId(@PathVariable("organizationId") Long organizationId) {

        try {
            return organizationBO.getAllDepartmentsByOrganizationId(organizationId);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentListDTO.newBuilder().setResult(a).build(), e);
        }
    }


    /**
     * 通过获取多个机构下的所有科室
     *
     * @param organizationIds
     * @return
     */
    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public Solar.DepartmentListDTO getDepartmentsByOrganizationIds(@RequestParam("organizationIds") String organizationIds) {

        try {
            return organizationBO.getDepartmentsByOrganizationIds(organizationIds);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 更新机构
     *
     * @param option
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Solar.OrganizationDTO updateOrganization(@RequestBody Solar.OrganizationUpdateOption option) {

        try {
            return organizationBO.updateOrganization(option);
        } catch (Exception e) {
            return handleException(a -> Solar.OrganizationDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 获取所有的机构和科室（机构分页获取&只拿科室）
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/organizations-and-departments", method = RequestMethod.GET)
    public Solar.OrganizationAndDepartmentListDTO findAllOrganizationAndDepartment(@RequestParam Map<String,String> map) {

        try {
            return organizationBO.findAllOrganizationAndDepartment(map);
        } catch (Exception e) {
            return handleException(a -> Solar.OrganizationAndDepartmentListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    //----------------- code for improve department zhushou ------------------//

    /**
     * 根据地区编号获取该地区下的所有机构
     *
     * @param areaId
     * @return
     */
    @RequestMapping(value = "/get-by-area/{administrativeDivisionId}", method = RequestMethod.GET)
    public Solar.LBSOrganizationDTO getOrganizationsByAreaId(@PathVariable("administrativeDivisionId") Long areaId) {

        try {
            return organizationBO.getOrganizationListByAreaId(areaId);
        } catch (Exception e) {
            return handleException(a -> Solar.LBSOrganizationDTO.newBuilder().setResult(a).build(), e);
        }
    }

    //----------------- code for improve department admin ------------------//

    /**
     * 管理员获取所有的机构
     *
     * @param adId
     * @param draw
     * @param length
     * @param start
     * @return
     */
    @RequestMapping(value = "/manager-organization", method = RequestMethod.GET)
    public Solar.ManagerOrganizationListDTO getAllWithoutDeleteOrganizations(@RequestParam("adId") Long adId,
                                                                             @RequestParam("draw") Integer draw,
                                                                             @RequestParam("length") Integer length,
                                                                             @RequestParam("start") Integer start) {

        try {
            return organizationBO.getAllWithoutDeleteOrgListByAreaId(adId, draw, length, start);
        } catch (Exception e) {
            return handleException(a -> Solar.ManagerOrganizationListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    /**
     * 管理员删除机构
     *
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/manager-organization/{orgId}", method = RequestMethod.DELETE)
    public Solar.ManagerOrganizationDTO managerDeleteOrganization(@PathVariable("orgId") Long orgId) {

        try {
            return organizationBO.markDeleteOrganizationById(orgId);
        } catch (Exception e) {
            return handleException(a -> Solar.ManagerOrganizationDTO.newBuilder().setResult(a).build(), e);
        }
    }
}
