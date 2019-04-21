package com.su.repository;

import com.su.model.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Test
    public void findByIdTest(){
        Optional<ProductCategory> optional = categoryRepository.findById(1);

        optional.ifPresent(System.out::println);
    }

    @Test
    public void saveTest(){
        ProductCategory category = new ProductCategory();
        category.setCategoryName("Java基础课程");
        category.setCategoryType(2);

        ProductCategory result = categoryRepository.save(category);
        System.out.println(result);
    }

    @Test
    public void updateTest(){
        ProductCategory productCategory = categoryRepository.findById(2).get();
        productCategory.setCategoryName("课本-书籍");

        ProductCategory result = categoryRepository.save(productCategory);
        System.out.println(result);
    }

    @Test
    public void findByCategoryTypeInTest(){
        Set<Integer> categoryTypeList = new HashSet<>();
        categoryTypeList.add(1);
        categoryTypeList.add(2);
        List<ProductCategory> result = categoryRepository.findByCategoryTypeIn(categoryTypeList);
        result.forEach(System.out::println);
    }
}