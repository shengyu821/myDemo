package com.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.entity.UserInfo;
import com.mybatisplus.mapper.UserInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class PageTest {


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void test() {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(UserInfo::getName, "J").lt(UserInfo::getAge, 40);
        // 开始查询
        IPage<UserInfo> page = userInfoMapper.selectPage(new Page<>(1, 2), queryWrapper);
        System.out.println(page);
    }

    @Test
    public void testPageMap() {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(UserInfo::getName, "J").lt(UserInfo::getAge, 40);
        // 开始查询
        IPage<Map<String, Object>> page = userInfoMapper.selectMapsPage(new Page<>(1, 2), queryWrapper);
        System.out.println(page);
    }

    @Test
    public void testPage(){
        IPage<UserInfo> page = userInfoMapper.selectPage2(new Page<>(1,2), 10);
        System.out.println(page);
    }
}
