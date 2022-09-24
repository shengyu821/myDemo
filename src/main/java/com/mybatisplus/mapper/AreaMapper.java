package com.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatisplus.entity.Area;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AreaMapper extends BaseMapper<Area> {

    @Result(column = "id", property = "users", many = @Many(select = "com.lsy.mapper.UserMapper.selectByAreaId"))
    @Select("SELECT * FROM area WHERE id = #{id}")
    Area getAreaById(int id);

    Area getAreaByIdXML(int id);

    Area getAreaByIdXML2(int id);
}
