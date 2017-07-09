package com.nothing.retrofit.autoconfigure;

import com.nothing.retrofit.autoconfigure.configuration.RetrofitConfigProperties;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Base64;


@Configuration
@ConditionalOnClass(Retrofit.class)
@EnableConfigurationProperties(RetrofitConfigProperties.class)
public class RetrofitAutoConfiguration {
    @Autowired
    private OkHttpClient client;

    @Autowired
    private RetrofitConfigProperties properties;

    /**
     * Configuring retrofit bean with converter factory, client and
     * base URL
     *
     * @return Retrofit bean
     */
    @Bean
    public Retrofit retrofit() {
        JacksonConverterFactory converterFactory =
                JacksonConverterFactory.create();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(properties.getBaseURL())
                .addConverterFactory(converterFactory)
                .build();
    }
}

@Qualifier
@Configuration
@ConditionalOnClass(OkHttpClient.class)
@EnableConfigurationProperties(RetrofitConfigProperties.class)
class OkHttpCallFactoryConfiguration {

    @Autowired
    private RetrofitConfigProperties properties;

    @Bean
    Call.Factory okHttpCallFactory() {
        final String auth_string =
                "Basic " + Base64.getEncoder().encodeToString((properties.getUser() + ":" + properties.getPassword()).getBytes());
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", auth_string)
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return httpClientBuilder.build();
    }
}
