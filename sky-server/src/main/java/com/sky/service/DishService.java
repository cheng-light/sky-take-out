package com.sky.service;

import com.sky.dto.DishDTO;


import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Param;

public interface DishService {

    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);



}