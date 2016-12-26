package spittr.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spittr.domain.Spittle;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 273cn on 16/12/14.
 */
@Repository
public interface SpittleRepository extends JpaRepository<Spittle, Long> {
    Page<Spittle> findByIdLessThanEqual(long max, Pageable pageable);

    Page<Spittle> findBySpitterId(Long spitterId, Pageable pageable);
}
