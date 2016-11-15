package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/6.
 */
public class MappingPropertiesVO {
    public MappingImageVO my_img;
//    public String my_img = "";


    public MappingPropertiesVO() {
    }

    public MappingImageVO getMy_img() {
        return my_img;
    }

    public void setMy_img(MappingImageVO my_img) {
        this.my_img = my_img;
    }

    @Override
    public String toString() {
        return "MappingPropertiesVO{" +
                "my_image=" + my_img +
                '}';
    }
}
