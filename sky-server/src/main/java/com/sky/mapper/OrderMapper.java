package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    /**
     * 根据动态条件统计订单数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * 新增订单
     * @param orders
     */
    void insert(Orders orders);

    @Select("select * from orders where status=#{status} and order_time < #{time}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime time);

    /**
     *
      * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 根据id查询订单
     * @param id
     */
    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);
    /**
     * 根据状态统计订单数量
     * @param status
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * 用于替换微信支付更新数据库状态的问题
     * @param orderStatus
     * @param orderPaidStatus
     */
    @Update("update orders set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} where number = #{orderNumber}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, String orderNumber);

    /**
     * 根据number查订单
     * @param Num
     * @return
     */
    @Select("select * from orders where number=#{Num}")
    Orders getByNum(String Num);

    /**
     * 根据动态条件统计营业额
     * @param map
     * @return
     */
    Double sumByMap(Map map);
    /**
     * 统计指定时间区间内的销量排名前10
     * @param begin
     * @param end
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}
