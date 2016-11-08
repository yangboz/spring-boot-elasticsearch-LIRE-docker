package info.smartkit.eip.obtuse_octo_prune.VOs;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by smartkit on 2016/11/6.
 */
public class FeatureVO {

    private Feature_CCED cced;
    private Feature_JCD jcd;

    public FeatureVO(Feature_CCED cced, Feature_JCD jcd) {
        this.cced = cced;
        this.jcd = jcd;
    }

    public FeatureVO() {
    }

    public Feature_CCED getCced() {
        return cced;
    }
    @JsonProperty("CCED")
    public void setCced(Feature_CCED cced) {
        this.cced = cced;
    }

    public Feature_JCD getJcd() {
        return jcd;
    }
    @JsonProperty("JCD")
    public void setJcd(Feature_JCD jcd) {
        this.jcd = jcd;
    }

    @Override
    public String toString() {
        return "FeatureVO{" +
                "cced=" + cced +
                ", jcd=" + jcd +
                '}';
    }
}
