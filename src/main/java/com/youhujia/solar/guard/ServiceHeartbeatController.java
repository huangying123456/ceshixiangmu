package com.youhujia.solar.guard;

import com.youhujia.halo.common.BaseController;
import com.youhujia.halo.common.COMMON;
import com.youhujia.solar.department.DepartmentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by girls3 on 2017/6/26.
 */
@RestController
@RequestMapping(value = "/guard")
public class ServiceHeartbeatController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DepartmentDAO departmentDAO;

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public COMMON.SimpleResponse heartbeat(@RequestParam(value = "db") Boolean db) {

        try {
            logger.info("Heartbeat find first, id: " + departmentDAO.findFirstByOrderByIdDesc().getId().toString());
            return OK();
        } catch (Exception e) {
            return handleException(a -> COMMON.SimpleResponse.newBuilder().setResult(a).build(), e);
        }
    }
}

