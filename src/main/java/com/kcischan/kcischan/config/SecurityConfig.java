package com.kcischan.kcischan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.servlet.config.annotation.*;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import java.util.Properties;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
  // Captcha
  @Bean
  public DefaultKaptcha captchaProducer() {
    DefaultKaptcha kaptcha = new DefaultKaptcha();
    Properties props = new Properties();
    props.setProperty("kaptcha.textproducer.char.length", "5");
    props.setProperty("kaptcha.image.width", "160");
    props.setProperty("kaptcha.image.height", "60");
    props.setProperty("kaptcha.textproducer.font.size", "40");
    kaptcha.setConfig(new Config(props));
    return kaptcha;
  }

  // Hash
  @Bean
  public Argon2PasswordEncoder passwordEncoder() {
    return new Argon2PasswordEncoder(16, 32, 1, 1 << 16, 3);
  }
}
