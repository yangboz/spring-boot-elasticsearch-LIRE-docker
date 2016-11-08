package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/6.
 */
public class SearchQueryVO {
    private String feature = "CCED";
    private String image = "... base64 encoded image to search ...";
    private String hash = "BIT_SAMPLING";
    private double boost = 2.1;
    private int limit = 100;

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public double getBoost() {
        return boost;
    }

    public void setBoost(double boost) {
        this.boost = boost;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "SearchQueryVO{" +
                "feature='" + feature + '\'' +
                ", image='" + image + '\'' +
                ", hash='" + hash + '\'' +
                ", boost=" + boost +
                ", limit=" + limit +
                '}';
    }
}
