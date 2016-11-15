package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/6.
 */
//@JsonPropertyOrder({ "type", "feature","metadata"})
public class MappingImageVO {

    private String type="image";
    private FeatureVO feature;
    private MetaDataVO metadata;

    public MappingImageVO(String type,FeatureVO feature, MetaDataVO metaData) {
        this.type = type;
        this.feature = feature;
        this.metadata = metaData;
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

    public MetaDataVO getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaDataVO metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "MappingImageVO{" +
                "type='" + type + '\'' +
                ", feature=" + feature +
                ", metadata=" + metadata +
                '}';
    }
}
