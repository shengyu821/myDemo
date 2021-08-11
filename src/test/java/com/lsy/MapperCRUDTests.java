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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MapperCRUDTests {

    @Autowired
    private UserInfoMapper userInfoMapper;


    // selectById
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
        // 查询id为 1,2,3的用户
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        List<UserInfo> userInfos = userInfoMapper.selectBatchIds(ids);
        System.out.println(userInfos);
    }

    // selectByMap
    @Test
    public void testSelectByMap() {
        // 查询名字为Tom，年龄28岁
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "Tom");
        columnMap.put("age", 28);
        List<UserInfo> userInfos = userInfoMapper.selectByMap(columnMap);
        System.out.println(userInfos);
    }


    // selectList
    @Test
    public void testSelectList() {
        // 查询条件：名字中包含'Tom'并且年龄小于28
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "Tom").lt("age", 28);
        // 开始查询
        List<UserInfo> users = userInfoMapper.selectList(queryWrapper);
    }

    @Test
    public void testSelectList2() {
        // 查询条件：名字中包含'Tom'并且年龄小于28
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(UserInfo::getName, "Tom").lt(UserInfo::getAge, 28);
        // 开始查询
        List<UserInfo> users = userInfoMapper.selectList(queryWrapper);
    }


    @Test
    public void testSelectList3() {
        // 查询条件：名字中包含'Tom'并且年龄小于28
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .like(UserInfo::getName, "Tom")
                .lt(UserInfo::getAge, 28)
                .list();
    }

    @Test
    public void testSelectList4() {
        // 查询条件：名字为'Tom'并且年龄为28
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Tom");
        userInfo.setAge(28);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>(userInfo);
        // 开始查询
        List<UserInfo> users = userInfoMapper.selectList(queryWrapper);
        System.out.println(users);
    }




    // selectMaps
    @Test
    public void testSelectMaps() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Tom");
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>(userInfo);
        // 开始查询
        List<Map<String, Object>> users = userInfoMapper.selectMaps(queryWrapper);
        System.out.println(users);
    }


    // selectObjs
    @Test
    public void testSelectObjs() {
        UserInfo userInfo = new UserInfo();
        userInfo.setAge(28);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>(userInfo);
        // 开始查询
        List<Object> users = userInfoMapper.selectObjs(queryWrapper);
        System.out.println(users);
    }

    // selectCount
    @Test
    public void testSelectCount() {
        UserInfo userInfo = new UserInfo();
        userInfo.setAge(28);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>(userInfo);
        // 开始查询
        Integer count = userInfoMapper.selectCount(queryWrapper);
        System.out.println(count);
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

}
