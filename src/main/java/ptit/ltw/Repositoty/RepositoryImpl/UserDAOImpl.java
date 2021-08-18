package ptit.ltw.Repositoty.RepositoryImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ptit.ltw.Repositoty.UserDAO;

import javax.persistence.PersistenceException;

@Repository
@Transactional(rollbackFor = PersistenceException.class)
public class UserDAOImpl  {

}
