package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/7.
 */
public class JpegImageVO {
    private String type = "string";
    private String store = "yes";

    public JpegImageVO(String type, String store) {
        this.type = type;
        this.store = store;
    }

    public JpegImageVO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "JpegImageVO{" +
                "type='" + type + '\'' +
                ", store='" + store + '\'' +
                '}';
    }
}
