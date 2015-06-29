package gov.emater.aterweb.mvc;

import gov.emater.aterweb.mvc.config.JsonHibernateAwareObjectMapper;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

// informa ao spring que esta classe implementa beans
@Configuration
// para informar onde encontrar controllers e restcontrollers
@ComponentScan(basePackages = { "gov.emater.aterweb" })
// mesmo que <tx:annotation-driven />
@EnableTransactionManagement
public class WebMvcConfig extends WebMvcConfigurationSupport {

	// mvc:annotation-driven pode ser anotar a classe com @EnableWebMvc e
	// extender de WebMvcConfigurerAdapter que é o formato mais simples ou mais
	// direto extendendo diretamente de WebMvcConfigurationSupport

	public WebMvcConfig() {
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// registry.addResourceHandler("/resources/**").addResourceLocations("/resources/",
		// "/html/").setCachePeriod(31556926);
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/").setCachePeriod(31556926);
		registry.addResourceHandler("/tiles/**")
				.addResourceLocations("/tiles/").setCachePeriod(31556926);
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		// é o mesmo que <mvc:default-servlet-handler/>
		configurer.enable();
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(setupMappingJackson2HttpMessageConverter());
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver setupCommonsMultipartResolver() {
		// Manipulação de Arquivos
		CommonsMultipartResolver result = new CommonsMultipartResolver();
		return result;
	}

	@Bean(name = "dataSource")
	public DataSource setupDataSource() {
		JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		DataSource dataSource = dsLookup.getDataSource("jdbc/ematerDS");
		return dataSource;
	}

	@Bean(name = "jsonMessageConverter")
	public MappingJackson2HttpMessageConverter setupMappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter result = new MappingJackson2HttpMessageConverter();
		result.setObjectMapper(new JsonHibernateAwareObjectMapper());
		return result;
	}

	@Bean
	public RequestMappingHandlerAdapter setupRequestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter result = new RequestMappingHandlerAdapter();
		result.getMessageConverters().add(
				setupMappingJackson2HttpMessageConverter());
		return result;
	}

	@Bean(name = "sessionFactory")
	public SessionFactory setupSessionFactory() {
		// Hibernate
		LocalSessionFactoryBean result = new LocalSessionFactoryBean();
		result.setDataSource(setupDataSource());
		result.setConfigLocation(new ClassPathResource(
				"/META-INF/hibernate.cfg.xml"));
		try {
			result.afterPropertiesSet();
		} catch (IOException e) {
			new RuntimeException(e);
		}
		return result.getObject();
	}

	@Bean(name = "tilesConfigurer")
	public TilesConfigurer setupTilesConfigurer() {
		TilesConfigurer result = new TilesConfigurer();
		result.setPreparerFactoryClass(SpringBeanPreparerFactory.class);
		return result;
	}

	@Bean(name = "transactionManager")
	public HibernateTransactionManager setupTransactionManager() {
		HibernateTransactionManager result = new HibernateTransactionManager();
		result.setSessionFactory((SessionFactory) setupSessionFactory());
		return result;
	}

	@Bean(name = "viewResolver")
	public UrlBasedViewResolver setupViewResolver() {
		TilesViewResolver result = new TilesViewResolver();
		result.setViewClass(TilesView.class);
		return result;
	}

}