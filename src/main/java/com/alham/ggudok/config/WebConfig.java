package com.alham.ggudok.config;

import com.alham.ggudok.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "error","/image/**");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //해당 경로로 접근시
        registry.addResourceHandler("/images/subs/main/**")
                //해당 경로의 파일을 읽는다.
                .addResourceLocations("file:///C:/ggudown/subs/main/")
                // 접근 파일 캐싱 시간
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));

        registry.addResourceHandler("/images/subs/icon/**")
                //해당 경로의 파일을 읽는다.
                .addResourceLocations("file:///C:/ggudown/subs/icon/")
                // 접근 파일 캐싱 시간
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));

        registry.addResourceHandler("/images/category/**")
                //해당 경로의 파일을 읽는다.
                .addResourceLocations("file:///C:/ggudown/category/")
                // 접근 파일 캐싱 시간
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));


        registry.addResourceHandler("/images/member/**")
                //해당 경로의 파일을 읽는다.
                .addResourceLocations("file:///C:/ggudown/member/")
                // 접근 파일 캐싱 시간
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));

        registry.addResourceHandler("/images/event/**")
                //해당 경로의 파일을 읽는다.
                .addResourceLocations("file:///C:/ggudown/event/")
                // 접근 파일 캐싱 시간
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("forward:/index.html");
        registry.addViewController("/Category/**")
                .setViewName("forward:/index.html");
    }
}
