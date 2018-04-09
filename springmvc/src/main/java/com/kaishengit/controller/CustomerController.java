package com.kaishengit.controller;

import com.kaishengit.entity.Customer;
import com.kaishengit.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping
    public String list(Model model,
                       @RequestParam(name = "p",defaultValue = "1") Integer pageNo) {
        System.out.println("PageNO: " + pageNo);
        model.addAttribute("pageNo",pageNo);
        return "customer/list";
    }

    @GetMapping("/new")
    public String newCustomer() throws IOException {
        if(1==1) {
            throw new IOException();
        }
        System.out.println("customer new controller.....");
        return "customer/new";
    }


    @PostMapping("/new")
    public String newCustomer(Customer customer,String level) {

        System.out.println("from value -> name:" + customer.getName()
                + "  address:" + customer.getAddress()
                + " level:" + level);
        return "redirect:/customer/new";
    }

    @GetMapping("/delete")
    public String deleteCustomer() {
        return "customer/delete";
    }

    @GetMapping("/{id:\\d+}")
    public String viewCustomer(@PathVariable(name = "id") Integer customerId,
                               Model model) throws IOException {
        System.out.println(">>>>>>>>>>>> view customer :" + customerId);

        if(customerId.equals(1024)) {
            throw new NotFoundException();
        }

        model.addAttribute("customerId",customerId);
        return "customer/view";
    }

    @GetMapping("/{typeName:d-.+}/{customerId:\\d+}")
    public ModelAndView viewCustomerByType(@PathVariable String typeName,
                                           @PathVariable Integer customerId) {
        System.out.println(">>>>> typeName:" + typeName + "  customerId:" + customerId);

        //ModelAndView modelAndView = new ModelAndView();
        //设定跳转的视图名称
        //modelAndView.setViewName("customer/view");

        ModelAndView modelAndView = new ModelAndView("customer/view");
        //设定传递的参数
        modelAndView.addObject("customerId",customerId);
        modelAndView.addObject("typeName",typeName);
        return modelAndView;
    }

    @PostMapping(value = "/save",produces = "text/plain;charset=UTF-8")
    public @ResponseBody String ajaxSave() {
        return "保存成功";
    }

    @GetMapping("/{id}.json")
    public @ResponseBody Customer viewCustomer(@PathVariable Integer id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("李晓明");
        customer.setAddress("北京");

        return customer;
    }

    @GetMapping("/all.json")
    public @ResponseBody List<Customer> showAllCustomer() {
        List<Customer> customerList = Arrays.asList(
                new Customer(1001,"lisi","北京"),
                new Customer(1002,"wangwu","上海"),
                new Customer(1006,"Hanmeili","开封")
        );
        return customerList;
    }

}
