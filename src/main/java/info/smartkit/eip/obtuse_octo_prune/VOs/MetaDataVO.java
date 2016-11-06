package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/6.
 */
public class MetaDataVO {
    public String jasonString = "'metadata': {\n" +
            "        'jpeg.image_width': {\n" +
            "        'type': 'string',\n" +
            "        'store': 'yes'\n" +
            "        },\n" +
            "        'jpeg.image_height': {\n" +
            "        'type': 'string',\n" +
            "        'store': 'yes'\n" +
            "        }\n" +
            "        }";
    public String jasonString() {
        return jasonString;
    }
}
