package hello.jejulu.web.config;

import hello.jejulu.web.annotation.LoginArgumentResolver;
import hello.jejulu.web.converter.CategoryConverter;
import hello.jejulu.web.interceptor.AdminAuthInterceptor;
import hello.jejulu.web.interceptor.HostAuthInterceptor;
import hello.jejulu.web.interceptor.MemberAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final List<String> MEMBER_INTERCEPTOR_EXCLUDE_LIST = new ArrayList<>();
    private final List<String> HOST_INTERCEPTOR_EXCLUDE_LIST = new ArrayList<>();

    WebConfig(){
        MEMBER_INTERCEPTOR_EXCLUDE_LIST.add("/members");
        MEMBER_INTERCEPTOR_EXCLUDE_LIST.add("/members/sign-up");
        MEMBER_INTERCEPTOR_EXCLUDE_LIST.add("/members/id-check");
        HOST_INTERCEPTOR_EXCLUDE_LIST.add("/hosts");
        HOST_INTERCEPTOR_EXCLUDE_LIST.add("/hosts/sign-up");
        HOST_INTERCEPTOR_EXCLUDE_LIST.add("/hosts/id-check");
    }

    @Bean //HttpHiddenMethodFilter 활성화 Spring빈 등록
    public HiddenHttpMethodFilter httpMethodFilter(){
        return new HiddenHttpMethodFilter();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CategoryConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MemberAuthInterceptor())
                .addPathPatterns("/members/**")
                .excludePathPatterns(MEMBER_INTERCEPTOR_EXCLUDE_LIST);

        registry.addInterceptor(new HostAuthInterceptor())
                .addPathPatterns("/hosts/**")
                .addPathPatterns("/posts/create")
                .addPathPatterns("/posts/host/**")
                .addPathPatterns("/posts")
                .addPathPatterns("/posts/{postId}/edit")
                .excludePathPatterns(HOST_INTERCEPTOR_EXCLUDE_LIST);

        registry.addInterceptor(new AdminAuthInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }
}

