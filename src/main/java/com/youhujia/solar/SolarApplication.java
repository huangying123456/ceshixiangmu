package com.youhujia.solar;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {
        "com.youhujia.solar.domain",
        "com.youhujia.halo"})
@EnableFeignClients(basePackages = {
        "com.youhujia.halo"})

public class SolarApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder(SolarApplication.class).web(true).run(args);
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }
}
