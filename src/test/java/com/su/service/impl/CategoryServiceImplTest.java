package com.su.service.impl;

import com.su.model.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findById() {
        ProductCategory category = categoryService.findById(1);
        System.out.println(category);
    }

    @Test
    public void findAll() {
        List<ProductCategory> categoryList = categoryService.findAll();
        categoryList.forEach(System.out::println);
    }

    @Test
    public void findByCategoryTypeIn() {
        Set<Integer> categoryTypeSet = new HashSet<>();
        categoryTypeSet.add(1);
        categoryTypeSet.add(2);
        categoryTypeSet.add(3);
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeSet);
        categoryList.forEach(System.out::println);
    }

    @Test
    public void save() {
        ProductCategory category = new ProductCategory("家用电器", 3);
        ProductCategory result = categoryService.save(category);
        System.out.println(result);
    }
}