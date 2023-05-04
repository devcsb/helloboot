package tobyspring.study;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ConditionalTest {
    @Test
    void conditional () throws Exception {

        //true
        ApplicationContextRunner contextRunner = new ApplicationContextRunner();// Test 전용 ApplicationContextRunner
        contextRunner.withUserConfiguration(Config1.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(MyBean.class);
                    assertThat(context).hasSingleBean(Config1.class);
                });

        //false
        new ApplicationContextRunner().withUserConfiguration(Config2.class)
                .run(context -> {
                    assertThat(context).doesNotHaveBean(MyBean.class);
                    assertThat(context).doesNotHaveBean(Config1.class);
                });
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanCondition.class)
    @interface BooleanConditional {
        boolean value(); // 어노테이션의 속성이 하나뿐일 때, 어노테이션 선언시, 필드명 명시 없이 사용 가능.
    }

    @Configuration
    @BooleanConditional(true)
    static class Config1 { // 중첩클래스이긴 하지만, 바깥쪽 클래스와 관련없이 평범함 클래스처럼 사용하기 위해 static class 로 선언. 바깥 클래스는 일종의 패키지 역할을 하게 됨.
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    @Configuration
    @BooleanConditional(false)
    static class Config2 {
        @Bean
        MyBean myBean () {
            return new MyBean();
        }
    }

    static class MyBean {}

     static class BooleanCondition implements Condition {
        // 어노테이션의 속성값에 따라서 컨디션을 판단하는 로직
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
            Boolean value = (Boolean)annotationAttributes.get("value");
            return value;
        }
    }

}