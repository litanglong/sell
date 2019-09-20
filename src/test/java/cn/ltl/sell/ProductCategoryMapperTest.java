package cn.ltl.sell;

import cn.ltl.sell.dataobject.ProductCategory;
import cn.ltl.sell.mapper.ProductCategoryMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductCategoryMapperTest {

    @Resource
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private MailSender mailSender;

    @Test
    public void test1() {
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(2);
        Assert.assertNotNull(productCategory);
    }

    @Test
    public void test2() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件发送人
        simpleMailMessage.setFrom("605325132@qq.com");
        //邮件接收人
        simpleMailMessage.setTo("605325132@qq.com");
        //邮件主题
        simpleMailMessage.setSubject("这是一封测试的邮件");
        //邮件内容
        simpleMailMessage.setText("Hello1");
        mailSender.send(simpleMailMessage);
    }

}