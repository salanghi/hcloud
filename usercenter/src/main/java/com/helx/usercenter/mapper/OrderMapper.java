package com.helx.usercenter.mapper;

import com.helx.usercenter.model.Order;
import com.helx.usercenter.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.aspectj.weaver.ast.Or;

import java.util.List;

@Mapper
public interface OrderMapper {

    int insert(Order order);

    List<Order> selectAll();
}
