package info.smartkit.eip.obtuse_octo_prune;

import info.smartkit.eip.obtuse_octo_prune.domains.Genre;
import info.smartkit.eip.obtuse_octo_prune.domains.Movie;
import info.smartkit.eip.obtuse_octo_prune.services.ESImageService;
import info.smartkit.eip.obtuse_octo_prune.services.MovieServiceItf;
import info.smartkit.eip.obtuse_octo_prune.utils.EsUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.SimpleQueryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import java.net.InetAddress;
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
        //@see: https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.4/transport-client.html
//        EsUtil.client = TransportClient.builder().build()
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.0.8"), 9300));
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch_smartkit").build();
        EsUtil.client = TransportClient.builder().settings(settings)
                .build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        LOG.info("EsClient:"+EsUtil.client);

    }

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext cac = SpringApplication.run(BootElastic.class, args);
        cac.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {

            @Override
            public void onApplicationEvent(ContextClosedEvent event) {
                // on shutdown
                EsUtil.client.close();
            }
        });

    }
}