package com.kaishengit.controller;

import com.kaishengit.entity.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/new")
    public String newCustomer() {
        return "customer/new";
    }

    @PostMapping("/new")
    public String newCustomer(Customer customer,String level) {
        System.out.println("from value -> name:" + customer.getName()
                + "  address:" + customer.getAddress()
                + " level:" + level);
        //saveCustomer(customer);
        return "redirect:/customer/new";
    }

    @GetMapping("/delete")
    public String deleteCustomer() {
        return "customer/delete";
    }

    @GetMapping("/{id:\\d+}")
    public String viewCustomer(@PathVariable(name = "id") Integer customerId,
                               Model model) {
        System.out.println(">>>>>>>>>>>> view customer :" + customerId);

        model.addAttribute("customerId",customerId);
        return "customer/view";
    }

    @GetMapping("/{typeName:d-\\d+}/{customerId:\\d+}")
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

}
