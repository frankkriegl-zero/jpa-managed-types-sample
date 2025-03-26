package org.example.bookstore.config;

import external.model.Book;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypesScanner;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Configuration
// to make use of the @EntityScan annotation instead, disable all the beans in this configuration class
//@EntityScan(basePackages = { // replaced by factoryBean.setManagedTypes(managedTypes)
//        "external.model",
//        "org.example.bookstore.model"
//})
public class JpaConfig {

    /**
     * List of fully qualified class names of the shared JPA entities that should be managed in this application.
     * Replaces the need for broad @EntityScan annotation with more precise per-entity-class configuration.
     */
    List<String> sharedManagedEntityClassNames = Stream.of(
            Book.class
    ).map(Class::getName).toList();

    /**
     * List of package names that contain JPA entities to be managed in this application.
     * For shared/foreign entities, prefer specifying single entities in {@link #sharedManagedEntityClassNames} instead.
     */
    List<String> managedEntityPackages = List.of(
            "org.example.bookstore.model"
    );

    @Bean(name = "persistenceManagedTypes")
    PersistenceManagedTypes persistenceManagedTypes(ResourceLoader resourceLoader) {
        var scanResult = new PersistenceManagedTypesScanner(resourceLoader)
                .scan(managedEntityPackages.toArray(new String[0]));

        // merge the shared managed entity class names with the scanned ones
        List<String> managedClassNames = new ArrayList<>();
        managedClassNames.addAll(sharedManagedEntityClassNames);
        managedClassNames.addAll(scanResult.getManagedClassNames());

        return PersistenceManagedTypes.of(managedClassNames, Collections.emptyList());
    }

    // Idea 1: replace the EntityManagerFactory with a custom implementation

    /**
     * Manually configuring an override for the EntityManagerFactory and specifying the managed types,
     * allows more control over which entities are managed and how the EntityManagerFactory is set up.
     * This can be useful in scenarios where fine-grained control over the JPA configuration is needed.
     *
     * @param dataSource
     * @param managedTypes a {@link PersistenceManagedTypes} bean
     * @return a configured {@link EntityManagerFactory} bean with specific managed types
     */
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory(DataSource dataSource,
                                                     PersistenceManagedTypes managedTypes) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        factoryBean.setManagedTypes(managedTypes); // alternative to @EntityScan and @EnableJpaRepositories

        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    // Idea 2: modify the default EntityManagerFactory and just set managedTypes
//    @Bean(name = "modifyEntityManagerFactory")
    public String modifyEntityManagerFactory(LocalContainerEntityManagerFactoryBean factoryBean,
                                             PersistenceManagedTypes managedTypes) {


        factoryBean.setManagedTypes(managedTypes); // alternative to @EntityScan and @EnableJpaRepositories
        return """
                doesn't feel like a good idea... 
                don't know when this is invoked during spring application startup,
                don't know if factoryBean is modified afterwards, again""";
    }
}
