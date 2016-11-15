package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/6.
 */
public class MappingItemVO {
    public MappingPropertiesVO getProperties() {
        return properties;
    }

    public void setProperties(MappingPropertiesVO properties) {
        this.properties = properties;
    }

    public MappingPropertiesVO properties;//


    public MappingItemVO() {
    }

    @Override
    public String toString() {
        return "MappingItemVO{" +
                "properties=" + properties +
                '}';
    }
}
