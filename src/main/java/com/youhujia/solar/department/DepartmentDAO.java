package com.youhujia.solar.department;

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

    List<Department> findByOrganizationIdAndGuest(Long organizationId, Long isGuest);

    List<Department> findByOrganizationIdAndGuestAndStatus(Long organizationId, Long isGuest, Integer status);

    List<Department> findByOrganizationId(Long organizationId);

    List<Department> findByHostId(Long departmentId);

    List<Department> findByHostIdAndStatus(Long departmentId, Integer status);

    List<Department> findByNumber(String dptNum);

    List<Department> findByNumberAndStatus(String dptNum, Integer status);

    List<Department> findByOrganizationIdAndStatus(Long organizationId, Integer status);

    @Modifying
    @Query(value = "from Department d where d.organizationId = ?1 and d.status > ?2")
    List<Department> findByOrganizationIdWithStatus(Long orgId, Integer status);

    @Modifying
    @Query(value = "select d from Department d where d.guest = 0 and d.organizationId in :ids")
    List<Department> queryByOrganizationIds(@Param("ids") List<Long> organizationIds);

    Department findFirstByOrderByIdDesc();

    List<Department> findByIdIn(List<Long> ids);

}
