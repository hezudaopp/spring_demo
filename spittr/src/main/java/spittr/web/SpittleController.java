package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spittr.domain.Spitter;
import spittr.domain.Spittle;
import spittr.exceptions.InvalidArgumentException;
import spittr.exceptions.ResourceNotFoundException;
import spittr.global.Constants;
import spittr.service.SpittleService;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by 273cn on 16/12/14.
 */
@RestController
@RequestMapping("/spittles")
public class SpittleController extends BaseController {
    @Autowired
    private SpittleService spittleService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Spittle> postSpittle(
            @AuthenticationPrincipal Spitter spitter,
            @Valid @RequestBody Spittle spittle,
            Errors errors,
            UriComponentsBuilder ucb) {
        if (errors.hasErrors()) {
            throw new InvalidArgumentException(errors);
        }
        Spittle savedSpittle = spittleService.post(spitter, spittle);
        HttpHeaders httpHeaders = new HttpHeaders();
        URI locationUri = ucb.path("/spittles/")
                .path(String.valueOf(savedSpittle.getId()))
                .build()
                .toUri();
        httpHeaders.setLocation(locationUri);
        return new ResponseEntity<>(savedSpittle, httpHeaders, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/{spittleId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Spittle> getSpittle(@PathVariable long spittleId) {
        Spittle spittle = spittleService.get(spittleId);
        if (spittle == null) {
            throw new ResourceNotFoundException(Spittle.class.getSimpleName(), String.valueOf(spittleId));
        }
        return new ResponseEntity<>(spittle, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Page<Spittle> getSpittleList(
            @RequestParam(value = "spitter_id", defaultValue = "0") long spitterId,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_SIZE) int size) {
        return spittleService.getSpittleList(spitterId, page, size);
    }
}
