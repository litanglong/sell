package cn.ltl.sell.service;

import cn.ltl.sell.DTO.CartDTO;
import cn.ltl.sell.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    void increaseStock(List<CartDTO> cartDTO);
    void decreaseStock(List<CartDTO> cartDTO);

    void onSale(String productId);
    void offShelves(String productId);

}
