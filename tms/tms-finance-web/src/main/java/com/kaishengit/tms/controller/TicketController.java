package com.kaishengit.tms.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.tms.entity.TicketInRecord;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.service.TicketService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

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
    public String ticketIn(Model model,
                           @RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo ) {
        PageInfo<TicketInRecord> pageInfo = ticketService.findTicketRecordByPageNo(pageNo);

        model.addAttribute("pageInfo",pageInfo);
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
    public String newTicketStorage(TicketInRecord ticketInRecord,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        try {
            ticketService.saveTicketInRecord(ticketInRecord);

            redirectAttributes.addFlashAttribute("message", "新增成功");
        } catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            ex.printStackTrace();
        }
        return "redirect:/ticket/storage";
    }

    /**
     * 删除年票入库记录
     */
    @GetMapping("/storage/{id:\\d+}/del")
    public String delInRecord(@PathVariable Integer id,RedirectAttributes redirectAttributes) {
        try {
            ticketService.delInRecordById(id);
            redirectAttributes.addFlashAttribute("message","删除成功");
        } catch (ServiceException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/ticket/storage";
    }

    /**
     * 年票统计首页
     * @return
     */
    @GetMapping("/chart")
    public String chartHome(Model model) {
        Map<String,Long> resultMap = ticketService.countTicketByState();
        model.addAttribute("resultMap",resultMap);
        return "ticket/chart/home";
    }
}
