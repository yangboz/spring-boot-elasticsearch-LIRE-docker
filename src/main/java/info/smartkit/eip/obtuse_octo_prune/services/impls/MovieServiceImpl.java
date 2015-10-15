package info.smartkit.eip.obtuse_octo_prune.services.impls;

import info.smartkit.eip.obtuse_octo_prune.daos.MovieRepository;
import info.smartkit.eip.obtuse_octo_prune.domains.Movie;
import info.smartkit.eip.obtuse_octo_prune.services.MovieServiceItf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangboz on 10/15/15.
 */
@Service
public class MovieServiceImpl implements MovieServiceItf {
    @Autowired
    private MovieRepository repository;

    public List<Movie> getByName(String name) {
        return repository.findByName(name);
    }

    public List<Movie> getByRatingInterval(Double beginning, Double end) {
        return repository.findByRatingBetween(beginning, end);
    }

    public void addMovie(Movie movie) {
        repository.save(movie);
    }
}
