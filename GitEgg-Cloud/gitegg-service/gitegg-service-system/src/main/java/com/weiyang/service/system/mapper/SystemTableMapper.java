package com.weiyang.service.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weiyang.service.system.entity.SystemTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemTableMapper {

    List<SystemTable> list();

    List<SystemTable> page(Page<SystemTable> page);
}
