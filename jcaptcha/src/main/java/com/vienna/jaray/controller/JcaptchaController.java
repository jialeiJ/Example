package com.vienna.jaray.controller;

import com.octo.captcha.service.CaptchaServiceException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.vienna.jaray.service.SimpleCustomImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 验证码控制器
 */
@Controller
public class JcaptchaController {
    @GetMapping("/jumpLogin")
    public String login() {
        return "login";
    }

    @GetMapping("/jcaptcha")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        byte[] captchaChallengeAsJpeg = null;
        // 声明一个 ByteArrayOutputStream 对象，用于接收输出流
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 返回包含分配给 Session 的唯一标识符的字符串
            String captchaId = request.getSession().getId();
            // 取得单例的 ImageCaptchaService 对象，生成一个 BufferedImage 对象
            BufferedImage challenge = SimpleCustomImageService.getInstance()
                    .getImageChallengeForID(captchaId,request.getLocale());
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
            // 生成 jpg 格式的验证码图片，存储在 jpegOutputStream 对象中
            jpegEncoder.encode(challenge);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (CaptchaServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        // 生成验证码图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // 设置基本响应的格式
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        // 写入验证码图片
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

}
