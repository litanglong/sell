package cn.ltl.sell.service;

import cn.ltl.sell.dataobject.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    Page<ProductCategory> findAll(Pageable pageable);

    ProductCategory save(ProductCategory productCategory);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    void delete(Integer categoryId);
}
