package br.com.alura.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
public class JPAConfiguration {
	

	private EmbeddedDatabase ed;


    @Bean(name="hsqlInMemory")
	public EmbeddedDatabase hsqlInMemory() {

        if ( this.ed == null ) {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            this.ed = builder.setType(EmbeddedDatabaseType.HSQL).build();
        }

        return this.ed;        
	}
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
	    LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
	    HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();


		lcemfb.setJpaVendorAdapter(va);
		
        lcemfb.setDataSource(this.hsqlInMemory());

		Properties ps = new Properties();
		ps.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		ps.put("hibernate.hbm2ddl.auto", "create");
		ps.put("hibernate.show_sql", "true");
		ps.put("hibernate.connection.username", "sa");
		ps.put("hibernate.connection.password", "");
		lcemfb.setJpaProperties(ps);


		lcemfb.setPackagesToScan("br.com.alura.loja.models");

        return lcemfb;
    }

    
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf){
        return new JpaTransactionManager(emf);
    }
    
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
