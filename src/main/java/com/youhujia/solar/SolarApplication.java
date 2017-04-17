package com.youhujia.solar;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.youhujia.halo","com.youhujia.solar"})
@SpringBootApplication(scanBasePackages = {"com.youhujia.halo","com.youhujia.solar"})
public class SolarApplication {

    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(SolarApplication.class).web(true).run(args);
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }
}
