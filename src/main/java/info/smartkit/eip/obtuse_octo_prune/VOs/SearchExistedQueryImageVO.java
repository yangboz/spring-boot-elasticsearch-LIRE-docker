package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/10.
 */
public class SearchExistedQueryImageVO {
   private SearchExistedQueryELImageVO my_img;

    public SearchExistedQueryImageVO(SearchExistedQueryELImageVO my_img) {
        this.my_img = my_img;
    }

    public SearchExistedQueryImageVO() {
    }

    public SearchExistedQueryELImageVO getMy_img() {
        return my_img;
    }

    public void setMy_img(SearchExistedQueryELImageVO my_img) {
        this.my_img = my_img;
    }
}
