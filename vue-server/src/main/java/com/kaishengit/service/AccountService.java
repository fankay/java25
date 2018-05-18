package com.kaishengit.service;

import com.kaishengit.entity.Account;
import com.kaishengit.entity.AccountExample;
import com.kaishengit.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    public Account login(String userName, String password) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andUserNameEqualTo(userName);

        List<Account> accounts = accountMapper.selectByExample(accountExample);
        Account account = accounts != null && !accounts.isEmpty() ? accounts.get(0) : null;
        if(account == null) {
            throw new RuntimeException("账号或密码错误");
        } else {
            if(account.getPassword().equals(password)) {
                return account;
            } else {
                throw new RuntimeException("账号或密码错误");
            }
        }
    }

    public Account findAccountById(Integer accountId) {
        return accountMapper.selectByPrimaryKey(accountId);
    }
}
