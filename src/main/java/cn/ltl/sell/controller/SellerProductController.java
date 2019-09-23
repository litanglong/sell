package cn.ltl.sell.controller;

import cn.ltl.sell.VO.ResultVO;
import cn.ltl.sell.dataobject.ProductCategory;
import cn.ltl.sell.dataobject.ProductInfo;
import cn.ltl.sell.service.CategoryService;
import cn.ltl.sell.service.ProductService;
import cn.ltl.sell.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer size) {
        Page<ProductInfo> productInfoPage = productService.findAll(new PageRequest(page-1, size));
        model.addAttribute("productInfoPage", productInfoPage);
        model.addAttribute("currentPage", page);
        return "product/list";
    }

    @GetMapping("{productId}")
    public String detail(Model model, @PathVariable String productId) {
        ProductInfo productInfo = productService.findOne(productId);
        List<ProductCategory> categoryList = categoryService.findAll();
        model.addAttribute("productInfo", productInfo);
        model.addAttribute("categoryList", categoryList);
        return "product/form";
    }

    @PostMapping("/save")
    @CacheEvict(cacheNames = "product", key = "1")
    public String save(Model model, @Valid ProductInfo productInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("msg", bindingResult.getFieldError().getDefaultMessage());
            return "redirect:error";
        }
        productService.save(productInfo);
        return "redirect:list";
    }

    @PostMapping("/onSale")
    @ResponseBody
    public ResultVO onSale(String productId) {
        productService.onSale(productId);
        return ResultVOUtil.success();
    }

    @PostMapping("/offShelves")
    @ResponseBody
    public ResultVO offShelves(String productId) {
        productService.offShelves(productId);
        return ResultVOUtil.success();
    }
}
