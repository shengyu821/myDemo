package com.lsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.entity.UserInfo;
import com.lsy.mapper.UserInfoMapper;
import com.lsy.service.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
