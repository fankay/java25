package com.kaishengit.shiro.realm;

import com.kaishengit.entity.Account;
import com.kaishengit.service.AccountService;
import com.kaishengit.shiro.token.JwtToken;
import com.kaishengit.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 验证权限和角色
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 验证登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        //从jwtToken对象中取出token xxx.xxx.xxx
        String token = (String) jwtToken.getPrincipal();
        //根据token取出账号ID
        Integer accountId = jwtUtil.getUserIdFromToken(token);
        //根据accountId查询Account
        Account account = accountService.findAccountById(accountId);
        if(account == null) {
            throw new UnknownAccountException("该账号不存在");
        } else {
            //验证Token是否正确
            try {
                jwtUtil.verifyToken(accountId,account.getPassword(),token);
            } catch (Exception e) {
                e.printStackTrace();
                throw new AuthenticationException(e.getMessage(),e);
            }
        }
        return new SimpleAuthenticationInfo(token,token,getName());
    }
}
