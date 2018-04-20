package com.kaishengit.tms.controller;

import com.kaishengit.tms.entity.TicketInRecord;
import com.kaishengit.tms.service.TicketService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    /**
     * 年票入库首页
     * @return
     */
    @GetMapping("/storage")
    public String ticketIn() {
        return "ticket/storage/home";
    }

    /**
     * 新增入库
     * @return
     */
    @GetMapping("/storage/new")
    public String newTicketStorage(Model model) {
        String today = DateTime.now().toString("YYYY-MM-dd");

        model.addAttribute("today",today);
        return "ticket/storage/new";
    }
    @PostMapping("/storage/new")
    public String newTicketStorage(TicketInRecord ticketInRecord, RedirectAttributes redirectAttributes) {
        ticketService.saveTicketInRecord(ticketInRecord);

        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/ticket/storage";
    }
}
