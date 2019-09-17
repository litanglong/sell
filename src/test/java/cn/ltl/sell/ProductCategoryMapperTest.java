package cn.ltl.sell;

import cn.ltl.sell.dataobject.ProductCategory;
import cn.ltl.sell.mapper.ProductCategoryMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductCategoryMapperTest {

    @Resource
    private ProductCategoryMapper productCategoryMapper;

    @Test
    public void test1() {
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(2);
        Assert.assertNotNull(productCategory);
    }

}