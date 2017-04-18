package com.youhujia.solar.domain.organization;

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
    List<Organization> findByStatus(Byte status);

    List<Organization> findByAreaIdAndStatus(Long areaId, Byte status);

    @Modifying
    @Query(value = "from Organization o where o.status > ?1")
    List<Organization> findWithStatus(Byte status);

    @Modifying
    @Query(value = "from Organization o where o.areaId = ?1 and o.status > ?2")
    List<Organization> findbyAreaIdWithStatus(Long areaId, Byte status);
}
