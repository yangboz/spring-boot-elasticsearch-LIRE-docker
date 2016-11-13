package info.smartkit.eip.obtuse_octo_prune.VOs;

import info.smartkit.eip.obtuse_octo_prune.utils.LireFeatures;
import info.smartkit.eip.obtuse_octo_prune.utils.LireHashs;
import net.semanticmetadata.lire.imageanalysis.LireFeature;

/**
 * Created by smartkit on 2016/11/10.
 */
public class SearchQueryELImageVO {
    private String feature= LireFeatures.CEDD;
    private String image = "...base64...";
    private String hash = LireHashs.CEDD;
    private double boost = 2.1;
//    private int limit = 100;

    public SearchQueryELImageVO(String feature, String image, String hash, double boost, int limit) {
        this.feature = feature;
        this.image = image;
        this.hash = hash;
        this.boost = boost;
//        this.limit = limit;
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

    public double getBoost() {
        return boost;
    }

    public void setBoost(double boost) {
        this.boost = boost;
    }

//    public int getLimit() {
//        return limit;
//    }
//
//    public void setLimit(int limit) {
//        this.limit = limit;
//    }
}
