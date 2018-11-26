package com.boot.test.fallbak;

import com.boot.test.client.TemplateClient;
import org.springframework.stereotype.Component;

/**
 * Created by sunyulei on 2018/5/9.
 */
@Component
public class TemplateHystrix implements TemplateClient{
    @Override
    public String getTestResult() {
        return "请求失败";
    }
}
