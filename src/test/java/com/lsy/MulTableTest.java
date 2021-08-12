package com.lsy;

import com.lsy.entity.Area;
import com.lsy.entity.User;
import com.lsy.mapper.AreaMapper;
import com.lsy.mapper.UserInfoMapper;
import com.lsy.mapper.UserMapper;
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
