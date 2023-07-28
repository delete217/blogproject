package com.delete.blogg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delete.blogg.entity.AdminPermission;
import com.delete.blogg.mapper.AdminPermissionMapper;
import com.delete.blogg.service.IAdminPermissionService;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
@Service
public class AdminPermissionServiceImpl extends ServiceImpl<AdminPermissionMapper, AdminPermission> implements IAdminPermissionService {

}
