package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/16.
 */
public class ImageSearchHitVO {
    private String _index;
    private String _type;
    private String _id;
    private String _score;
    private Object _source;

    public ImageSearchHitVO(String _index, String _type, String _id, String _score, Object _source) {
        this._index = _index;
        this._type = _type;
        this._id = _id;
        this._score = _score;
        this._source = _source;
    }

    public ImageSearchHitVO() {
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

    public String get_score() {
        return _score;
    }

    public void set_score(String _score) {
        this._score = _score;
    }

    public Object get_source() {
        return _source;
    }

    public void set_source(Object _source) {
        this._source = _source;
    }
}
