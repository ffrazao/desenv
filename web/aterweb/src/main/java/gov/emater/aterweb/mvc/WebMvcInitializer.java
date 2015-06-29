package gov.emater.aterweb.mvc;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

public class WebMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer {

	public WebMvcInitializer() {
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	@Override
	protected Filter[] getServletFilters() {		
		// cuidado com o filtro a seguir, pode causar problemas com o spring
		// security
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		characterEncodingFilter.setBeanName("CharacterEncodingFilter");

		return new Filter[] { characterEncodingFilter };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// habilitar o log4j
		servletContext.setInitParameter("log4jConfigLocation", "classpath:META-INF/log4j.xml");
		servletContext.addListener(Log4jConfigListener.class);

		// habilitar cors
		servletContext.addFilter("AtivaCORSSimplesFiltro", AtivaCORSSimplesFiltro.class).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");

		// habilitar o Spring Security
		servletContext.addFilter(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, DelegatingFilterProxy.class).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");

		super.onStartup(servletContext);
	}

}