package com.mpf.sell.dataobject.mapper;

import com.mpf.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface ProductCategoryMapper {
    @Insert("insert into product_category (category_name,category_type) values(#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertIntoCategoryType(Map<String,Object> map);

    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_type",property = "categoryType")}
    )
    ProductCategory findByCategoryType(Integer categoryType);
}
