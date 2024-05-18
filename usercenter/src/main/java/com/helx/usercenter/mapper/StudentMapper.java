package com.helx.usercenter.mapper;

import com.helx.usercenter.model.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper{

    int insert(Student student);
}
