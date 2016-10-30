package info.smartkit.eip.obtuse_octo_prune.domains.metadata;

/**
 * Created by smartkit on 2016/10/30.
 * @wee
 */
//"metadata": {
//        "jpeg.image_width": {
//        "type": "string",
//        "store": "yes"
//        },
//        "jpeg.image_height": {
//        "type": "string",
//        "store": "yes"
//        }
//        }

public class JpegMetaData implements IMetaData {

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

    @Override
    public String jasonString() {
        return jasonString;
    }
}
