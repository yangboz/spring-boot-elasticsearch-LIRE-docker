package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/6.
 */
public class MappingPropertiesVO {
    public MappingImageVO my_img;

    public MappingPropertiesVO(MappingImageVO my_img) {
        this.my_img = my_img;
    }

    public MappingPropertiesVO() {
    }

    public MappingImageVO getEl_image() {

        return my_img;
    }

    public void setEl_image(MappingImageVO el_image) {
        this.my_img = el_image;
    }

    @Override
    public String toString() {
        return "MappingPropertiesVO{" +
                "el_image=" + my_img +
                '}';
    }
}
