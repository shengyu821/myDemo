package com.mybatisplus;

import com.mybatisplus.entity.Area;
import com.mybatisplus.entity.User;
import com.mybatisplus.mapper.AreaMapper;
import com.mybatisplus.mapper.UserInfoMapper;
import com.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MulTableTest {


    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AreaMapper areaMapper;


    @Test
    public void testOne() {
        User user = userMapper.getUserByIdOne(1);
        System.out.println(user);
    }

    @Test
    public void testMany() {
        Area area = areaMapper.getAreaById(1);
        System.out.println(area);
    }

}
