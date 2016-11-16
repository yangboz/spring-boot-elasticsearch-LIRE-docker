package info.smartkit.eip.obtuse_octo_prune.VOs;

import net.semanticmetadata.lire.ImageSearchHits;

import java.util.LinkedHashMap;

/**
 * Created by smartkit on 2016/11/8.
 */
public class SearchResponseVO {
    private float took;
    private String time_out;
    private ResponseShardVO _shards;
    private Object hits;

    public SearchResponseVO(float took, String time_out, ResponseShardVO _shards, Object hits) {
        this.took = took;
        this.time_out = time_out;
        this._shards = _shards;
        this.hits = hits;
    }

    public SearchResponseVO() {
    }

    public float getTook() {
        return took;
    }

    public void setTook(float took) {
        this.took = took;
    }

    public String getTime_out() {
        return time_out;
    }

    public void setTime_out(String time_out) {
        this.time_out = time_out;
    }

    public ResponseShardVO get_shards() {
        return _shards;
    }

    public void set_shards(ResponseShardVO _shards) {
        this._shards = _shards;
    }

    public Object getHits() {
        return hits;
    }

    public void setHits(Object hits) {
        this.hits = hits;
    }
}
