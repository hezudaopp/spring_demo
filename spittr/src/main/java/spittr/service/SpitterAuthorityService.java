package spittr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spittr.data.SpitterAuthorityRepository;
import spittr.domain.Spitter;
import spittr.domain.SpitterAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 273cn on 16/12/21.
 */
@Service
public class SpitterAuthorityService implements UserDetailsService {
    @Autowired
    SpitterAuthorityRepository spitterAuthorityRepository;

    @Autowired
    SpitterService spitterService;

    public List<SpitterAuthority> getSpitterAuthorityList(String username) {
        return spitterAuthorityRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Spitter spitter = spitterService.getSpitterByUsername(username);
        if (spitter == null)
            throw new UsernameNotFoundException("User " + username + " not found.");

        List<SpitterAuthority> spitterAuthorityList = getSpitterAuthorityList(username);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (SpitterAuthority spitterAuthority : spitterAuthorityList) {
            authorityList.add(new SimpleGrantedAuthority(spitterAuthority.getRoleName()));
        }
        spitter.setAuthorities(authorityList);
        return spitter;
    }
}
