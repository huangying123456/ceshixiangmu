package com.youhujia.solar.domain.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
@Transactional
public interface DepartmentDAO extends JpaRepository<Department, Long> {
    List<Department> findByOrganizationIdAndGuest(Long organizationId, Boolean isGuest);

    List<Department> findByOrganizationIdAndGuestAndStatus(Long organizationId, Boolean isGuest, Byte status);

    List<Department> findByOrganizationId(Long organizationId);

    List<Department> findByHostId(Long departmentId);

    List<Department> findByHostIdAndStatus(Long departmentId, Byte status);

    List<Department> findByNumber(String dptNum);

    List<Department> findByNumberAndStatus(String dptNum, Byte status);

    List<Department> findByOrganizationIdAndStatus(Long organizationId, Byte status);

    @Modifying
    @Query(value = "from Department d where d.organizationId = ?1 and d.status > ?2")
    List<Department> findByOrganizationIdWithStatus(Long orgId, Byte status);

    @Modifying
    @Query(value = "select d from Department d where d.guest = false and d.organizationId in :ids")
    List<Department> queryByOrganizationIds(@Param("ids") List<Long> organizationIds);

}
