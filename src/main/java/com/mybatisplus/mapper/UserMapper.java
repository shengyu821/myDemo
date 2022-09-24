package com.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT user.*, area.area_name FROM user, area " +
            "WHERE user.area_id = area.id and user.id = #{id}")
    User getUserById(int id);

    @Result(column = "area_id", property = "area",
            one = @One(select = "com.lsy.mapper.AreaMapper.selectById"))
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserByIdOne(int id);


    @Select("SELECT * FROM user WHERE area_id = #{areaId}")
    User selectByAreaId(int areaId);
}
