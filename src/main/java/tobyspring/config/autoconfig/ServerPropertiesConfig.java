package tobyspring.config.autoconfig;

//@MyAutoConfiguration
public class ServerPropertiesConfig {

    // Binder 클래스를 이용하여 간편하게 바인딩 가능.
/*    @Bean
    public ServerProperties serverProperties(Environment environment) {
        return Binder.get(environment).bind("", ServerProperties.class).get();
    }*/

    // 직접 하나씩 꺼내와서 바인딩 하는 방식
/*    @Bean
    public ServerProperties serverProperties(Environment environment) {
        ServerProperties properties = new ServerProperties();
        properties.setContextPath(environment.getProperty("contextPath"));
        properties.setPort(Integer.parseInt(environment.getProperty("port")));

        return properties;
    }*/
}
