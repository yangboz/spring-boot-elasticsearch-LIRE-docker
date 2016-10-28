package info.smartkit.eip.obtuse_octo_prune.controllers;

import com.wordnik.swagger.annotations.ApiOperation;
import info.smartkit.eip.obtuse_octo_prune.daos.MovieRepository;
import info.smartkit.eip.obtuse_octo_prune.domains.Movie;
import info.smartkit.eip.obtuse_octo_prune.services.MovieServiceItf;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

/**
 * Created by smartkit on 2016/10/28.
 */
@RestController
@RequestMapping("/es/image")
public class ImageController {

    @Autowired
    private MovieServiceItf movieServiceItf;

    @Autowired
    private MovieRepository movieRepository;

    private static Logger LOG = LogManager.getLogger(MovieController.class);

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Movie info is successfully created or not.")
//    public Map<String, Float> create(@RequestBody @Valid Map<String, Float> value) {
    public Movie create(@RequestBody Movie value) {
        //
        return movieRepository.save(value);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Response a list describing all of Movie that is successfully get or not.")
    public Iterable<Movie> list() {
        return movieRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Response a string describing if the Movie related value is successfully get or not.")
    public Movie get(@PathVariable("id") long id) {
        return movieRepository.findOne(id);
    }

    //
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(httpMethod = "PUT", value = "Response a string describing if the  Movie item  is successfully updated or not.")
    public Movie update(@PathVariable("id") long id, @RequestBody Movie value) {
//		Movie find = movieRepository.findOne(id);
        return movieRepository.save(value);
    }


    //
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(httpMethod = "DELETE", value = "Response a string describing if the Movie item is successfully delete or not.")
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
        //
        movieRepository.delete(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
