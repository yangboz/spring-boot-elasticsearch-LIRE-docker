package info.smartkit.eip.obtuse_octo_prune.daos;

import info.smartkit.eip.obtuse_octo_prune.domains.Movie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by yangboz on 10/15/15.
 */
public interface MovieRepository extends ElasticsearchRepository<Movie, Long> {
    public List<Movie> findByName(String name);

    public List<Movie> findByRatingBetween(Double beginning, Double end);
}
