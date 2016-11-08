package info.smartkit.eip.obtuse_octo_prune.VOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by smartkit on 2016/11/6.
 */
//@JsonPropertyOrder({ "type", "feature","metadata"})
public class MappingImageVO {

    private String type="image";
    private FeatureVO feature;
    private MetaDataVO metaData;

    public MappingImageVO(String type,FeatureVO feature, MetaDataVO metaData) {
        this.type = type;
        this.feature = feature;
        this.metaData = metaData;
    }

    public MappingImageVO() {
    }

    public String getType() {
        return type;
    }

//    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public FeatureVO getFeature() {
        return feature;
    }

    public void setFeature(FeatureVO feature) {
        this.feature = feature;
    }

    public MetaDataVO getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaDataVO metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "MappingImageVO{" +
                "type='" + type + '\'' +
                ", feature=" + feature +
                ", metaData=" + metaData +
                '}';
    }
}
