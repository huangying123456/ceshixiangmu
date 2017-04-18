package com.youhujia.solar.domain.organization;

import com.youhujia.halo.common.BaseController;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by huangYing on 2017/4/17.
 */
@RestController
@RequestMapping(value = "api/solar/v1/organizations")
public class OrganizationController extends BaseController {

    @Autowired
    private OrganizationBO organizationBO;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Solar.OrganizationDTO create(Solar.OrganizationCreateOption option) {
        try {
            return organizationBO.create(option);
        } catch (Exception e) {
            return handleException(r -> Solar.OrganizationDTO.newBuilder().setResult(r).build(), e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Solar.OrganizationListDTO findAllOrganization() {
        try {
            return organizationBO.findAll();
        } catch (Exception e) {
            return handleException(r -> Solar.OrganizationListDTO.newBuilder().setResult(r).build(), e);
        }
    }

    @RequestMapping(value = "{organizationId}", method = RequestMethod.GET)
    public Solar.OrganizationDTO getOrganizationById(@PathVariable("organizationId") Long organizationId) {
        try {
            return organizationBO.getOrganizationById(organizationId);
        } catch (Exception e) {
            return handleException(r -> Solar.OrganizationDTO.newBuilder().setResult(r).build(), e);
        }
    }

    @RequestMapping(value = "/{organizationId}/departments", method = RequestMethod.GET)
    public Solar.DepartmentListDTO getDepartmentsByOrganizationId(@PathVariable("organizationId") Long organizationId) {

        try {
            return organizationBO.getDepartmentsByOrganizationId(organizationId);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping(value = "/{organizationId}/departments/all", method = RequestMethod.GET)
    public Solar.DepartmentListDTO getAllDepartmentsByOrganizationId(@PathVariable("organizationId") Long organizationId) {

        try {
            return organizationBO.getAllDepartmentsByOrganizationId(organizationId);
        } catch (Exception e) {
            return handleException(a -> Solar.DepartmentListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Solar.OrganizationDTO updateOrganization(@RequestBody Solar.OrganizationUpdateOption option) {

        try {
            return organizationBO.updateOrganization(option);
        } catch (Exception e) {
            return handleException(a -> Solar.OrganizationDTO.newBuilder().setResult(a).build(), e);
        }
    }

    //----------------- code for improve department zhushou ------------------//

    @RequestMapping(value = "/get-by-area/{administrativeDivisionId}", method = RequestMethod.GET)
    public Solar.LBSOrganizationDTO getOrganizationsByAreaId(@PathVariable("administrativeDivisionId") Long areaId) {

        try {
            return organizationBO.getOrganizationListByAreaId(areaId);
        } catch (Exception e) {
            return handleException(a -> Solar.LBSOrganizationDTO.newBuilder().setResult(a).build(), e);
        }
    }

    //----------------- code for improve department admin ------------------//
    @RequestMapping(value = "/manager-organization", method = RequestMethod.GET)
    public Solar.ManagerOrganizationListDTO getAllWithoutDeleteOrganizations(@RequestParam("adId") Long adId,
                                                                             @RequestParam("draw")Integer draw,
                                                                             @RequestParam("length")Integer length,
                                                                             @RequestParam("start")Integer start) {

        try {
            return organizationBO.getAllWithoutDeleteOrgListByAreaId(adId, draw, length, start);
        } catch (Exception e) {
            return handleException(a -> Solar.ManagerOrganizationListDTO.newBuilder().setResult(a).build(), e);
        }
    }

    @RequestMapping(value = "/manager-organization/{orgId}", method = RequestMethod.DELETE)
    public Solar.ManagerOrganizationDTO managerDeleteOrganization(@PathVariable("orgId") Long orgId) {

        try {
            return organizationBO.markDeleteOrganizationById(orgId);
        } catch (Exception e) {
            return handleException(a -> Solar.ManagerOrganizationDTO.newBuilder().setResult(a).build(), e);
        }
    }
}
