package com.youhujia.solar.organization;

import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
@Transactional
public interface OrganizationDAO extends JpaRepository<Organization, Long> {

    List<Organization> findByStatus(Integer status);

    List<Organization> findByAreaIdAndStatus(Long areaId, Integer status);

    @Modifying
    @Query(value = "from Organization o where o.status > ?1")
    List<Organization> findWithStatus(Integer status);

    @Modifying
    @Query(value = "from Organization o where o.areaId = ?1 and o.status > ?2")
    List<Organization> findbyAreaIdWithStatus(Long areaId, Integer status);

    String select = "select o from Organization o";
    String count = "select count(o) from Organization o";

    @Query(value = select, countQuery = count)
    Page<Organization> queryOrganizations(Pageable pageable);
}
