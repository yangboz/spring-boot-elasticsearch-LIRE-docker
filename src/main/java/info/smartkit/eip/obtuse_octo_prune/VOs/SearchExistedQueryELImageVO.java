package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/10.
 */
public class SearchExistedQueryELImageVO {
//    "feature": "CEDD",
//            "index": "test",
//            "type": "test",
//            "id": "image1",
//            "hash": "BIT_SAMPLING"
    private String feature;
    private String index;
    private String type;
    private String id;
    private String hash;

    public SearchExistedQueryELImageVO(String feature, String index, String type, String id, String hash) {
        this.feature = feature;
        this.index = index;
        this.type = type;
        this.id = id;
        this.hash = hash;
    }

    public SearchExistedQueryELImageVO() {
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
