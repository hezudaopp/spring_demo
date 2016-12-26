package spittr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spittr.data.SpittleRepository;
import spittr.domain.Spitter;
import spittr.domain.Spittle;

/**
 * Created by 273cn on 16/12/22.
 */
@Service
public class SpittleService {

    @Autowired
    SpittleRepository spittleRepository;

    @PreAuthorize("(#spitterId > 0L) or (#spitterId == 0L and hasRole('ROLE_ADMIN'))")
    public Page<Spittle> getSpittleList(long spitterId, int page, int size) {
        if (spitterId == 0L)
            return getSpittleList(page, size);
        return spittleRepository.findBySpitterId(spitterId, new PageRequest(page, size));
    }

    public Page<Spittle> getSpittleList(int page, int size) {
        return spittleRepository.findAll(new PageRequest(page, size));
    }

    public Spittle post(Spitter spitter, Spittle spittle) {
        spittle.setSpitterId(spitter.getId());
        spittle.setPostedTime(System.currentTimeMillis() / 1000);
        return spittleRepository.save(spittle);
    }

    /**
     * Get Spittle by id.
     * @param spittleId
     * @return null if no object found.
     */
    public Spittle get(Long spittleId) {
        return spittleRepository.findOne(spittleId);
    }
}
