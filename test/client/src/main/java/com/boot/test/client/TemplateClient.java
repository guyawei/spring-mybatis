package com.boot.test.client;

import com.boot.test.fallbak.TemplateHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by sunyulei on 2018/5/9.
 */

@FeignClient(value="test",fallback = TemplateHystrix.class)
public interface TemplateClient {

    @PostMapping("/test/getTestResult")
    String getTestResult();
}
