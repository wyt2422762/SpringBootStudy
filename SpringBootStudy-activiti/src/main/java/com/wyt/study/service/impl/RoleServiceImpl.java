package com.wyt.study.service.impl;

import com.wyt.study.bean.Role;
import com.wyt.study.mapper.RoleMapper;
import com.wyt.study.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }
}
