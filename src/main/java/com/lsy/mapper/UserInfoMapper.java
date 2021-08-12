package com.lsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsy.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Wrapper;
import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {


    // 自定义数据库操作
/*    @Select("select * from user WHERE age > #{age}")
      List<UserInfo> getAll(Integer age);*/

//    List<UserInfo> getAll(Integer age);



    // 自定义wrapper
//    @Select("SELECT * FROM user_info ${ew.customSqlSegment}")
//    List<UserInfo> getAll(@Param(Constants.WRAPPER) Wrapper wrapper);

//    List<UserInfo> getAll(@Param(Constants.WRAPPER) Wrapper wrapper);

    // 自定义分页
    IPage<UserInfo> selectPage2(Page<?> page, Integer age);

    @Select("SELECT user.*, area.area_name FROM user, area " +
            "WHERE user.area_id = area.id and user.id = #{id}")
    UserInfo getUserById(int id);
}
