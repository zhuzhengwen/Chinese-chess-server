package com.chess.server.service;

import com.chess.server.entity.Subscription;
import com.chess.server.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;

    public Subscription subscribe(Long userId, String plan) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireDate;
        BigDecimal price;
        switch (plan) {
            case "MONTH":    expireDate = now.plusDays(30);   price = new BigDecimal("18.00");  break;
            case "YEAR":     expireDate = now.plusDays(365);  price = new BigDecimal("98.00");  break;
            case "LIFETIME": expireDate = now.plusYears(100); price = new BigDecimal("298.00"); break;
            default: throw new IllegalArgumentException("Unknown plan: " + plan);
        }
        Subscription sub = new Subscription();
        sub.setUserId(userId);
        sub.setPlan(plan);
        sub.setStartDate(now);
        sub.setExpireDate(expireDate);
        sub.setPrice(price);
        sub.setStatus("ACTIVE");
        subscriptionRepository.save(sub);
        userService.updateVipStatus(userId, plan, expireDate);
        return sub;
    }

    public Optional<Subscription> getLatest(Long userId) {
        return subscriptionRepository.findTopByUserIdOrderByExpireDateDesc(userId);
    }

    public List<Map<String, Object>> getPlans() {
        List<Map<String, Object>> plans = new ArrayList<>();
        Object[][] data = {
            {"MONTH",    "月度会员", "18",  "30天",  new String[]{"解锁全部棋谱", "无广告浏览", "收藏同步"}},
            {"YEAR",     "年度会员", "98",  "365天", new String[]{"解锁全部棋谱", "无广告浏览", "收藏同步", "优先客服"}},
            {"LIFETIME", "永久会员", "298", "永久",  new String[]{"解锁全部棋谱", "无广告浏览", "收藏同步", "优先客服", "专属标识"}}
        };
        for (Object[] d : data) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", d[0]);
            map.put("name", d[1]);
            map.put("price", d[2]);
            map.put("duration", d[3]);
            map.put("features", d[4]);
            plans.add(map);
        }
        return plans;
    }
}
