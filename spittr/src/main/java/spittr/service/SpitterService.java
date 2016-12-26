package spittr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import spittr.data.SpitterRepository;
import spittr.domain.Spitter;

/**
 * Created by 273cn on 16/12/21.
 */
@Service
public class SpitterService {
    @Autowired
    SpitterRepository spitterRepository;

    public Spitter getSpitter(String username) {
        return spitterRepository.findByUsernameIgnoringCase(username);
    }

    public Spitter saveSpitter(Spitter spitter) {
        spitter.setCreatedTime(System.currentTimeMillis() / 1000);
        return spitterRepository.save(spitter);
    }

    public Page<Spitter> getSpitters(int page, int size) {
        return spitterRepository.findAll(new PageRequest(page, size));
    }
}
