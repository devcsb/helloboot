package tobyspring.helloboot;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class HelloServiceTest {
    @Test
    void simpleHelloService() {
        SimpleHelloService helloService = new SimpleHelloService(helloRepositoryStub);

        String ret = helloService.sayHello("Test");

        assertThat(ret).isEqualTo("Hello Test");
    }

    // 재사용을 위해, 변수에 담아 사용.
    private static HelloRepository helloRepositoryStub = new HelloRepository() {
            @Override
            public Hello findHello(String name) {
                return null;
            }
            @Override
            public void increaseCount(String name) {
            }
        };

    @Test
    void helloDecorator() {
        HelloDecorator decorator = new HelloDecorator(name -> name);

        String ret = decorator.sayHello("Test");
        Assertions.assertThat(ret).isEqualTo("*Test*");
    }
}
