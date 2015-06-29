package gov.emater.aterweb.security;

import gov.emater.aterweb.security.CustomAuthenticationProvider;
import gov.emater.aterweb.security.CustomUsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public SecurityConfig() {
	}

	@Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		List<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
		authenticationProviderList.add(setupCustomAuthenticationProvider());
		AuthenticationManager result = new ProviderManager(authenticationProviderList);
		return result;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// configuracao de acesso
		String[] permitAll = { "/", "/dominio/**", "/enquete-anonima/**", "/enumeracao/**", "/erro", "/login", "/logout", "/mudarSenhaAtual", "/pesquisa-textual/**", "/resources/**", "/testDB.jsp", "/tiles/enquete-resp/**", "/tiles/exibe-erro/**" };
		String[] fullyAuthenticated = { "/cadastro-geral-cad/**", "/enquete-avaliacao/**", "/enquete-identificada/**", "/tiles/cadastro-geral-cad/**" };
		String[] fullyAuthenticatedERoleAdmin = { "/modulo-cad/**", "/usuario-cad/**", "/tiles/modulo-cad/**", "/tiles/usuario-cad/**" };

		http.authorizeRequests().antMatchers(permitAll).access("permitAll");
		http.authorizeRequests().antMatchers(fullyAuthenticated).access("fullyAuthenticated");
		http.authorizeRequests().antMatchers(fullyAuthenticatedERoleAdmin).access("fullyAuthenticated and hasRole('ROLE_ADMIN')");
//		// para qualquer outra coisa nao identificada anteriormente
		http.authorizeRequests().anyRequest().access("fullyAuthenticated and hasRole('ROLE_USER')");

		//http.authorizeRequests().anyRequest().access("permitAll");

		http.formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?login_error=true");
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID");

		http.exceptionHandling().accessDeniedPage("/acessoNegado");

		http.addFilterBefore(setupCustomUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		http.sessionManagement().maximumSessions(1);
		
		// TODO por hora é necessário desabilitar, futuramente reabilitar
		http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	}

	@Bean(name = "customAuthenticationProvider")
	public CustomAuthenticationProvider setupCustomAuthenticationProvider() {
		// Customização da autenticação do usuário
		CustomAuthenticationProvider result = new CustomAuthenticationProvider();
		return result;
	}

	@Bean(name = "customUsernamePasswordAuthenticationFilter")
	public CustomUsernamePasswordAuthenticationFilter setupCustomUsernamePasswordAuthenticationFilter() throws Exception {
		// Necessario para habilitar a captação do campo administrador da tela
		// de login
		CustomUsernamePasswordAuthenticationFilter result = new CustomUsernamePasswordAuthenticationFilter();
		result.setAuthenticationManager(authenticationManagerBean());
		result.setAuthenticationSuccessHandler(setupSuccessHandler());
		result.setAuthenticationFailureHandler(setupFailureHandler());
		return result;
	}

	@Bean
	public DefaultWebSecurityExpressionHandler setupDefaultWebSecurityExpressionHandler() {
		// evaluating security expressions
		DefaultWebSecurityExpressionHandler result = new DefaultWebSecurityExpressionHandler();
		return result;
	}

	@Bean(name = "failureHandler")
	public SimpleUrlAuthenticationFailureHandler setupFailureHandler() {
		// Necessario para habilitar a captação do campo administrador da tela
		// de login
		SimpleUrlAuthenticationFailureHandler result = new SimpleUrlAuthenticationFailureHandler();
		result.setDefaultFailureUrl("/login?login_error=true");
		return result;
	}

	@Bean(name = "successHandler")
	public SavedRequestAwareAuthenticationSuccessHandler setupSuccessHandler() {
		// Necessario para habilitar a captação do campo administrador da tela
		// de login
		SavedRequestAwareAuthenticationSuccessHandler result = new SavedRequestAwareAuthenticationSuccessHandler();
		result.setDefaultTargetUrl("/");
		return result;
	}

}