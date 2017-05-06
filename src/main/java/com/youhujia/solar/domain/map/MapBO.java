package com.youhujia.solar.domain.map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.solar.DepartmentStatusEnum;
import com.youhujia.halo.solar.OrganizationStatusEnum;
import com.youhujia.solar.domain.area.Area;
import com.youhujia.solar.domain.area.AreaDAO;
import com.youhujia.solar.domain.common.HttpConnectionUtils;
import com.youhujia.solar.domain.department.Department;
import com.youhujia.solar.domain.department.DepartmentDAO;
import com.youhujia.solar.domain.organization.Organization;
import com.youhujia.solar.domain.organization.OrganizationDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class MapBO {

    @Value("${map.areaInfoUrl}")
    String areaInfoUrl;

    @Value("${map.areaInfoParams}")
    String areaInfoParams;

    @Value("${map.readHospitalFileName}")
    String readHospitalFileName;

    @Value("${map.hospitalErrOutFileName}")
    String hospitalErrOutFileName;

    @Value("${map.amapAPIKey}")
    String amapAPIKey;

    @Value("${map.amapSearchPOIUrl}")
    String amapSearchPOIUrl;

    @Autowired
    AreaDAO areaDAO;

    @Autowired
    OrganizationDAO organizationDAO;

    @Autowired
    DepartmentDAO departmentDAO;

    Log log = LogFactory.getLog(Log.class);


    //--------------------------------读取存储数据（属于一次性接口）------------------

    public COMMON.Result resetAreaInfo() {

        StringBuffer stringBuffer = HttpConnectionUtils.sendGETRequest(areaInfoUrl, areaInfoParams);
        ObjectMapper mapper = new ObjectMapper();

        try {
            //拿到所有的数据
            JsonNode rootNode = mapper.readTree(stringBuffer.toString());
            //拿到所有省级的数据集合
            JsonNode provincesNode = rootNode.findValue("districts").get(0).findValue("districts");
            int pSize = provincesNode.size();
            for (int i = 0; i < pSize; i++) {
                //循环处理每个省的数据
                JsonNode provinceNode = provincesNode.get(i);
                String pName = provinceNode.findValue("name").asText();
                Area provinceArea = new Area();
                provinceArea.setName(pName);
                provinceArea.setParentId(-999L);

                provinceArea = areaDAO.save(provinceArea);
                Long pId = provinceArea.getId();

                //循环处理每个省下的市的数据
                JsonNode citiesNode = provinceNode.findValue("districts");
                int cSize = citiesNode.size();
                for (int j = 0; j < cSize; j++) {
                    JsonNode cityNode = citiesNode.get(j);
                    String cName = cityNode.findValue("name").asText();
                    Area cityArea = new Area();
                    cityArea.setName(cName);
                    cityArea.setParentId(pId);
                    cityArea = areaDAO.save(cityArea);
                    Long cId = cityArea.getId();

                    //循环处理每个行政区域的信息
                    JsonNode adsNode = cityNode.findValue("districts");
                    int idSize = adsNode.size();
                    for (int k = 0; k < idSize; k++) {
                        JsonNode idNode = adsNode.get(k);
                        String idName = idNode.findValue("name").asText();
                        Area idArea = new Area();
                        idArea.setName(idName);
                        idArea.setParentId(cId);
                        areaDAO.save(idArea);
                    }
                }

            }
        } catch (IOException e) {
            log.error("json解析失败");
        }
        return COMMON.Result.newBuilder().setDisplaymsg("success").setCode(200).build();
    }

    public COMMON.Result updateOrganizationDepartmentFromFile() {
        String fileName = readHospitalFileName;
        String godAPIKey = amapAPIKey;
        String godSearchUrl = amapSearchPOIUrl;
        COMMON.Result.Builder resultBuilder = COMMON.Result.newBuilder();
        //整理一下我们库内的数据 来对老的数据做一下补充
        improveOrganizationInfo();
        List<Organization> organizationList = organizationDAO.findAll();
        List<Department> departmentList = departmentDAO.findAll();
        Map<String, Organization> organizationMap = new HashMap<>();
        Map<Long, Map<String, Department>> departmentMap = new HashMap<>();
        for (Organization o : organizationList) {
            organizationMap.put(o.getName(), o);
        }
        Iterator<Organization> organizationIterator = organizationList.iterator();
        while (organizationIterator.hasNext()) {
            Organization organization = organizationIterator.next();
            Long id = organization.getId();
            Iterator<Department> departmentIterator = departmentList.iterator();
            Map<String, Department> temp = new HashMap<>();
            while (departmentIterator.hasNext()) {
                Department department = departmentIterator.next();
                if (department.getOrganizationId().equals(id)) {
                    temp.put(department.getName(), department);
                    departmentIterator.remove();
                }
            }
            departmentMap.put(id, temp);
        }

        //从文件逐行读取json（http://www.cnblogs.com/tangkai/p/4834688.html）
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(fileName)), 10 * 1024 * 1024);
            ObjectMapper mapper = new ObjectMapper();
            String tempString = null;
            //以下三个变量用于判断这条数据和上条数据是不是同一个医院，节省API的调用次数
            String lastHospitalName = new String("");
            String lastHsopitalRealName = new String("");
            boolean sameHospitalFlag = false;
            //{"hospital_name": "延庆县中医院", "province_name": "北京", "hospital_level": "(二级)", "city_name": "延庆", "department_name": "内科"}
            while ((tempString = reader.readLine()) != null) {
                tempString = tempString.replaceAll(" +", "");
                JsonNode node = mapper.readTree(tempString);
                String hospitalName = node.findValue("hospital_name").asText();
                String departmentName = node.findValue("department_name").asText();
                String address = new String();
                String level = new String();
                double lon = 0L;
                double lat = 0L;
                long areaId = 0L;

                if (!hospitalName.equals(lastHospitalName) && !hospitalName.equals(lastHsopitalRealName)) {
                    sameHospitalFlag = false;
                    lastHospitalName = hospitalName;

                    String provinceName = node.findValue("province_name").asText();
                    level = extendsLevelInfo(node.findValue("hospital_level").asText());

                    //调用高德地图API获取详细数据
                    StringBuffer params = new StringBuffer();
                    params.append("key=").append(godAPIKey).append("&keywords=").append(hospitalName).append("&city=")
                            .append(provinceName).append("&children=1&offset=20&page=1&extensions=all");
                    StringBuffer godSearchResult = HttpConnectionUtils.sendGETRequest(godSearchUrl, params.toString());
                    JsonNode godSearchResultNode = mapper.readTree(godSearchResult.toString());
                    JsonNode infoNode = godSearchResultNode.findValue("pois").get(0);
                    //尝试打一下log
                    log.info("params:" + params.toString());
                    if (infoNode == null || infoNode.size() < 1) {
                        logErrorHospitalInfo(tempString);
                        continue;
                    }
                    hospitalName = infoNode.findValue("name").asText();
                    //存储医院可能的真名
                    lastHsopitalRealName = hospitalName;
                    address = infoNode.findValue("address").asText();
                    String location = infoNode.findValue("location").asText();
                    lon = Double.parseDouble(location.split(",")[0]);
                    lat = Double.parseDouble(location.split(",")[1]);
                    areaId = getAdIdByPNameCNameAndAdName(infoNode.findValue("pname").asText(),
                            infoNode.findValue("cityname").asText(),
                            infoNode.findValue("adname").asText());
                } else {
                    sameHospitalFlag = true;
                }
                //若是已有的医院，则信息一定是更新过的，所以医院的信息不需要更新
                if (sameHospitalFlag) {
                    Long orgId = organizationMap.get(lastHsopitalRealName).getId();
                    Map<String, Department> map = departmentMap.get(orgId);
                    Department mayDepartment = map.get(departmentName);
                    //判断新爬到的科室是不是已经存在了,不存在才进行处理
                    if (mayDepartment == null || mayDepartment.getId() == null) {
                        Department newDepartment = new Department();
                        newDepartment.setName(departmentName);
                        newDepartment.setGuest(false);
                        newDepartment.setOrganizationId(orgId);
                        newDepartment.setStatus(DepartmentStatusEnum.UNAUTHORIZED.getStatus());
                        newDepartment = departmentDAO.save(newDepartment);
                        map.put(departmentName, newDepartment);
                        //更新map
                        departmentMap.put(orgId, map);
                    }
                    sameHospitalFlag = false;
                } else {
                    //查找是不是已有的医院、科室 并入库
                    Organization oldOrganization = organizationMap.get(hospitalName);
                    if (oldOrganization != null && oldOrganization.getId() != null) {
                        //医院存在，更新医院信息
                        oldOrganization.setAreaId(areaId);
                        oldOrganization.setAddress(address);
                        oldOrganization.setLat(new BigDecimal(lat));
                        oldOrganization.setLon(new BigDecimal(lon));
                        oldOrganization.setLevel(level);
                        oldOrganization = organizationDAO.save(oldOrganization);
                        //更新医院map
                        organizationMap.put(oldOrganization.getName(), oldOrganization);

                        //检查医院下对应科室是否存在
                        Long orgId = oldOrganization.getId();
                        Map<String, Department> map = departmentMap.get(orgId);
                        Department mayDepartment = map.get(departmentName);
                        if (mayDepartment == null || mayDepartment.getId() == null) {
                            Department newDepartment = new Department();
                            newDepartment.setName(departmentName);
                            newDepartment.setGuest(false);
                            newDepartment.setOrganizationId(orgId);
                            newDepartment.setStatus(DepartmentStatusEnum.UNAUTHORIZED.getStatus());
                            newDepartment = departmentDAO.save(newDepartment);
                            map.put(departmentName, newDepartment);
                            //更新map
                            departmentMap.put(orgId, map);
                        }
                    } else {
                        //医院不存在，新建医院 科室更不可能存在 同时也要新建科室
                        Organization newOrganization = new Organization();
                        newOrganization.setName(hospitalName);
                        newOrganization.setStatus(OrganizationStatusEnum.UNAUTHORIZED.getStatus());
                        newOrganization.setAddress(address);
                        newOrganization.setAreaId(areaId);
                        newOrganization.setLat(new BigDecimal(lat));
                        newOrganization.setLon(new BigDecimal(lon));
                        newOrganization.setLevel(level);
                        newOrganization = organizationDAO.save(newOrganization);
                        //更新map
                        organizationMap.put(newOrganization.getName(), newOrganization);

                        //存储科室的信息
                        Department newDepartment = new Department();
                        newDepartment.setName(departmentName);
                        newDepartment.setGuest(false);
                        newDepartment.setOrganizationId(newOrganization.getId());
                        newDepartment.setStatus(DepartmentStatusEnum.UNAUTHORIZED.getStatus());
                        newDepartment = departmentDAO.save(newDepartment);
                        //更新map
                        Map<String, Department> map = new HashMap<>();
                        map.put(newDepartment.getName(), newDepartment);
                        departmentMap.put(newOrganization.getId(), map);
                    }
                }
            }
        } catch (Exception e) {
            log.error("文件打开失败！");
            log.error(e.getMessage());
            e.printStackTrace();

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    reader = null;
                }
            }
        }


        return resultBuilder.setCode(0).setSuccess(true).setMsg("真难为你能等这么久，终于成功了").build();
    }

    /**
     * 开疆拓土
     *
     * @return
     */
    public COMMON.Result setOrganizationDepartmentFromFile() {
        String fileName = readHospitalFileName;
        String godAPIKey = amapAPIKey;
        String godSearchUrl = amapSearchPOIUrl;
        COMMON.Result.Builder resultBuilder = COMMON.Result.newBuilder();
        //创建一些用的到的空的数据结构
        Map<String, Organization> organizationMap = new HashMap<>();
        Map<Long, Map<String, Department>> departmentMap = new HashMap<>();

        //从文件逐行读取json（http://www.cnblogs.com/tangkai/p/4834688.html）
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(fileName)), 10 * 1024 * 1024);
            ObjectMapper mapper = new ObjectMapper();
            String tempString = null;
            //以下三个变量用于判断这条数据和上条数据是不是同一个医院，节省API和数据库的调用次数
            String lastHospitalName = new String("");
            String lastHsopitalRealName = new String("");
            boolean sameHospitalFlag = false;
            //{"hospital_name": "延庆县中医院", "province_name": "北京", "hospital_level": "(二级)", "city_name": "延庆", "department_name": "内科"}
            while ((tempString = reader.readLine()) != null) {
                tempString = tempString.replaceAll(" +", "");
                JsonNode node = mapper.readTree(tempString);
                String hospitalName = node.findValue("hospital_name").asText();
                String departmentName = node.findValue("department_name").asText();
                String address = new String();
                String level = new String();
                double lon = 0L;
                double lat = 0L;
                long areaId = 0L;

                if (!hospitalName.equals(lastHospitalName) && !hospitalName.equals(lastHsopitalRealName)) {
                    sameHospitalFlag = false;
                    lastHospitalName = hospitalName;

                    String provinceName = node.findValue("province_name").asText();
                    level = extendsLevelInfo(node.findValue("hospital_level").asText());

                    //调用高德地图API获取详细数据
                    StringBuffer params = new StringBuffer();
                    params.append("key=").append(godAPIKey).append("&keywords=").append(hospitalName).append("&city=")
                            .append(provinceName).append("&children=1&offset=20&page=1&extensions=all");
                    StringBuffer godSearchResult = HttpConnectionUtils.sendGETRequest(godSearchUrl, params.toString());
                    JsonNode godSearchResultNode = mapper.readTree(godSearchResult.toString());
                    JsonNode infoNode = godSearchResultNode.findValue("pois").get(0);
                    //尝试打一下log
                    log.info("params:" + params.toString());
                    if (infoNode == null || infoNode.size() < 1) {
                        logErrorHospitalInfo(tempString);
                        continue;
                    }
                    hospitalName = infoNode.findValue("name").asText();
                    //存储医院可能的真名
                    lastHsopitalRealName = hospitalName;
                    address = infoNode.findValue("address").asText();
                    String location = infoNode.findValue("location").asText();
                    lon = Double.parseDouble(location.split(",")[0]);
                    lat = Double.parseDouble(location.split(",")[1]);
                    areaId = getAdIdByPNameCNameAndAdName(infoNode.findValue("pname").asText(),
                            infoNode.findValue("cityname").asText(),
                            infoNode.findValue("adname").asText());
                } else {
                    sameHospitalFlag = true;
                }
                //若是已有的医院，则信息一定是更新过的，所以医院的信息不需要更新
                if (sameHospitalFlag) {
                    Long orgId = organizationMap.get(lastHsopitalRealName).getId();
                    Map<String, Department> map = departmentMap.get(orgId);
                    Department mayDepartment = map.get(departmentName);
                    //因为是开疆扩土，爬取到的科室一定不存在
                    Department newDepartment = new Department();
                    newDepartment.setName(departmentName);
                    newDepartment.setGuest(false);
                    newDepartment.setOrganizationId(orgId);
                    newDepartment.setStatus(DepartmentStatusEnum.UNAUTHORIZED.getStatus());
                    newDepartment = departmentDAO.save(newDepartment);
                    map.put(departmentName, newDepartment);
                    //更新map
                    departmentMap.put(orgId, map);
                    sameHospitalFlag = false;
                } else {
                    //若sameHospitalFlag为false 则医院一定不存在
                    Organization newOrganization = new Organization();
                    newOrganization.setName(hospitalName);
                    newOrganization.setStatus(OrganizationStatusEnum.UNAUTHORIZED.getStatus());
                    newOrganization.setAddress(address);
                    newOrganization.setAreaId(areaId);
                    newOrganization.setLat(new BigDecimal(lat));
                    newOrganization.setLon(new BigDecimal(lon));
                    newOrganization.setLevel(level);
                    newOrganization = organizationDAO.save(newOrganization);
                    //更新map
                    organizationMap.put(newOrganization.getName(), newOrganization);

                    //存储科室的信息
                    Department newDepartment = new Department();
                    newDepartment.setName(departmentName);
                    newDepartment.setGuest(false);
                    newDepartment.setOrganizationId(newOrganization.getId());
                    newDepartment.setStatus(DepartmentStatusEnum.UNAUTHORIZED.getStatus());
                    newDepartment = departmentDAO.save(newDepartment);
                    //更新map
                    Map<String, Department> map = new HashMap<>();
                    map.put(newDepartment.getName(), newDepartment);
                    departmentMap.put(newOrganization.getId(), map);
                }
            }
        } catch (Exception e) {
            log.error("文件打开失败！");
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    reader = null;
                }
            }
        }

        return resultBuilder.setCode(0).setSuccess(true).setMsg("真难为你能等这么久，终于成功了").build();
    }

    private String extendsLevelInfo(String level) {
        String str1 = new String();
        String str2 = new String();
        //\(([一|二|三])([特|甲|乙|丙])
        String regex1 = "\\(([一|二|三])([特|甲|乙|丙])";
        String regex2 = "\\(([一|二|三])";
        Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(level);
        if (matcher.find()) {
            str1 = matcher.group(1);
            str2 = matcher.group(2);
            return str1 + "级" + str2 + "等";
        }

        pattern = Pattern.compile(regex2);
        matcher = pattern.matcher(level);
        if (matcher.find()) {
            str1 = matcher.group(1);
            return str1 + "级医院";
        }

        return "其他";
    }

    private void logErrorHospitalInfo(String jsonStr) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(hospitalErrOutFileName), true)));
            out.write(jsonStr + "\r\n");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    out = null;
                }
            }
        }
    }

    private Long getAdIdByPNameCNameAndAdName(String pName, String cName, String adName) {
        Long pId = areaDAO.findByName(pName).get(0).getId();
        List<Area> cities = areaDAO.findByParentId(pId);
        Long cId = -1L;
        for (Area temp : cities) {
            if (temp.getName().equals(cName)) {
                cId = temp.getId();
                break;
            }
        }
        Long adId = 0L;
        List<Area> ads = areaDAO.findByParentId(cId);
        for (Area temp : ads) {
            if (temp.getName().equals(adName)) {
                adId = temp.getId();
                break;
            }
        }
        return adId;
    }

    public Map<Long, Area> getAreaIdObjMap() {
        List<Area> list = areaDAO.findAll();
        Map<Long, Area> map = new HashMap<>();
        for (Area a : list) {
            map.put(a.getId(), a);
        }

        return map;
    }

    public void improveOrganizationInfo() {
        List<Organization> organizationList = organizationDAO.findAll();
        Map<Long, Area> map = getAreaIdObjMap();
        ObjectMapper mapper = new ObjectMapper();
        for (Organization o : organizationList) {
            if (o.getAreaId() == null) {
                //调用高德地图API获取详细数据
                try {
                    StringBuffer params = new StringBuffer();
                    //这个查询是以关键字为先，如果关键字足够，省份的信息会被忽略掉（已测试）
                    params.append("key=").append(amapAPIKey).append("&keywords=").append(o.getName()).append("&city=")
                            .append("北京").append("&children=1&offset=20&page=1&extensions=all");
                    StringBuffer godSearchResult = HttpConnectionUtils.sendGETRequest(amapSearchPOIUrl, params.toString());
                    JsonNode godSearchResultNode = mapper.readTree(godSearchResult.toString());
                    JsonNode infoNode = godSearchResultNode.findValue("pois").get(0);
                    //尝试打一下log
                    log.info("params:" + params.toString());
                    if (infoNode == null || infoNode.size() < 1) {
                        continue;
                    }
                    String address = infoNode.findValue("address").asText();
                    String location = infoNode.findValue("location").asText();
                    Double lon = Double.parseDouble(location.split(",")[0]);
                    Double lat = Double.parseDouble(location.split(",")[1]);
                    Long areaId = getAdIdByPNameCNameAndAdName(infoNode.findValue("pname").asText(),
                            infoNode.findValue("cityname").asText(),
                            infoNode.findValue("adname").asText());
                    o.setAddress(address);
                    o.setLat(new BigDecimal(lat));
                    o.setLon(new BigDecimal(lon));
                    o.setAreaId(areaId);
                    organizationDAO.save(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
