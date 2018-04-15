package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.exception.ServiceException;

import java.util.List;

/**
 * 系统账号的业务类
 * @author fankay
 */
public interface AccountService {

    /**
     * 系统登录
     * @param accountMobile 手机号码
     * @param password 密码
     * @param requestIp 登录的IP地址
     * @return 如果登录成功，则返回Account对象，如果登录失败返回null
     * @throws ServiceException 如果登录失败，则通过异常抛出具体的错误原因
     */
    Account login(String accountMobile, String password, String requestIp) throws ServiceException;

    /**
     * 新增账号
     * @param account 账号对象
     * @param rolesIds 账号拥有的角色ID数字
     */
    void saveAccount(Account account, Integer[] rolesIds);

    /**
     * 查询所有账号并加载对应的角色列表
     * @return
     */
    List<Account> findAllAccountWithRoles();
}
