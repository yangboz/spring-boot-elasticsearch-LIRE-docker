package info.smartkit.eip.obtuse_octo_prune.domains;

/**
 * Created by yangboz on 10/15/15.
 */
public class Genre {
    private String name;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
}
