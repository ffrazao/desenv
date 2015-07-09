package br.gov.df.emater.entidade;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = "br.gov.df.emater.entidade.teste")
@EnableTransactionManagement
public class _EntidadeConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean e = new LocalContainerEntityManagerFactoryBean();
		e.setDataSource(dataSource);
		HibernateJpaVendorAdapter v = new HibernateJpaVendorAdapter();
		v.setGenerateDdl(false);
		e.setJpaVendorAdapter(v);
		return e;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager e = new JpaTransactionManager();
		e.setEntityManagerFactory(emf);
		return e;
	}

}