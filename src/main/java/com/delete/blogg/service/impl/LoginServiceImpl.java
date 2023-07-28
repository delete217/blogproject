package com.delete.blogg.service.impl;

import com.alibaba.fastjson.JSON;
import com.delete.blogg.entity.SysUser;
import com.delete.blogg.service.ISysUserService;
import com.delete.blogg.service.LoginService;
import com.delete.blogg.utils.JwtUtils;
import com.delete.blogg.vo.EnumError;
import com.delete.blogg.vo.Result;
import com.delete.blogg.vo.params.LoginParams;
import net.minidev.json.JSONArray;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 1. 判断account password是否合法
     * 2. 根据account password在表中查询是否存在
     * 3. 不存在则登录失败
     * 4. 存在 ， 生成token返回给前端
     * 5. token放入redis中  key:value  token：user信息
     *   （登录认证时，先认证token字符串是否合法，去redis查看是否存在）
     * @return
     */
    @Override
    public Result login(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        password = DigestUtils.md5Hex(password);
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
            return Result.fail(EnumError.PARAMS_ERROR.getCode(),EnumError.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser =  sysUserService.findUser(account,password);
        if (sysUser == null){
            return Result.fail(EnumError.ACCOUNT_PWD_NOT_EXIST.getCode(), EnumError.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JwtUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("Token_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JwtUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("Token_" + token);
        if (StringUtils.isEmpty(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson,SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("Token_"+token);
        return Result.success("token删除成功");
    }

    @Override
    public Result register(LoginParams loginParams) {
        /**
         * 1. 判断参数是否合法
         * 2. 判断账户是否已存在 已存在需要返回账户已存在
         * 3. 如果不存在，就注册用户
         * 4. 生成token 存入Redis并返回
         * 5. 注意：需要加上事务 注册流程的任意过程出现问题 需要回滚
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)){
            return Result.fail(EnumError.PARAMS_ERROR.getCode(), EnumError.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(EnumError.ACCOUNT_EXIST_ALREADY.getCode(), EnumError.ACCOUNT_EXIST_ALREADY.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setNickname(nickname);
        sysUser.setAdmin(true);
        sysUser.setAvatar("/new photo");
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setEmail("");
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setPassword(DigestUtils.md5Hex(password));
        sysUser.setSalt("");
        this.sysUserService.saveUser(sysUser);

        String token = JwtUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("Token_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
