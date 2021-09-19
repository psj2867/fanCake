package ml.psj2867.demo.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
public class DbConfig {

    @Bean
    public JdbcTemplate jdbcTempalte(DataSource datasource){
        return new JdbcTemplate(datasource);
    }
    
}