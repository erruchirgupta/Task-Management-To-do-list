package com.ruchir.toDoList.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.transaction.PlatformTransactionManager;

import com.ruchir.toDoList.filter.ResponseFilter;
//import com.ruchir.toDoList.mapper.UserDetailsMapper;
//import com.ruchir.toDoList.serviceImpl.UserDetailsServiceImpl;
import com.ruchir.toDoList.util.CustomHikariConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @author	Ruchir Gupta
 * @email	erruchirgupta@gmail.com
 * @date	11:41:18 PM
 * 
 **/


@Configuration
@EnableScheduling
@PropertySource(value = "classpath:properties/application-${spring.profiles.active?:dev}.properties")//${spring.profiles.active?:dev}
public class AppConfig implements SchedulingConfigurer  {

	@Bean(name = "executorService")
	ExecutorService taskExecutors() {
		return Executors.newScheduledThreadPool(1);
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutors());
	}

	@Bean
	@Primary
	public DataSource mySqlDataSource() {
		return new HikariDataSource(mySqlcustomHikariConfig());
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "mysql.datasource")
	public HikariConfig mySqlcustomHikariConfig() {
		return new CustomHikariConfig();
	}
	
	@Bean
	public SqlSessionFactory mySqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(mySqlDataSource());
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:/config/mybatis-config.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public PlatformTransactionManager mySqltransactionManager() {
		return new DataSourceTransactionManager(mySqlDataSource());
	}
	
		
	@Bean
	public FilterRegistrationBean myFilterBean() {
		final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
		filterRegBean.setFilter(new ResponseFilter());
		filterRegBean.addUrlPatterns("/*");
		filterRegBean.setEnabled(Boolean.TRUE);
		filterRegBean.setName("Reponse Filter");
		filterRegBean.setAsyncSupported(Boolean.TRUE);
		return filterRegBean;
	}

}