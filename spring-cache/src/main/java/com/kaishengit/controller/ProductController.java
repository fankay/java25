package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductType;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "1",name = "p",required = false) Integer pageNo,
                       @RequestParam(required = false) String productName,
                       @RequestParam(required = false) String place,
                       @RequestParam(required = false) Float minPrice,
                       @RequestParam(required = false) Float maxPrice,
                       @RequestParam(required = false) Integer typeId,
                       Model model) {
        //将搜索添加封装到Map集合中
        Map<String,Object> queryParamMap = new HashMap<>();
        queryParamMap.put("productName",productName);
        queryParamMap.put("place",place);
        queryParamMap.put("minPrice",minPrice);
        queryParamMap.put("maxPrice",maxPrice);
        queryParamMap.put("typeId",typeId);

        PageInfo<Product> pageInfo = productService.findAllProductByPageNoAndQueryParam(pageNo,queryParamMap);

        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("typeList",productService.findAllProductType());
        return "product/list";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        //查询所有的商品类型
        List<ProductType> productTypeList = productService.findAllProductType();

        model.addAttribute("typeList",productTypeList);
        return "product/new";
    }

    @PostMapping("/new")
    public String saveProduct(Product product,
                              RedirectAttributes redirectAttributes) {
        //将商品保存到数据库中
        productService.saveProduct(product);

        redirectAttributes.addFlashAttribute("message","添加商品成功");
        return "redirect:/product";
    }

    @GetMapping("/{id:\\d+}/del")
    public String delProduct(@PathVariable Integer id,
                             RedirectAttributes redirectAttributes) {
        productService.delProductById(id);
        redirectAttributes.addFlashAttribute("message","商品删除成功");
        return "redirect:/product";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editProduct(@PathVariable Integer id,
                              Model model) {
        //根据ID查询商品
        Product product = productService.findById(id);
        if(product == null) {
            throw new NotFoundException();
        }
        //查询商品分类列表
        List<ProductType> productTypeList = productService.findAllProductType();

        model.addAttribute("typeList",productTypeList);
        model.addAttribute("product",product);
        return "product/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String editProduct(Product product,RedirectAttributes redirectAttributes) {
        productService.updateProduct(product);

        redirectAttributes.addFlashAttribute("message","商品修改成功");
        return "redirect:/product";
    }

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
