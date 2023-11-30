package com.alham.ggudok.config;

import com.alham.ggudok.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${resource.handler.subs}")
    private String resourceSubsHandler;

    @Value("${resource.handler.category}")
    private String resourceCategoryHandler;

    @Value("${resource.handler.member}")
    private String resourceMemberHandler;

    @Value("${resource.handler.event}")
    private String resourceEventHandler;

    @Value("${resource.location.subs}")
    private String resourceSubsLocation;

    @Value("${resource.location.category}")
    private String resourceCategoryLocation;

    @Value("${resource.location.member}")
    private String resourceMemberLocation;

    @Value("${resource.location.event}")
    private String resourceEventLocation;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "error","/home/ec2-user/images/**","/images/**");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //해당 경로로 접근시
        registry.addResourceHandler(resourceSubsHandler)
                //해당 경로의 파일을 읽는다.
                .addResourceLocations(resourceSubsLocation)
                // 접근 파일 캐싱 시간
                .setCacheControl(CacheControl.maxAge(3, TimeUnit.SECONDS));


        registry.addResourceHandler(resourceCategoryHandler)
                //해당 경로의 파일을 읽는다.
                .addResourceLocations(resourceCategoryLocation)
                // 접근 파일 캐싱 시간
                .setCacheControl(CacheControl.maxAge(3, TimeUnit.SECONDS));


        registry.addResourceHandler(resourceMemberHandler)
                //해당 경로의 파일을 읽는다.
                .addResourceLocations(resourceMemberLocation)
                // 접근 파일 캐싱 시간
                .setCacheControl(CacheControl.maxAge(3, TimeUnit.SECONDS));

        registry.addResourceHandler(resourceEventHandler)
                //해당 경로의 파일을 읽는다.
                .addResourceLocations(resourceEventLocation)
                // 접근 파일 캐싱 시간
                .setCacheControl(CacheControl.maxAge(3, TimeUnit.SECONDS));
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("forward:/index.html");

        registry.addViewController("/Main/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/Home/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/Category/**")
                .setViewName("forward:/index.html");

        //
        registry.addViewController("/Auth/**")
                .setViewName("forward:/index.html");


        registry.addViewController("/Event/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/Compare/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/Mypage/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/MySubscribe/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/MyReview/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/MyLike/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/Subscribe/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/SearchItemlist/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/subs/detail/item/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/Admin/**")
                .setViewName("forward:/index.html");

    }
}
