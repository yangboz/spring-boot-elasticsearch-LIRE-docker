package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/8.
 */
public class IndexResponseVO {
//    {"_index":"test","_type":"test","_id":"AVhC1Y72yZZqeGpBPk-L","_version":1,"_shards":{"total":2,"successful":1,"failed":0},"created":true}?
    private String _index;
    private String _type;
    private String _id;
    private String _version;
    private boolean created;
    private ResponseShardVO _shards;

    public IndexResponseVO(String _index, String _type, String _id, String _version, boolean created, ResponseShardVO _shards) {
        this._index = _index;
        this._type = _type;
        this._id = _id;
        this._version = _version;
        this.created = created;
        this._shards = _shards;
    }

    public IndexResponseVO() {
    }

    public String get_index() {
        return _index;
    }

    public void set_index(String _index) {
        this._index = _index;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_version() {
        return _version;
    }

    public void set_version(String _version) {
        this._version = _version;
    }

    public boolean getCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public ResponseShardVO get_shards() {
        return _shards;
    }

    public void set_shards(ResponseShardVO _shards) {
        this._shards = _shards;
    }
}
