package com.Mattheo92.PatientMicroservice.retryer;

import com.Mattheo92.PatientMicroservice.decoder.RetreiveMessageErrorDecoder;
import feign.Retryer;
import feign.codec.ErrorDecoder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 1000, 4);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new RetreiveMessageErrorDecoder();
    }
}

