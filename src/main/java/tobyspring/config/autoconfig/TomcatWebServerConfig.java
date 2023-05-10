package tobyspring.config.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.MyAutoConfiguration;

@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat") // 1. 클래스의 존재로 해당 기술의 사용 여부를 확인하고
public class TomcatWebServerConfig {
    @Bean("tomcatWebServerFactory")
    @ConditionalOnMissingBean // 2. 개발자가 직접 추가한 커스텀 빈 구성 중, 해당 메서드의 반환타입과 일치하는 빈이 있는지 확인하여, 일치하는 빈이 없을 때만 구성정보 적용
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }
}
/*
* @Configuration 클래스 레벨의 @ConditionalOnClass 와 @Bean 메소드 레벨의 @ConditionalOnMissingBean 조합은 가장 대표적으로 사용되는 방식.
* 클래스 레벨의 검증 없이 @Bean 메소드에만 적요하면 불필요하게 @Configuration 클래스가 빈으로 등록되기 때문.
*
* */
