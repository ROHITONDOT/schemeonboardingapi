package com.schemeonboardingapi.schemeonboardingapi.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
@Configuration
public class Bmscoredb {
	

	
	@Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;
	
	
	@Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.bmscore.datasource")
    public DataSource dataSource1() {
       // return DataSourceBuilder.create().build();
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUsername);
        dataSource.setPassword(dataSourcePassword);
        return dataSource;
    }
	
	@Primary
    @Bean
    public JdbcTemplate jdbcTemplatebmsdb(DataSource dataSource1) {
        return new JdbcTemplate(dataSource1);
    }
}
