package ptit.ltw.Utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtils {
    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    static {
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
    }

    //Utility method to return SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
