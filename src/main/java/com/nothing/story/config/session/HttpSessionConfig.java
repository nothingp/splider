/*
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nothing.story.config.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * JDBC SESSION 配置
 * @author cai
 *
 */
@Configuration
@EnableSpringHttpSession
public class HttpSessionConfig {
    public static int SESSION_TIMEOUT = 3600;

    //    @Bean
    //    SessionRepository<ExpiringSession> sessionRepository() {
    //        SecuritySessionRepository sessionRep = new SecuritySessionRepository();
    //        sessionRep.setDefaultMaxInactiveInterval(SESSION_TIMEOUT);
    //        return sessionRep;
    //    }

    @Bean
    public MapSessionRepository sessionRepository() {
        return new MapSessionRepository();
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }
}