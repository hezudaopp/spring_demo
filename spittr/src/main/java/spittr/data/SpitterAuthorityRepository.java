package spittr.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spittr.domain.SpitterAuthority;

import java.util.List;

/**
 * Created by 273cn on 16/12/21.
 */
@Repository
public interface SpitterAuthorityRepository extends JpaRepository<SpitterAuthority, Long> {
    List<SpitterAuthority> findByUsername(String username);
}
