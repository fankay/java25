package com.kaishengit.tms.controller;

import com.kaishengit.tms.entity.TicketStore;
import com.kaishengit.tms.fileStore.QiniuStore;
import com.kaishengit.tms.service.TicketStoreService;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ticketstore")
public class TicketStoreController {

    @Autowired
    private TicketStoreService ticketStoreService;
    @Autowired
    private QiniuStore qiniuStore;

    @GetMapping
    public String home() {
        return "store/home";
    }

    /**
     * 新增售票点
     * @return
     */
    @GetMapping("/new")
    public String newStore(Model model) {
        String upToken = qiniuStore.getUploadToken();

        model.addAttribute("upToken",upToken);
        return "store/new";
    }

    @PostMapping("/new")
    public String newStore(TicketStore ticketStore, RedirectAttributes redirectAttributes) {
        ticketStoreService.saveNewTicktStore(ticketStore);
        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/ticketstore";
    }
}
