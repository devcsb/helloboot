package tobyspring.helloboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // property에 정의된 포트로 톰캣을 띄워서 테스트하게끔 설정
class HelloApiTest {

    @Test
    void hello_api_호출시_200성공을_반환한다() throws Exception {
        TestRestTemplate rest = new TestRestTemplate();

        ResponseEntity<String> response = rest.getForEntity("http://localhost:9090/app/hello?name={name}", String.class, "spring");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE))
                .startsWith(MediaType.TEXT_PLAIN_VALUE);
        assertThat(response.getBody()).isEqualTo("*Hello spring*");
    }

    @Test
    void hello_api_호출시_500에러를_반환한다() throws Exception {
        TestRestTemplate rest = new TestRestTemplate();

        ResponseEntity<String> response = rest.getForEntity("http://localhost:9090/app/hello?name=", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}