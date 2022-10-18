package com.example.shoppingcartservice.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NewBeeMallShoppingCartMapper {
    int deleteByPrimaryKey(@Param("cartId") Long cartId);
}
