package dev.ershov.vd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"dev.ershov.vd"})
@EnableTransactionManagement
public class JpaConfig {

}
