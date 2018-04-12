package com.kaishengit.tms.service.impl;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.AccountExample;
import com.kaishengit.tms.entity.AccountLoginLog;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.mapper.AccountLoginLogMapper;
import com.kaishengit.tms.mapper.AccountMapper;
import com.kaishengit.tms.service.AccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 系统账号的业务类
 * @author fankay
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountLoginLogMapper accountLoginLogMapper;


    /**
     * 系统登录
     *
     * @param accountMobile 手机号码
     * @param password      密码
     * @param requestIp     登录的IP地址
     * @return 如果登录成功，则返回Account对象，如果登录失败返回null
     * @throws ServiceException 如果登录失败，则通过异常抛出具体的错误原因
     */
    @Override
    public Account login(String accountMobile, String password, String requestIp) throws ServiceException {
        //根据手机号码查找对应的账号
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andAccountMobileEqualTo(accountMobile);

        List<Account> accountList = accountMapper.selectByExample(accountExample);
        Account account = null;
        if(accountList != null && !accountList.isEmpty()) {
            account = accountList.get(0);
            //匹配密码
            if(account.getAccountPassword().equals(DigestUtils.md5Hex(password))) {
                //判断状态
                if(Account.STATE_NORMAL.equals(account.getAccountState())) {
                    //添加登录的日志
                    AccountLoginLog loginLog = new AccountLoginLog();
                    loginLog.setAccountId(account.getId());
                    loginLog.setLoginIp(requestIp);
                    loginLog.setLoginTime(new Date());

                    accountLoginLogMapper.insertSelective(loginLog);

                    logger.info("{} 登录系统",account);
                    return account;
                } else if(Account.STATE_LOCKED.equals(account.getAccountState())) {
                    throw new ServiceException("账号被锁定");
                } else {
                    throw new ServiceException("账号被禁用");
                }
            } else {
                throw new ServiceException("账号或密码错误");
            }
        } else {
            throw new ServiceException("账号或密码错误");
        }
    }
}
