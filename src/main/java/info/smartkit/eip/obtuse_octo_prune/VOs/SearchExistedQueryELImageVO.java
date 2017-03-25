/*
 * Copyright (c) 2017. SMARTKIT.INFO reserved.
 */

package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/10.
 */
//curl -XPOST 'localhost:9200/test/test/_search' -d '{
//        "query": {
//        "image": {
//        "my_img": {
//        "feature": "CEDD",
//        "index": "test",
//        "type": "test",
//        "id": "image1",
//        "path": "my_image",
//        "hash": "BIT_SAMPLING"
//        }
//        }
//        }
//        }'

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

    public SearchExistedQueryELImageVO(String feature, String index, String type, String id, String path, String hash) {
        this.feature = feature;
        this.index = index;
        this.type = type;
        this.id = id;
        this.path = path;
        this.hash = hash;
    }

    public String getPath() {

        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;
    private String hash;

    @Override
    public String   toString() {
        return "SearchExistedQueryELImageVO{" +
                "feature='" + feature + '\'' +
                ", index='" + index + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", hash='" + hash + '\'' +
                '}';
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
