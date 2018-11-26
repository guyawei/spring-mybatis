package com.boot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages={"com.boot.test.client"})
@ComponentScan(value = { "com.boot.test", "springfox.documentation" })
public class Application
{
    public static void main( String[] args )
    {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.run(args);
    }
}
