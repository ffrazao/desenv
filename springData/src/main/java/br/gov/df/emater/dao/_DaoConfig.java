package br.gov.df.emater.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "br.gov.df.emater.repositorio.teste" }, considerNestedRepositories = true)
public class _DaoConfig {

}