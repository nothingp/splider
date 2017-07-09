package com.nothing.retrofit.autoconfigure.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("retrofit.http")
@Data
public class RetrofitConfigProperties {

    private String baseURL;

    private String user;

    private String password;
}
