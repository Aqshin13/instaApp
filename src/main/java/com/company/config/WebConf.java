package com.company.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class WebConf implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + "./images/");
            registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
            registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
            registry.addResourceHandler("/image/**").addResourceLocations("classpath:/static/image/");

    }


    @Bean
    CommandLineRunner createStorageDirectories() {
        return (args) -> {
            createFolder("images");
            createFolder("images/uploads");
            createFolder("images/profile");

        };
    }

    private void createFolder(String path) {
        File folder = new File(path);
        boolean folderExist = folder.exists() && folder.isDirectory();
        if(!folderExist) {
            folder.mkdir();
        }
    }

}
