package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/10.
 */
public class SearchQueryELImageVO {
    private String feature;
    private String image;
    private String hash;
    private float boost;
    private int limit;

    public SearchQueryELImageVO(String feature, String image, String hash, float boost, int limit) {
        this.feature = feature;
        this.image = image;
        this.hash = hash;
        this.boost = boost;
        this.limit = limit;
    }

    public SearchQueryELImageVO() {
    }

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

    public float getBoost() {
        return boost;
    }

    public void setBoost(float boost) {
        this.boost = boost;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
