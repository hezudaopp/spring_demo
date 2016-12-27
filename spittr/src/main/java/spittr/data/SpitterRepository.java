package spittr.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spittr.domain.Spitter;

import java.util.List;

/**
 * Created by 273cn on 16/12/14.
 */
@Repository
public interface SpitterRepository extends JpaRepository<Spitter, Long>, SpitterDao {
    @Query("SELECT s FROM Spitter s WHERE s.mobileNo LIKE '130%'")
    List<Spitter> findAllShortUsernameSpitters();

    Spitter findByUsernameIgnoringCase(String username);

    Spitter findByMobileNo(String mobileNo);
}
