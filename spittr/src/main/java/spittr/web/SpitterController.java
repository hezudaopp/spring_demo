package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spittr.domain.Spitter;
import spittr.exceptions.ErrorResponse;
import spittr.exceptions.InvalidArgumentException;
import spittr.exceptions.ResourceConflictException;
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
        if (errors.hasErrors()) throw new InvalidArgumentException(errors);
        if (spitter.getPassword().length() > 20) {
            throw new InvalidArgumentException(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "password len greater than 20", "password"));
        }
        if (spitterService.isUsernameExists(spitter.getUsername())) throw new ResourceConflictException(Spitter.class.getSimpleName(), "username", spitter.getUsername());
        if (spitterService.isMobileNoExists(spitter.getMobileNo())) throw new ResourceConflictException(Spitter.class.getSimpleName(), "mobileNo", spitter.getMobileNo());
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
        Spitter spitter = spitterService.getSpitterByUsername(username);
        if (spitter == null) {
            throw new ResourceNotFoundException(Spitter.class.getSimpleName(), "username", username);
        }
        return new ResponseEntity<>(spitter, HttpStatus.OK);
    }
}
