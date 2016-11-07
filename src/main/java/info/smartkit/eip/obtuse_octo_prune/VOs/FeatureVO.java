package info.smartkit.eip.obtuse_octo_prune.VOs;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by smartkit on 2016/11/6.
 */
public class FeatureVO {
    public FeatureVO(Feature_CCED CCED, Feature_JCD JCD) {
        this.cced = CCED;
        this.jcd = JCD;
    }

    private Feature_CCED cced= new Feature_CCED(null);
    private Feature_JCD jcd = new Feature_JCD(null);

    public Feature_CCED getCCED() {
        return cced;
    }
    @JsonProperty("CCED")
    public void setCCED(Feature_CCED CCED) {
        this.cced = CCED;
    }

    public Feature_JCD getJCD() {
        return jcd;
    }
    @JsonProperty("jJCD")
    public void setJCD(Feature_JCD JCD) {
        this.jcd = JCD;
    }
}
