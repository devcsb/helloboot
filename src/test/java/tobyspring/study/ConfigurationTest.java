package tobyspring.study;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationTest {
    @Test
    void 스프링_DI_컨테이너를_사용하는_경우() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.refresh();

        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);

        assertThat(bean1.common).isSameAs(bean2.common); // true.
    }

    @Test
    void 프록시를_사용하지_않는_경우엔_true가_반환된다() {
        MyConfig myConfig = new MyConfig();

        Bean1 bean1 = myConfig.bean1();
        Bean2 bean2 = myConfig.bean2();
        assertThat(bean1.common).isSameAs(bean2.common); // true
    }

    @Test
    void 프록시를_사용하여_싱글톤을_보장하는_경우엔_true가_반환된다() { // 프록시를 사용해 싱글톤을 보장하는 테스트
        MyconfigProxy myconfigProxy = new MyconfigProxy();

        Bean1 bean1 = myconfigProxy.bean1();
        Bean2 bean2 = myconfigProxy.bean2();
        assertThat(bean1.common).isSameAs(bean2.common); // true
    }

    static class MyconfigProxy extends MyConfig {
        private Common common;

        @Override
        Common common() {
            if (this.common == null) {
                common = super.common();
            }
            return this.common;
        }
    }

    @Configuration
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common()); // 이렇게, 또다른 빈 메서드를 호출해서 가져오는 식의 코드가 존재할 경우, proxyBeanMethods가 false로 설정했을 때, 경고를 띄워준다.
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }


    static class Bean1 {
        private final Common common;

        public Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        public Bean2(Common common) {
            this.common = common;
        }
    }

    static class Common {

    }

    // proxyBeanMethod를 false로 사용하고 있는 스프링 내부 클래스 예시 : EnableScheduling
}
