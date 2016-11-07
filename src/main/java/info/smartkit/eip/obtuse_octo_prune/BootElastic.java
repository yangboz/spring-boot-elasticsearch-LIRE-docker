package info.smartkit.eip.obtuse_octo_prune;

import info.smartkit.eip.obtuse_octo_prune.domains.Genre;
import info.smartkit.eip.obtuse_octo_prune.domains.Movie;
import info.smartkit.eip.obtuse_octo_prune.services.MovieServiceItf;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class BootElastic implements CommandLineRunner {

    @Autowired
    private MovieServiceItf movieService;

    private static Logger LOG = LogManager.getLogger(BootElastic.class);

    // add star wars and
// princess bride as a movie
// to elastic search
    private void addSomeMovies() {
        Movie starWars = getFirstMovie();
        movieService.addMovie(starWars);

        Movie princessBride = getSecondMovie();
        movieService.addMovie(princessBride);
    }

    private Movie getSecondMovie() {
        Movie secondMovie = new Movie();
        secondMovie.setId(2);
        secondMovie.setRating(8.4d);
        secondMovie.setName("The Princess Bride");

        List<Genre> princessPrideGenre = new ArrayList<Genre>();
        princessPrideGenre.add(new Genre("ACTION"));
        princessPrideGenre.add(new Genre("ROMANCE"));
        secondMovie.setGenre(princessPrideGenre);

        return secondMovie;
    }


    private Movie getFirstMovie() {
        Movie firstMovie = new Movie();
        firstMovie.setId(1);
        firstMovie.setRating(9.6d);
        firstMovie.setName("Star Wars");

        List<Genre> starWarsGenre = new ArrayList<Genre>();
        starWarsGenre.add(new Genre("ACTION"));
        starWarsGenre.add(new Genre("SCI_FI"));
        firstMovie.setGenre(starWarsGenre);

        return firstMovie;
    }

    public void run(String... args) throws Exception {
        addSomeMovies();
        // We indexed star wars and pricess bride to our movie
        // listing in elastic search

        //Lets query if we have a movie with Star Wars as name
        List<Movie> starWarsNameQuery = movieService.getByName("Star Wars");
        LOG.info("Content of star wars name query is {}" + starWarsNameQuery);

        //Lets query if we have a movie with The Princess Bride as name
        List<Movie> brideQuery = movieService.getByName("The Princess Bride");
        LOG.info("Content of princess bride name query is {}" + brideQuery);


        //Lets query if we have a movie with rating between 6 and 9
        List<Movie> byRatingInterval = movieService.getByRatingInterval(6d, 9d);
        LOG.info("Content of Rating Interval query is {}" + byRatingInterval);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BootElastic.class, args);
    }
}