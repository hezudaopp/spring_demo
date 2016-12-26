package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spittr.domain.Spitter;
import spittr.exceptions.InvalidArgumentException;
import spittr.exceptions.ResourceNotFoundException;
import spittr.global.Constants;
import spittr.service.SpitterService;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by 273cn on 16/12/14.
 */
@RestController
@RequestMapping("/spitters")
public class SpitterController extends BaseController {
    @Autowired
    private SpitterService spitterService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Spitter> register(
            @Valid @RequestBody Spitter spitter,
            Errors errors,
            UriComponentsBuilder ucb) {
        if (errors.hasErrors()) {
            throw new InvalidArgumentException(errors);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        URI locationUri = ucb.path("/spitter/")
                .path(String.valueOf(spitter.getUsername()))
                .build()
                .toUri();
        httpHeaders.setLocation(locationUri);
        Spitter savedSpitter = spitterService.saveSpitter(spitter);
        return new ResponseEntity<>(savedSpitter, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Page<Spitter> allSpitters(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_SIZE) int size) {
        return spitterService.getSpitters(page, size);
    }

    @RequestMapping(value="/me", method=RequestMethod.GET)
    public ResponseEntity<Spitter> me(@AuthenticationPrincipal Spitter spitter) {
        return profile(spitter.getUsername());
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Spitter> profile(@PathVariable String username) {
        Spitter spitter = spitterService.getSpitter(username);
        if (spitter == null) {
            throw new ResourceNotFoundException(Spitter.class.getSimpleName(), username);
        }
        return new ResponseEntity<>(spitter, HttpStatus.OK);
    }
}
