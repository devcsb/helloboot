package tobyspring.helloboot;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

    public static void main(String[] args) {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean(HelloController.class); // 빈 등록
        applicationContext.refresh(); // 초기화


        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> {

            servletContext.addServlet("frontController", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                    //인증, 보안, 다국어처리 등 공통 기능을 처리하는 코드가 위치할 부분...

                    // 매핑 작업
                    if (request.getRequestURI().equals("/hello") && request.getMethod().equals(HttpMethod.GET.name())){
                        String name = request.getParameter("name");

                        HelloController helloController = applicationContext.getBean(HelloController.class);
                        String returnValue = helloController.hello(name);

                        response.setStatus(HttpStatus.OK.value()); // 생략해도 무방함.(서블릿에서 에러가 발생하지 않을시 200을 자동으로 내려주므로)
                        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                        response.getWriter().println(returnValue);
                    }
                    else if (request.getRequestURI().equals("/user")) {
                        //
                    }
                    else {
                        response.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                }
            }).addMapping("/*");

        });
        webServer.start();
    }
}