package tobyspring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // default 는 RetentionPolicy.CLASS
@Target(ElementType.TYPE) // TYPE : Class, interface (including annotation type), or enum declaration
@Configuration
@ComponentScan // 해당 패키지와 하위 패키지에 있는 모든 클래스들 중에서 @Component 어노테이션이 붙은 클래스들을 스캔하여 스프링 컨텍스트에 빈으로 등록한다
@EnableMyAutoConfiguration
public @interface MySpringBootApplication {
}
