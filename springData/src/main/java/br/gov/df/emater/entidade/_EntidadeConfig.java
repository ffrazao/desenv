package br.gov.df.emater.entidade;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
@EntityScan(basePackages = "br.gov.df.emater.entidade.teste")
@EnableTransactionManagement
public class _EntidadeConfig {

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		MysqlDataSource e = new MysqlDataSource();
		e.setUrl(env.getProperty("jdbc.url"));
		e.setUser(env.getProperty("jdbc.username"));
		e.setPassword(env.getProperty("jdbc.password"));
		return e;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean e = new LocalContainerEntityManagerFactoryBean();
		e.setDataSource(dataSource());
		e.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return e;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager e = new JpaTransactionManager();
		e.setEntityManagerFactory(entityManagerFactory);
		return e;
	}

}