package cn.ltl.sell.service.impl;

import cn.ltl.sell.DTO.CartDTO;
import cn.ltl.sell.dataobject.ProductInfo;
import cn.ltl.sell.enums.ProductStatusEnum;
import cn.ltl.sell.enums.ResultEnum;
import cn.ltl.sell.exception.SellException;
import cn.ltl.sell.repository.ProductInfoRepository;
import cn.ltl.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTO) {
        for (CartDTO cart : cartDTO) {
            ProductInfo productInfo = repository.findOne(cart.getProductId());
            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_FOUND);
            }
            Integer result = productInfo.getProductStock() + cart.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTO) {
        for (CartDTO cart : cartDTO) {
            ProductInfo productInfo = repository.findOne(cart.getProductId());
            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_FOUND);
            }
            Integer result = productInfo.getProductStock() - cart.getProductQuantity();
            if(result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }
}
