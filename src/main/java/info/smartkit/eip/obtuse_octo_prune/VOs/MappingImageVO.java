package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/6.
 */
public class MappingImageVO {
    private String type="image";
    private FeatureVO feature;
    private MetaDataVO metaData;

    public MappingImageVO(String type,FeatureVO feature, MetaDataVO metaData) {
        this.type = type;
        this.feature = feature;
        this.metaData = metaData;
    }

    public String getType() {
        return type;
    }

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
}
