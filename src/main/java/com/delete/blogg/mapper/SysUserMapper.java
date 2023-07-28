package com.delete.blogg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.delete.blogg.entity.SysUser;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
public interface SysUserMapper extends BaseMapper<SysUser> {


    SysUser findUserByAuthorId(Long authorId);
}
