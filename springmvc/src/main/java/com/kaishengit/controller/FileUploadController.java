package com.kaishengit.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
public class FileUploadController {

    @GetMapping("/upload")
    public String uploadFile(@RequestHeader(name = "User-Agent") String userAgent,
                             HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        System.out.println("userAgent:" + userAgent);

        Cookie cookie = new Cookie("userLevel","SVIP");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setDomain("localhost");

        response.addCookie(cookie);
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@CookieValue(name = "userLevel") String userLevel, String name, MultipartFile photo, RedirectAttributes redirectAttributes) throws IOException {
        System.out.println("cookieValue: " + userLevel);
        if(!photo.isEmpty()) {
            System.out.println("表单中的元素名字：" + photo.getName());
            System.out.println("原始文件名称：" + photo.getOriginalFilename());
            System.out.println("ContentType：" + photo.getContentType());
            System.out.println("文件大小：" + photo.getSize() + " -> " + FileUtils.byteCountToDisplaySize(photo.getSize()));

            //byte[] bytes = photo.getBytes();

            /*InputStream inputStream = photo.getInputStream();
            OutputStream outputStream = new FileOutputStream("D:/temp/upload/"+photo.getOriginalFilename());
            IOUtils.copy(inputStream,outputStream);

            outputStream.flush();
            outputStream.close();
            inputStream.close();*/
            photo.transferTo(new File("X:/temp/upload/"+photo.getOriginalFilename()));


        } else {
            System.out.println("请选择文件");

            redirectAttributes.addFlashAttribute("message","请选择文件");
        }
        return "redirect:/upload";
    }

    @ExceptionHandler(IOException.class)
    public String ioExceptionHandler() {
        return "error/500";
    }

}
