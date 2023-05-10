package tobyspring.config.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.EnableMyConfigurationProperties;
import tobyspring.config.MyAutoConfiguration;

@EnableMyConfigurationProperties(ServerProperties.class)
@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat") // 1. 클래스의 존재로 해당 기술의 사용 여부를 확인하고
public class TomcatWebServerConfig {

    @Bean("tomcatWebServerFactory")
    @ConditionalOnMissingBean // 2. 개발자가 직접 추가한 커스텀 빈 구성 중, 해당 메서드의 반환타입과 일치하는 빈이 있는지 확인하여, 일치하는 빈이 없을 때만 구성정보 적용
    public ServletWebServerFactory servletWebServerFactory(ServerProperties properties) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        factory.setContextPath(properties.getContextPath()); // 모든 서블릿 매핑 앞에 contextPath 추가
        factory.setPort(properties.getPort());

        return factory;
    }
}

/*
 * @Configuration 클래스 레벨의 @ConditionalOnClass 와 @Bean 메소드 레벨의 @ConditionalOnMissingBean 조합은 가장 대표적으로 사용되는 방식.
 * 클래스 레벨의 검증 없이 @Bean 메소드에만 적요하면 불필요하게 @Configuration 클래스가 빈으로 등록되기 때문.
 *
 * @ConditionalOnMissingBean은 현재까지 컨테이너에 등록된 빈 정보를 기준으로 체크를 하기 때문에,
 * 커스텀 빈 구성 정보에 이 어노테이션을 적용하는 것은
 * 피해야한다. (구성 정보 처리 순서가 커스텀 빈 구성 정보 -> 자동 구성 정보 이므로, 커스텀 구성 정보에 적용 시, 어떤 커스텀 구성 정보가 먼저 적용되었는지 명확히 알 수 없기 때문)
 * */