package spittr.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by 273cn on 16/12/21.
 */
public class SpitterRepositoryImpl implements SpitterDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long notAnRealMethod() {
        final String sql = "SELECT COUNT(1) FROM Spitter";
        Long result = (Long) entityManager.createQuery(sql).getSingleResult();
        return result == null ? -1L : result;
    }
}
