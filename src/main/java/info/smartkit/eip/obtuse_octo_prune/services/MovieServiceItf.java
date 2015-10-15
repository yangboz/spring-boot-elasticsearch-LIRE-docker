package info.smartkit.eip.obtuse_octo_prune.services;

import info.smartkit.eip.obtuse_octo_prune.domains.Movie;

import java.util.List;

/**
 * Created by yangboz on 10/15/15.
 */
public interface MovieServiceItf {
    public List<Movie> getByName(String name);

    public List<Movie> getByRatingInterval(Double beginning, Double end);

    public void addMovie(Movie movie);
}
