package com.kcischan.kcischan.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class IpLimitService {
  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(IpLimitService.class);
  private static final long TIME_THRESHOLD = 5 * 60 * 1000; // millis
  private static final int MAX_WARNINGS = 3;

  private final Map<String, Long> lastPostTime = new ConcurrentHashMap<>();
  private final Map<String, Integer> warningCounts = new ConcurrentHashMap<>();

  public void handlePost(String ip) {
    logger.info("Handling post from IP: {}", ip);
    long now = System.currentTimeMillis();
    Long lastTime = lastPostTime.get(ip);

    if (lastTime != null && (now - lastTime) <= TIME_THRESHOLD) {
      // posted again under 5 minutes
      logger.info("IP {} posted again under 5 minutes, accumulated warning: {}", lastTime,
          warningCounts.getOrDefault(ip, 0));
      warningCounts.put(ip, warningCounts.getOrDefault(ip, 0) + 1);
    } else {
      warningCounts.remove(ip);
    }

    lastPostTime.put(ip, now);
  }

  public boolean isLimited(String ip) {
    return warningCounts.getOrDefault(ip, 0) - 1 >= MAX_WARNINGS;
  }

  public void clearIp(String ip) {
    lastPostTime.remove(ip);
    warningCounts.remove(ip);
  }
}
