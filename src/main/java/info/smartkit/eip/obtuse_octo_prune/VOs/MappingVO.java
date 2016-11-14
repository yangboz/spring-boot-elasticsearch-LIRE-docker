package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/14.
 */
public class MappingVO {
    private MappingItemVO my_image_item;

    public MappingVO(MappingItemVO my_image_item) {
        this.my_image_item = my_image_item;
    }

    public MappingVO() {
    }

    public MappingItemVO getMy_image_item() {
        return my_image_item;
    }

    public void setMy_image_item(MappingItemVO my_image_item) {
        this.my_image_item = my_image_item;
    }
}
