package com.kaishengit.tms.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaishengit.tms.entity.Permission;
import com.kaishengit.tms.entity.PermissionExample;
import com.kaishengit.tms.entity.Roles;
import com.kaishengit.tms.entity.RolesPermissionExample;
import com.kaishengit.tms.entity.RolesPermissionKey;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.mapper.PermissionMapper;
import com.kaishengit.tms.mapper.RolesMapper;
import com.kaishengit.tms.mapper.RolesPermissionMapper;
import com.kaishengit.tms.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.ArrayList;
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
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private RolesPermissionMapper rolesPermissionMapper;
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
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        List<Permission> resultList = new ArrayList<>();
        treeList(permissionList,resultList,0);
        return resultList;
    }

    /**
     * 新增角色
     *
     * @param roles        角色对象
     * @param permissionId 角色对应的权限ID列表
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveRoles(Roles roles, Integer[] permissionId) {
        //保存角色
        roles.setCreateTime(new Date());
        rolesMapper.insertSelective(roles);
        //保存角色和权限的关系
        for(Integer perId : permissionId) {
            RolesPermissionKey rolesPermissionKey = new RolesPermissionKey();
            rolesPermissionKey.setPermissionId(perId);
            rolesPermissionKey.setRolesId(roles.getId());

            rolesPermissionMapper.insert(rolesPermissionKey);
        }
        logger.info("保存角色 {}",roles);
    }

    /**
     * 根据权限ID删除权限
     *
     * @param id
     * @throws ServiceException 删除失败抛出此异常，例如权限已经被角色使用
     */
    @Override
    public void delPermissionById(Integer id) throws ServiceException {
        //查询该权限是否有子节点
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andParentIdEqualTo(id);

        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        if(permissionList != null && !permissionList.isEmpty()) {
            throw new ServiceException("该权限下有子节点，删除失败");
        }

        //查询权限是否被角色使用
        RolesPermissionExample rolesPermissionExample = new RolesPermissionExample();
        rolesPermissionExample.createCriteria().andPermissionIdEqualTo(id);

        List<RolesPermissionKey> rolesPermissionKeyList = rolesPermissionMapper.selectByExample(rolesPermissionExample);
        if(rolesPermissionKeyList != null && !rolesPermissionKeyList.isEmpty()) {
            throw new ServiceException("该权限被角色引用，删除失败");
        }
        //如果没有被使用，则可以直接删除
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        permissionMapper.deleteByPrimaryKey(id);
        logger.info("删除权限 {}",permission);
    }

    /**
     * 将查询数据库的角色列表转换为树形集合结果
     * @param sourceList 数据库查询出的集合
     * @param endList 转换结束的结果集合
     * @param parentId 父ID
     */
    private void treeList(List<Permission> sourceList, List<Permission> endList, int parentId) {
        List<Permission> tempList = Lists.newArrayList(Collections2.filter(sourceList, permission -> permission.getParentId().equals(parentId)));

        for(Permission permission : tempList) {
            endList.add(permission);
            treeList(sourceList,endList,permission.getId());
        }
    }
}
