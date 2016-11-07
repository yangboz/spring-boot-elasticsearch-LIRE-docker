package info.smartkit.eip.obtuse_octo_prune.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by yangboz on 10/15/15.
 */
@Document(indexName = "elastic_sample", type = "movie")
public class Movie {

    @Id
    private  int id;

    private String name;

    @Field(type = FieldType.Nested)
    private List<Genre> genre;

    private Double rating;

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", rating=" + rating +
                '}';
    }
}