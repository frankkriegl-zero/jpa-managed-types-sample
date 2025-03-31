package org.example.bookstore.config;

import external.model.Book;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
// to make use of the @EntityScan annotation instead, disable all the beans in this configuration class
//@EntityScan(basePackages = { // replaced by HibernatePropertiesCustomizer bean configuration
//        "external.model",
//        "org.example.bookstore.model"
//})
public class JpaConfig {

    /**
     * List of classes of the shared JPA entities that should be managed in this application.
     * Replaces the need for broad @EntityScan annotation with more precise per-entity-class configuration.
     */
    List<Class<?>> sharedManagedEntityClasses = List.of(
            Book.class
    );

    /**
     * Manually specify managed types, by implementing a HibernatePropertiesCustomizer bean.
     * Allows customizing the Hibernate properties (in this case {@link AvailableSettings#LOADED_CLASSES}) before they are used by an autoconfigured {@link EntityManagerFactory}.
     * Allows more control over which entities are managed.
     * This can be useful in scenarios where fine-grained control over the JPA configuration is needed.
     *
     * @return a {@link HibernatePropertiesCustomizer} bean that adds custom managed entity classes as JPA managed types.
     */
    @SuppressWarnings("unchecked")
    @Bean
    public HibernatePropertiesCustomizer extraEntities() {
        return map -> {
            List<Class<?>> managedTypes = new ArrayList<>();
            managedTypes.addAll(sharedManagedEntityClasses);
            var existingLoadedClasses = map.get(AvailableSettings.LOADED_CLASSES);
            if (existingLoadedClasses != null) {
                managedTypes.addAll((Collection<Class<?>>) existingLoadedClasses);
            }
            map.put(AvailableSettings.LOADED_CLASSES, managedTypes);
        };
    }
}
