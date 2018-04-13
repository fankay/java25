package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.Permission;

import java.util.List;

/**
 * 角色和权限的业务类
 * @author fankay
 */
public interface RolePermissionService {

    /**
     * 添加权限
     * @param permission
     */
    void savePermission(Permission permission);

    /**
     * 根据权限类型查询权限列表
     * @param permissionType 权限类型 菜单|按钮
     * @return 对应的权限列表
     */
    List<Permission> findPermissionByPermissionType(String permissionType);

    /**
     * 查询所有的权限
     * @return
     */
    List<Permission> findAllPermission();

}
