package com.kaishengit.controller;

import com.kaishengit.entity.Product;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id:\\d+}")
    public String viewProduct(@PathVariable Integer id,
                              Model model) {
        Product product = productService.findById(id);
        if(product == null) {
            throw new NotFoundException();
        }
        model.addAttribute("product",product);
        return "product/product";
    }

}
