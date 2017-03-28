package info.smartkit.eip.obtuse_octo_prune.VOs;

import info.smartkit.eip.obtuse_octo_prune.utils.LireFeatures;
import info.smartkit.eip.obtuse_octo_prune.utils.LireHashs;

import java.util.Arrays;

/**
 * Created by smartkit on 2016/11/10.
 */
public class SearchQueryELImageVO {
    private String feature= LireFeatures.CEDD;
    private String imageStr = "...base64...";
    private String hash = LireHashs.CEDD;
    private double boost = 2.1;

    @Override
    public String toString() {
        return "SearchQueryELImageVO{" +
                "feature='" + feature + '\'' +
                ", imageStr='" + imageStr + '\'' +
                ", hash='" + hash + '\'' +
                ", boost=" + boost +
                ", imageData=" + Arrays.toString(imageData) +
                '}';
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public SearchQueryELImageVO(String feature, String imageStr, String hash, double boost, byte[] imageData) {
        this.feature = feature;
        this.imageStr = imageStr;
        this.hash = hash;
        this.boost = boost;
        this.imageData = imageData;
    }

    //    private int limit = 100;
    private byte[] imageData;

    public SearchQueryELImageVO(String feature, String imageStr, String hash, double boost, int limit) {
        this.feature = feature;
        this.imageStr = imageStr;
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

    public String getImageStr() {
        return imageStr;
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
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
