package com.lsy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsy.entity.User;
import com.lsy.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class CRUDTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        // 参数是一个 Wrapper ，条件构造器，这里我们先不用 null
        // 查询全部用户
        List<User> users = userMapper.selectList(null);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("mybatisplus");
        user.setAge(3);
        user.setEmail("123456@qq.com");
        int result = userMapper.insert(user); // 帮我们自动生成id
        System.out.println(result); // 受影响的行数
        System.out.println(user); // 发现，id会自动回填
    }


    // 测试更新
    @Test
    public void testUpdate() {
        User user = new User();
        // 通过条件自动拼接动态sql

        user.setId(6L);
        user.setName("mybatisplus");
        user.setAge(18);
        // 注意：updateById 但是参数是一个 对象！
        int i = userMapper.updateById(user);
        System.out.println(i);
    }


    // 测试查询
    @Test
    public void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }


    //SelectOne
    @Test
    public void testSelectOne() {
        // 查询条件：名字为Tom的用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Tom");
        // 开始查询
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void testSelectOne2() {
        // 查询条件：名字为Tom的用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, "Tom");
        // 开始查询
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void testSelectOne3() {
        User user = new LambdaQueryChainWrapper<>(userMapper)
                .eq(User::getName, "Tom")
                .one();
        System.out.println(user);
    }

    @Test
    public void testSelectOne4() {
        User user = new User();
        user.setName("Tom");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        // 开始查询
        User user2 = userMapper.selectOne(queryWrapper);
        System.out.println(user2);
    }


    // SelectBatchIds
    @Test
    public void testSelectBatchIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        List<User> users = userMapper.selectBatchIds(ids);
        System.out.println(users);
    }

    @Test
    public void testSelectByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "Tom");
        columnMap.put("age", 28);
        List<User> users = userMapper.selectByMap(columnMap);
        System.out.println(users);
    }


    // 测试批量查询！
    @Test
    public void testSelectByBatchId() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
    }

    // 按条件查询之一使用map操作
    @Test
    public void testSelectByBatchIds() {
        HashMap<String, Object> map = new HashMap<>();
        // 自定义查询
        map.put("name", "mybatisplus");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    // 测试分页查询
    @Test
    public void testPage() {
        // 参数一：当前页
        // 参数二：页面大小
        // 使用了分页插件之后，所有的分页操作也变得简单的！
        Page<User> page = new Page<>(2, 5);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }


}
