package com.lsy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsy.entity.UserInfo;
import com.lsy.mapper.UserInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class ServiceCRUDTests {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    void contextLoads() {
        // 参数是一个 Wrapper ，条件构造器，这里我们先不用 null
        // 查询全部用户
        List<UserInfo> userInfos = userInfoMapper.selectList(null);
    }

    @Test
    public void testInsert() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("mybatisplus");
        userInfo.setAge(3);
        userInfo.setEmail("123456@qq.com");
        int result = userInfoMapper.insert(userInfo); // 帮我们自动生成id
        System.out.println(result); // 受影响的行数
        System.out.println(userInfo); // 发现，id会自动回填
    }


    // 测试更新
    @Test
    public void testUpdate() {
        UserInfo userInfo = new UserInfo();
        // 通过条件自动拼接动态sql

        userInfo.setId(6L);
        userInfo.setName("mybatisplus");
        userInfo.setAge(18);
        // 注意：updateById 但是参数是一个 对象！
        int i = userInfoMapper.updateById(userInfo);
        System.out.println(i);
    }


    // 测试查询
    @Test
    public void testSelectById() {
        UserInfo userInfo = userInfoMapper.selectById(1L);
        System.out.println(userInfo);
    }


    //SelectOne
    @Test
    public void testSelectOne() {
        // 查询条件：名字为Tom的用户
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Tom");
        // 开始查询
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        System.out.println(userInfo);
    }

    @Test
    public void testSelectOne2() {
        // 查询条件：名字为Tom的用户
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getName, "Tom");
        // 开始查询
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        System.out.println(userInfo);
    }

    @Test
    public void testSelectOne3() {
        UserInfo userInfo = new LambdaQueryChainWrapper<>(userInfoMapper)
                .eq(UserInfo::getName, "Tom")
                .one();
        System.out.println(userInfo);
    }

    @Test
    public void testSelectOne4() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Tom");
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>(userInfo);
        // 开始查询
        UserInfo userInfo2 = userInfoMapper.selectOne(queryWrapper);
        System.out.println(userInfo2);
    }


    // SelectBatchIds
    @Test
    public void testSelectBatchIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        List<UserInfo> userInfos = userInfoMapper.selectBatchIds(ids);
        System.out.println(userInfos);
    }

    @Test
    public void testSelectByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "Tom");
        columnMap.put("age", 28);
        List<UserInfo> userInfos = userInfoMapper.selectByMap(columnMap);
        System.out.println(userInfos);
    }


    // 测试批量查询！
    @Test
    public void testSelectByBatchId() {
        List<UserInfo> userInfos = userInfoMapper.selectBatchIds(Arrays.asList(1, 2, 3));
    }

    // 按条件查询之一使用map操作
    @Test
    public void testSelectByBatchIds() {
        HashMap<String, Object> map = new HashMap<>();
        // 自定义查询
        map.put("name", "mybatisplus");
        map.put("age", 18);
        List<UserInfo> userInfos = userInfoMapper.selectByMap(map);
        userInfos.forEach(System.out::println);
    }

    // 测试分页查询
    @Test
    public void testPage() {
        // 参数一：当前页
        // 参数二：页面大小
        // 使用了分页插件之后，所有的分页操作也变得简单的！
        Page<UserInfo> page = new Page<>(2, 5);
        userInfoMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }


}
