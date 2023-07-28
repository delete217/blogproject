package com.delete.blogg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.delete.blogg.entity.SysUser;
import com.delete.blogg.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
public interface ISysUserService extends IService<SysUser> {
    SysUser findUserByAuthorId(Long authorId);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void saveUser(SysUser sysUser);
}
