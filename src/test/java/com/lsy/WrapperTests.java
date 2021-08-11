package com.lsy;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.lsy.entity.Area;
import com.lsy.entity.UserInfo;
import com.lsy.mapper.AreaMapper;
import com.lsy.mapper.UserInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class WrapperTests {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private AreaMapper areaMapper;


    // 相等、不相等
    @Test
    public void testAllEq() {
        Map<SFunction<UserInfo, ?>, Object> map = new HashMap<>();
        map.put(UserInfo::getId, 3);
        map.put(UserInfo::getName, "xxname");
        map.put(UserInfo::getAge, null);

        //allEq({id:1,user_name:“xxname”,age:null}) -> id = 1 and user_name = ‘xxname’ and age is null
        //allEq({id:1,user_name:“xxname”,age:null}, false) —> id = 1 and user_name = 'xxname’

        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .allEq(map)
                .list();
    }

    @Test
    public void testEq() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .eq(UserInfo::getId, 1) // id = 1
                .ne(UserInfo::getAge, 22) // age <> 22
                .list();
    }


    // 大于、小于
    @Test
    public void testGt() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .gt(UserInfo::getId, 1) // id > 1
                .ge(UserInfo::getAge, 22) // age >=22
                .lt(UserInfo::getId, 3) // id < 3
                .le(UserInfo::getAge, 50) // age <=50
                .list();
    }

    // between
    @Test
    public void testBetween() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .between(UserInfo::getId, 1, 3) // id between 1 and 3
                .notBetween(UserInfo::getAge, 40, 50) // age not between 40 and 50
                .list();
    }

    // 模糊查询
    @Test
    public void testLike() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .like(UserInfo::getName, "ha") // name like '%ha%'
                .likeLeft(UserInfo::getName, "ha") // name like '%ha'
                .likeRight(UserInfo::getName, "ha") // name like 'ha%'
                .list();
    }

    @Test
    public void testNotLike() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .notLike(UserInfo::getName, "xxname") // name not like '%xxname%'
                .list();
    }

    //是否为 null
    @Test
    public void testNul() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .isNull(UserInfo::getName) // name is null
                .isNotNull(UserInfo::getAge) // age is not null
                .list();
    }

    // in、notIn
    @Test
    public void testIn() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .in(UserInfo::getId, Arrays.asList(1, 2, 3)) // id in (1,2,3)
                .notIn(UserInfo::getAge, Arrays.asList(22, 33)) // age not in (22,33)
                .list();
    }

    // 子查询
    @Test
    public void testInSql() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .inSql(UserInfo::getAge, "22,33")
                // age in (22,33)
                .inSql(UserInfo::getId, "select id from vip where level > 3")
                // id in (select id from vip where level > 3)
                .list();
    }

    @Test
    public void testNotInSql() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .notInSql(UserInfo::getAge, "22,33") // age not in (22,33)
                // id not in (select id from vip where level > 3)
                .notInSql(UserInfo::getId, "select id from vip where level > 3")
                .list();
    }

    //排序
    @Test
    public void testOrderBy() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .orderByAsc(UserInfo::getId) // ORDER BY id ASC
                .orderByDesc(UserInfo::getAge) // ORDER BY age DESC
                .list();
    }

    //分组、筛选
    @Test
    public void testGroupBy() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .groupBy(UserInfo::getAge) // group by age
                .having("sum(age) > 20") // HAVING sum(age) > 20
                .having("sum(age) > {0}", 30) // HAVING sum(age) > 30
                .select(UserInfo::getName, UserInfo::getAge)
                .list();
    }

    // or、 and、nested
    @Test
    public void testOr() {
        // WHERE age = 22 or age = 33
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .eq(UserInfo::getAge, 22)
                .or()
                .eq(UserInfo::getAge, 33)
                .list();
    }

    @Test
    public void testNested() {
        // WHERE age IS NOT NULL AND ((id = 1 AND name = 'xxname') OR (id = 2 AND name = 'xxname'))
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .isNotNull(UserInfo::getAge)
                .and(
                        i -> i.nested(
                                j -> j.eq(UserInfo::getId, 1)
                                        .eq(UserInfo::getName, "xxname")
                        ).or(j -> j.eq(UserInfo::getId, 2).eq(UserInfo::getName, "xxname"))
                )
                .list();
    }

    //拼接 sql
    @Test
    public void testApply() {
        // WHERE age IS NOT NULL AND id = 3 AND user_name = 'xxname'
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .isNotNull(UserInfo::getAge)
                .apply("id = 3") // 有sql注入的风险
                .apply("user_name = {0}", "xxname") //无sql注入的风险
                .list();
    }

    @Test
    public void testLast() {
        // WHERE age IS NOT NULL limit 2
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .isNotNull(UserInfo::getAge)
                .last("limit 2")
                .list();
    }

    //exists、notExists
    @Test
    public void testExists() {
        // SELECT id,area_name FROM area WHERE
        //         (EXISTS (select * from user where user.area_id = area.id))
        List<Area> areas = new LambdaQueryChainWrapper<>(areaMapper)
                .exists("select * from user where user.area_id = area.id")
                .list();
    }


    @Test
    public void testNotExists() {
        // SELECT id,area_name FROM area WHERE
        //         (NOT EXISTS (select * from user where user.area_id = area.id))
        List<Area> areas = new LambdaQueryChainWrapper<>(areaMapper)
                .notExists("select * from user where user.area_id = area.id")
                .list();
    }

    // select
    @Test
    public void testSelect() {
        List<UserInfo> userInfos = new LambdaQueryChainWrapper<>(userInfoMapper)
                .select(UserInfo::getName, UserInfo::getAge)
                .list();
    }

}
