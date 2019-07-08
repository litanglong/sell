package cn.ltl.sell;

import cn.ltl.sell.DTO.OrderDTO;
import cn.ltl.sell.dataobject.OrderDetail;
import cn.ltl.sell.dataobject.ProductCategory;
import cn.ltl.sell.repository.ProductCategoryRepository;
import cn.ltl.sell.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;
    @Autowired
    private OrderService orderService;

    @Test
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("畅销榜");
        productCategory.setCategoryType(2);
        ProductCategory save = repository.save(productCategory);
        Assert.assertNotNull(save);
    }

    @Test
    public void listTest() {
        for (ProductCategory category : repository.findAll()) {
            System.out.println(category.getCategoryName());
        }
    }

    @Test
    public void updateTest() {
        ProductCategory category = repository.findOne(1);
        category.setCategoryName("新菜榜");
        ProductCategory productCategory = repository.save(category);
        Assert.assertNotNull(productCategory);
        listTest();
    }

    @Test
    public void createOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("地球");
        orderDTO.setBuyerName("ltl");
        orderDTO.setBuyerOpenid("605325132");
        orderDTO.setBuyerPhone("18239478967");
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("12346");
        orderDetail.setProductQuantity(5);
        orderDTO.setOrderDetailList(Arrays.asList(orderDetail));
        orderService.create(orderDTO);
    }

}