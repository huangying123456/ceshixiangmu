package com.youhujia.solar.domain.organization;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by huangYing on 2017/4/17.
 */
@Transactional
public interface OriganizationDAO extends JpaRepository<Organization, Long> {
}
