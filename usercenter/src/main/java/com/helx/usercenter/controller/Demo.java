package com.helx.usercenter.controller;

import com.helx.usercenter.mapper.OrderMapper;
import com.helx.usercenter.mapper.StudentMapper;
import com.helx.usercenter.model.Order;
import com.helx.usercenter.model.Student;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class Demo {

    @Autowired
    private TestBak test;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/testMapper")
    public Object testMapper(){
        Student student = new Student();
        student.setName("何立新");
        studentMapper.insert(student);
        return "新增";
    }

    @GetMapping("/testOrder")
    public Object testOrder(){

        for(int i = 1; i < 11; i++) {
            Order order = new Order();
            order.setOrderId(i);
            order.setPrice(new BigDecimal("1"));
            order.setUserId(12L);
            order.setStatus("正常");
            orderMapper.insert(order);
        }
        return "新增";
    }

    @GetMapping("/selectAll")
    public Object selectAll(){
       List<Order> orderList = orderMapper.selectAll();
       return orderList;
    }

    @GetMapping("/demo")
    public Object demo(){
        return test.sayHello();
    }

    @GetMapping("/testSentinel")
    public Object testSentinel(){
        System.out.println("testSentinel");
        return "testSentinel";
    }
}
