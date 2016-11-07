package info.smartkit.eip.obtuse_octo_prune.VOs;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by smartkit on 2016/11/6.
 */
public class MetaDataVO {
    private JpegImageVO jpegImageWidth;
    private JpegImageVO getJpegImageHeight;

    public MetaDataVO(JpegImageVO jpegImageWidth, JpegImageVO getJpegImageHeight) {
        this.jpegImageWidth = jpegImageWidth;
        this.getJpegImageHeight = getJpegImageHeight;
    }

    public MetaDataVO() {
    }

    public JpegImageVO getJpegImageWidth() {

        return jpegImageWidth;
    }
    @JsonProperty("jpeg.width")
    public void setJpegImageWidth(JpegImageVO jpegImageWidth) {
        this.jpegImageWidth = jpegImageWidth;
    }

    public JpegImageVO getGetJpegImageHeight() {
        return getJpegImageHeight;
    }
    @JsonProperty("jpeg.height")
    public void setGetJpegImageHeight(JpegImageVO getJpegImageHeight) {
        this.getJpegImageHeight = getJpegImageHeight;
    }
}
