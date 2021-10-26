package ptit.ltw.Repositoty.RepositoryImpl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.Subscriber;
import ptit.ltw.Repositoty.IRepository.SubscriberRepository;

@Repository
public class SubscriberRepositoryImpl extends CrudCustomRepositoryImpl<Subscriber,Long> implements SubscriberRepository {
    public SubscriberRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Subscriber.class);
    }
    
}
