package com.weiyang.service.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weiyang.service.system.entity.SystemTable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ISystemService {

    List<SystemTable> list();

    Page<SystemTable> page();
}
