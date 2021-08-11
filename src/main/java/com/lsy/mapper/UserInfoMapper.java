package com.lsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsy.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

//    List<UserInfo> getAll(Integer age);

/*    @Select("select * from user WHERE age > #{age}")
    List<UserInfo> getAll(Integer age);*/

}
