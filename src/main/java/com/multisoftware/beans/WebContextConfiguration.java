package com.multisoftware.beans;

import org.apache.tomcat.JarScanner;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebContextConfiguration {

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return context -> context.setAttribute(
                JarScanner.class.getName(),
                new StandardJarScanner() {{
                    setScanManifest(false);
                }}
        );
    }
}