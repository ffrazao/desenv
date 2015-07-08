package br.gov.df.emater.entidade;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
@EntityScan(basePackages = "br.gov.df.emater.entidade.teste")
public class _EntidadeConfig {
	
	@Bean
	public DataSource dataSource(Environment env) {
		MysqlDataSource dataSource = new MysqlDataSource(); 
		dataSource.setUrl(env.getProperty("jdbc.url")); 
		dataSource.setUser(env.getProperty("jdbc.username")); 
		dataSource.setPassword(env.getProperty("jdbc.password")); 
		return dataSource;
	}
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource(env));
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return entityManagerFactoryBean;
    }

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

}