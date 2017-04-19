package com.youhujia.solar.domain.map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youhujia.halo.common.COMMON;
import com.youhujia.halo.solar.Solar;
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

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ljm on 2017/4/18.
 */
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
    AreaDAO areaDao;

    @Autowired
    OrganizationDAO organizationDAO;

    @Autowired
    DepartmentDAO departmentDAO;

    Log log = LogFactory.getLog(Log.class);

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

                provinceArea = areaDao.save(provinceArea);
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
                    cityArea = areaDao.save(cityArea);
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
                        areaDao.save(idArea);
                    }
                }

            }
        } catch (IOException e) {
            log.error("json解析失败");
        }
        return COMMON.Result.newBuilder().setDisplaymsg("success").setCode(200).build();
    }

    public Solar.MapDTO getMapSet() {
        COMMON.Result.Builder resultBuilder = COMMON.Result.newBuilder();
        Solar.MapDTO.Builder mapDTOBuilder = Solar.MapDTO.newBuilder();
        Solar.MapDataDTO.Builder mapDataDTOBuilder = Solar.MapDataDTO.newBuilder();
        //开始时是所有的地理区域，随着每次的遍历 会删除遍历出来的数据 对此数据结构的遍历的复杂度会逐渐降低（此数据结构size理论值为3625）
        List<Area> leftAreas = areaDao.findAll();
        //拿到所有的省的信息
        List<Area> provinceAreas = new ArrayList<>();
        Iterator<Area> leftIterator = leftAreas.iterator();
        while (leftIterator.hasNext()) {
            Area temp = leftIterator.next();
            if (temp.getParentId() < 0) {
                provinceAreas.add(temp);
                leftIterator.remove();
            }
        }
        //遍历省的信息来寻找其下的城市
        for (Area provinceArea : provinceAreas) {
            Solar.ProvinceData.Builder provinceBuilder = Solar.ProvinceData.newBuilder();
            provinceBuilder.setPId(provinceArea.getId());
            provinceBuilder.setPName(provinceArea.getName());
            Long pId = provinceArea.getId();
            List<Area> cityAreas = new ArrayList<>();
            leftIterator = leftAreas.iterator();
            while (leftIterator.hasNext()) {
                Area temp = leftIterator.next();
                if (temp.getParentId().equals(pId)) {
                    cityAreas.add(temp);
                    leftIterator.remove();
                }
            }
            //遍历所有城市来寻找其下的行政区域划分
            for (Area cityArea : cityAreas) {
                Solar.CityData.Builder cityBuilder = Solar.CityData.newBuilder();
                cityBuilder.setCityId(cityArea.getId());
                cityBuilder.setCityName(cityArea.getName());
                Long cId = cityArea.getId();
                //最后一层遍历 不需要再浪费list来存储信息了
                leftIterator = leftAreas.iterator();
                while (leftIterator.hasNext()) {
                    Area adArea = leftIterator.next();
                    if (adArea.getParentId().equals(cId)) {
                        Solar.AdministrativeDivisionData.Builder adBuilder = Solar.AdministrativeDivisionData.newBuilder();
                        adBuilder.setAdId(adArea.getId());
                        adBuilder.setAdName(adArea.getName());
                        //区域存入城市的protobuf里面
                        cityBuilder.addAdministrativeDivisions(adBuilder);
                        leftIterator.remove();
                    }
                }
                //某个城市的区域信息已经全部存入，它可以回归省了
                provinceBuilder.addCities(cityBuilder);
            }
            //某个省的区域信息已经全部存入，它可以回归DTO了
            mapDataDTOBuilder.addProvinces(provinceBuilder);
        }
        //整理最后的protobuf
        mapDTOBuilder.setData(mapDataDTOBuilder);
        resultBuilder.setCode(0);
        resultBuilder.setSuccess(true);
        resultBuilder.setMsg("成功！请缓存到本地");
        mapDTOBuilder.setResult(resultBuilder);
        return mapDTOBuilder.build();
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
                        newDepartment.setStatus(new Byte("0"));
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
                            newDepartment.setStatus(new Byte("0"));
                            newDepartment = departmentDAO.save(newDepartment);
                            map.put(departmentName, newDepartment);
                            //更新map
                            departmentMap.put(orgId, map);
                        }
                    } else {
                        //医院不存在，新建医院 科室更不可能存在 同时也要新建科室
                        Organization newOrganization = new Organization();
                        newOrganization.setName(hospitalName);
                        newOrganization.setStatus(new Byte("0"));
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
                        newDepartment.setStatus(new Byte("0"));
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
                    newDepartment.setStatus(new Byte("0"));
                    newDepartment = departmentDAO.save(newDepartment);
                    map.put(departmentName, newDepartment);
                    //更新map
                    departmentMap.put(orgId, map);
                    sameHospitalFlag = false;
                } else {
                    //若sameHospitalFlag为false 则医院一定不存在
                    Organization newOrganization = new Organization();
                    newOrganization.setName(hospitalName);
                    newOrganization.setStatus(new Byte("0"));
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
                    newDepartment.setStatus(new Byte("0"));
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
        Long pId = areaDao.findByName(pName).get(0).getId();
        List<Area> cities = areaDao.findByParentId(pId);
        Long cId = -1L;
        for (Area temp : cities) {
            if (temp.getName().equals(cName)) {
                cId = temp.getId();
                break;
            }
        }
        Long adId = 0L;
        List<Area> ads = areaDao.findByParentId(cId);
        for (Area temp : ads) {
            if (temp.getName().equals(adName)) {
                adId = temp.getId();
                break;
            }
        }
        return adId;
    }

    public Map<Long, Area> getAreaIdObjMap() {
        List<Area> list = areaDao.findAll();
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
