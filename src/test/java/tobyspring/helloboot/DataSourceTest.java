package tobyspring.helloboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.sql.Connection;

@JdbcTest // JDBC 사용을 위한 bean만 로드하므로, 빠르다.
public class DataSourceTest {
    @Autowired DataSource dataSource;

    @Test
    void connect() throws Exception {
        Connection connection = dataSource.getConnection();
        connection.close();
    }
}