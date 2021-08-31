package ptit.ltw.Utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateUtils {
    @Bean
    public SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        StandardServiceRegistry standardServiceRegistry = null;
        try {
            // Create StandardServiceRegistry
             standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            // Create MetadataSources
            MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
            // Create Metadata
            Metadata metadata = metadataSources.getMetadataBuilder().build();
            // Create SessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
            if (standardServiceRegistry != null) {
                StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
            }
        }
        return sessionFactory;
    }

}
