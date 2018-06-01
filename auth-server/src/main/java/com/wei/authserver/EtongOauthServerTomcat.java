package com.wei.authserver;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class EtongOauthServerTomcat extends SpringBootServletInitializer {
    /**
     * 以下用于外部 tomcat 启动
     *
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<AuthServerApplication> applicationClass = AuthServerApplication.class;
}
