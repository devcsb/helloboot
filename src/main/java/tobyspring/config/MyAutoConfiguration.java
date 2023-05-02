package tobyspring.config;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration(proxyBeanMethods = false) // @Bean에 의한 수동 빈 등록을 할 때 싱글톤을 보장하기 위한 프록시 사용 설정 (default = true)
public @interface MyAutoConfiguration {

}
