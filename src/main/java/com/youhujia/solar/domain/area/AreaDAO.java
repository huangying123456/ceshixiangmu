package com.youhujia.solar.domain.area;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by huangYing on 2017/4/17.
 */
@Transactional
public interface AreaDAO extends JpaRepository<Area, Long> {
    List<Area> findByParentId(Long parentId);
    List<Area> findByName(String name);
}
