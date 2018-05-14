package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.controller.result.ResponseBean;
import com.kaishengit.entity.Product;
import com.kaishengit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String home(@RequestParam(required = false,name = "p",defaultValue = "1") Integer pageNo,
                       Model model) {
        PageInfo<Product> pageInfo = productService.findByPageNo(pageNo);

        model.addAttribute("page",pageInfo);
        return "product/home";
    }

    @GetMapping("/new")
    public String newProduct() {
        return "product/new";
    }

    @PostMapping("/new")
    public String newProduct(Product product) {
        productService.saveNewProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}/edit")
    public String editProduct(@PathVariable Integer id,
                              Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "product/edit";
    }

    @PostMapping("/{id}/edit")
    public String editProduct(Product product) {
        productService.editProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}/del")
    public String removeProduct(@PathVariable Integer id) {
        productService.deleteById(id);
        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Integer id,
                              Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "product/show";
    }

    @GetMapping("/buy/{id}")
    @ResponseBody
    public ResponseBean buyProduct(@PathVariable Integer id) {
        try {
            productService.buyProduct(id);
        } catch (RuntimeException e) {
            return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.success();
    }

}
