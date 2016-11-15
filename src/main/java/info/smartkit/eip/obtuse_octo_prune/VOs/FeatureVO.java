package info.smartkit.eip.obtuse_octo_prune.VOs;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by smartkit on 2016/11/6.
 */
public class FeatureVO {

    private Feature_CEDD CEDD;
    private Feature_JCD JCD;

    public FeatureVO(Feature_CEDD cced, Feature_JCD jcd) {
        this.CEDD = cced;
        this.JCD = jcd;
    }

    public FeatureVO() {
    }

    public Feature_CEDD getCced() {
        return CEDD;
    }
    @JsonProperty("CEDD")
    public void setCced(Feature_CEDD cced) {
        this.CEDD = cced;
    }

    public Feature_JCD getJcd() {
        return JCD;
    }
    @JsonProperty("JCD")
    public void setJcd(Feature_JCD JCD) {
        this.JCD = JCD;
    }

    @Override
    public String toString() {
        return "FeatureVO{" +
                "CEDD=" + CEDD +
                ", JCD=" + JCD +
                '}';
    }
}
