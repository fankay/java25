package com.kaishengit.tms.controller;


import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kaishengit.tms.entity.TicketOutRecord;
import com.kaishengit.tms.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 财务管理控制器
 * @author fankay
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    private TicketService ticketService;

    /**
     * 售票点缴费首页
     * @return
     */
    @GetMapping("/ticket")
    public String ticketFinanceHome(@RequestParam(required = false,defaultValue = "未支付") String state,
                                    @RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo,
                                    Model model) {
        Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("state",state);

        PageInfo<TicketOutRecord> pageInfo = ticketService.findTicketOutRecordByPageNoAndQueryParam(pageNo,queryParam);
        model.addAttribute("page",pageInfo);
        return "finance/ticket/home";
    }

    /**
     * 支付下发单
     * @param id
     * @return
     */
    @GetMapping("/ticket/{id:\\d+}/pay")
    public String payTicketFinance(@PathVariable Integer id,Model model) {
        TicketOutRecord ticketOutRecord = ticketService.findTicketOutRecordById(id);
        model.addAttribute("record",ticketOutRecord);
        //ticketService.payTicketOutRecord(id);
        return "finance/ticket/pay";
    }

    @PostMapping("/ticket/{id:\\d+}/pay")
    public String payTicketFinance( Integer id,String payType) {
        ticketService.payTicketOutRecord(id,payType);
        return "redirect:/finance/ticket";
    }
}
