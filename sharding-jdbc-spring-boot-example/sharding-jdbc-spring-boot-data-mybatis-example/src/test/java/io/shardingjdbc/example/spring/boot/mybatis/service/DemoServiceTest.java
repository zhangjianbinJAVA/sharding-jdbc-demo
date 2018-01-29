package io.shardingjdbc.example.spring.boot.mybatis.service;

import io.shardingjdbc.example.spring.boot.mybatis.entity.Order;
import io.shardingjdbc.example.spring.boot.mybatis.entity.OrderItem;
import io.shardingjdbc.example.spring.boot.mybatis.repository.OrderItemRepository;
import io.shardingjdbc.example.spring.boot.mybatis.repository.OrderRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * user: zhangjianbin <br/>
 * date: 2018/1/28 15:10
 */
public class DemoServiceTest extends DemoBaseTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    @Test
    public void create() {
        orderRepository.createIfNotExistsTable();
        orderItemRepository.createIfNotExistsTable();
        orderRepository.truncateTable();
        orderItemRepository.truncateTable();
    }

    @Test
    public void query() {
        List<OrderItem> orderItems = orderItemRepository.selectAll();
        orderItems.stream().forEach(order -> {
            System.out.println(order.toString());
        });
    }

    @Test
    public void insert() {
        List<Long> orderIds = new ArrayList<>(10);
        System.out.println("1.Insert--------------");
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            //分库字段
            int userId = 59;

            order.setUserId(userId);

            order.setStatus("INSERT_TEST");
            orderRepository.insert(order);
            long orderId = order.getOrderId(); //id 自动生成
            orderIds.add(orderId);

            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setUserId(userId);
            item.setStatus("INSERT_TEST");
            orderItemRepository.insert(item);
        }
        System.out.println(orderItemRepository.selectAll());
    }

    @Test
    public void delete() throws Exception {
        List<Long> orderIds = new ArrayList<>(10);
        System.out.println("2.Delete--------------");
        for (Long each : orderIds) {
            orderRepository.delete(each);
            orderItemRepository.delete(each);
        }
        System.out.println(orderItemRepository.selectAll());

    }

    @Test
    public void drop() {
        orderItemRepository.dropTable();
        orderRepository.dropTable();
    }

}