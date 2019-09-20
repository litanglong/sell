package cn.ltl.sell.controller;

import cn.ltl.sell.util.KeyUtil;
import cn.ltl.sell.util.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/secKill")
public class SecKillController {

    @Autowired
    private RedisLock redisLock;


    private static Map<String, Integer> product = new HashMap<>();
    private static Map<String, Object> order = new HashMap<>();

    static {
        product.put("123456", 100000);
    }

    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId) {
        return "剩余" + product.get(productId) + "件，已售" + order.size();
    }

    @GetMapping("/order/{productId}")
    public String order(@PathVariable String productId) {
        try {
            create(productId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "剩余" + product.get(productId) + "件，已售" + order.size();
    }

    @GetMapping("/init")
    public void init() {
        product.put("123456", 100000);
        order.clear();
    }


    private void create(String productId) {

        long timeMillis = System.currentTimeMillis() + 10 * 1000;

        //分布式锁
        if (!redisLock.lock(productId, String.valueOf(timeMillis))) {
            throw new RuntimeException("抢购失败");
        }
        //判断库存
        Integer stock = product.get(productId);
        if (stock.intValue() <= 0) {
            throw new RuntimeException("库存不足");
        }
        product.put("123456", stock-1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        order.put(KeyUtil.getUniqueKey(), "1");
        //解锁
        redisLock.unlock(productId, String.valueOf(timeMillis));
    }
}
