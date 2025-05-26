package com.kcischan.kcischan.api;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class CaptchaApi {
  @Autowired
  private DefaultKaptcha captchaProducer;

  @GetMapping(value = "/captcha.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
  public void getCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
    String text = captchaProducer.createText();
    BufferedImage image = captchaProducer.createImage(text);

    session.setAttribute("captcha", text);

    ImageIO.write(image, "jpg", response.getOutputStream());
  }

  // @GetMapping("/verify-captcha")
  // public ResponseEntity<String> verifyCaptcha(@RequestParam("input") String
  // input, HttpSession session) {
  // String stored = (String) session.getAttribute("captcha");
  //
  // if (stored == null || !stored.equalsIgnoreCase(input)) {
  // return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Wrong captcha");
  // }
  //
  // session.removeAttribute("captcha");
  // return ResponseEntity.ok("Verified");
  // }
}
