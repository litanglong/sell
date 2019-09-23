package cn.ltl.sell.service.impl;

import cn.ltl.sell.DTO.CartDTO;
import cn.ltl.sell.dataobject.ProductInfo;
import cn.ltl.sell.enums.ProductStatusEnum;
import cn.ltl.sell.enums.ResultEnum;
import cn.ltl.sell.exception.SellException;
import cn.ltl.sell.repository.ProductInfoRepository;
import cn.ltl.sell.service.ProductService;
import cn.ltl.sell.util.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;
    @Autowired
    private RedisLock redisLock;

    @Override
    @Cacheable(value = "product", key = "2")
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
    @CachePut(value = "product", key = "2")
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
        //分布式锁
        long timeMillis = System.currentTimeMillis() + 10 * 1000;
        if (!redisLock.lock("order", String.valueOf(timeMillis))) {
            throw new RuntimeException("下单失败");
        }
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
        //解锁
        redisLock.unlock("order", String.valueOf(timeMillis));
    }

    @Override
    @Transactional
    public void onSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if(productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_FOUND);
        }
        if(!ProductStatusEnum.DOWN.getCode().equals(productInfo.getProductStatus())) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        repository.save(productInfo);
    }

    @Override
    @Transactional
    public void offShelves(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if(productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_FOUND);
        }
        if(!ProductStatusEnum.UP.getCode().equals(productInfo.getProductStatus())) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        repository.save(productInfo);
    }
}
