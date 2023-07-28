package com.delete.blogg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delete.blogg.entity.Admin;
import com.delete.blogg.mapper.AdminMapper;
import com.delete.blogg.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
