package com.youhujia.solar.domain.department;

import javax.transaction.Transactional;

/**
 * Created by huangYing on 2017/4/17.
 */
@Transactional
public interface DepartmentDAO extends JpaRepository<Department, Long> {
}
