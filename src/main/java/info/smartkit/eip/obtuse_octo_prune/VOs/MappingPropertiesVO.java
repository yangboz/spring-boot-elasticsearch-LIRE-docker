package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/6.
 */
public class MappingPropertiesVO {
    public MappingImageVO el_image;

    public MappingPropertiesVO(MappingImageVO el_image) {
        this.el_image = el_image;
    }

    public MappingPropertiesVO() {
    }

    public MappingImageVO getEl_image() {

        return el_image;
    }

    public void setEl_image(MappingImageVO el_image) {
        this.el_image = el_image;
    }

    @Override
    public String toString() {
        return "MappingPropertiesVO{" +
                "el_image=" + el_image +
                '}';
    }
}
