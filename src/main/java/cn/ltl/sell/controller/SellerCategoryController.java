package cn.ltl.sell.controller;

import cn.ltl.sell.dataobject.ProductCategory;
import cn.ltl.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer size) {
        Page<ProductCategory> productCategoryPage = categoryService.findAll(new PageRequest(page-1, size));
        model.addAttribute("productCategoryPage", productCategoryPage);
        model.addAttribute("currentPage", page);
        return "category/list";
    }

    @GetMapping("{categoryId}")
    public String detail(Model model, @PathVariable Integer categoryId) {
        ProductCategory productCategory = categoryService.findOne(categoryId);
        if (productCategory == null) {
            productCategory = new ProductCategory();
        }
        model.addAttribute("productCategory", productCategory);
        return "category/form";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid ProductCategory productCategory, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("msg", bindingResult.getFieldError().getDefaultMessage());
            return "redirect:error";
        }
        categoryService.save(productCategory);
        return "redirect:list";
    }

    @GetMapping("/delete")
    public String delete(Integer categoryId) {
        categoryService.delete(categoryId);
        return "redirect:list";
    }
}
