package info.smartkit.eip.obtuse_octo_prune.domains;

/**
 * Created by smartkit on 2016/10/28.
 */
public class LireImage {
    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
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

    private String feature;
    private String base64Image;
    private String hash;//BIT_SAMPLING,LSH
    private double boost;//2.1
    private  int limit;//100

    @Override
    public String toString() {
        return "LireImage{" +
                "feature='" + feature + '\'' +
                ", base64Image='" + base64Image + '\'' +
                ", hash='" + hash + '\'' +
                ", boost=" + boost +
                ", limit=" + limit +
                '}';
    }
}
