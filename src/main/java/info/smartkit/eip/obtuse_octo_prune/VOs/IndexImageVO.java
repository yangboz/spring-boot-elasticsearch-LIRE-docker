package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/6.
 */
public class IndexImageVO {
    private String my_img="... base64 encoded image ...";

    public IndexImageVO() {
    }

    public IndexImageVO(String my_imge) {

        this.my_img = my_imge;
    }

    public String getMy_img() {
        return my_img;
    }

    public void setMy_img(String my_imge) {
        this.my_img = my_imge;
    }

    @Override
    public String toString() {
        return "IndexImageVO{" +
                "my_img='" + my_img + '\'' +
                '}';
    }
}
