package com.kaishengit.tms.service.impl;

import com.kaishengit.tms.entity.Permission;
import com.kaishengit.tms.entity.PermissionExample;
import com.kaishengit.tms.mapper.PermissionMapper;
import com.kaishengit.tms.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 角色和权限的业务类
 * @author fankay
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    public static final Logger logger = LoggerFactory.getLogger(RolePermissionServiceImpl.class);

    @Autowired
    private PermissionMapper permissionMapper;
    /**
     * 添加权限
     *
     * @param permission
     */
    @Override
    public void savePermission(Permission permission) {
        permission.setCreateTime(new Date());
        permissionMapper.insertSelective(permission);
        logger.info("添加权限 {}",permission);
    }

    /**
     * 根据权限类型查询权限列表
     *
     * @param permissionType 权限类型 菜单|按钮
     * @return 对应的权限列表
     */
    @Override
    public List<Permission> findPermissionByPermissionType(String permissionType) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPermissionTypeEqualTo(permissionType);
        return permissionMapper.selectByExample(permissionExample);
    }

    /**
     * 查询所有的权限
     *
     * @return
     */
    @Override
    public List<Permission> findAllPermission() {
        PermissionExample permissionExample = new PermissionExample();
        return permissionMapper.selectByExample(permissionExample);
    }
}
