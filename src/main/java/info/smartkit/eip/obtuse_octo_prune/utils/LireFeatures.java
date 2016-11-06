package info.smartkit.eip.obtuse_octo_prune.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by smartkit on 2016/11/6.
 */
public enum LireFeatures {
//    Supported Features

//  AUTO_COLOR_CORRELOGRAM, BINARY_PATTERNS_PYRAMID, CEDD, SIMPLE_COLOR_HISTOGRAM, COLOR_LAYOUT,
// EDGE_HISTOGRAM, FCTH, GABOR, JCD, JOINT_HISTOGRAM, JPEG_COEFFICIENT_HISTOGRAM, LOCAL_BINARY_PATTERNS,
// LUMINANCE_LAYOUT, OPPONENT_HISTOGRAM, PHOG, ROTATION_INVARIANT_LOCAL_BINARY_PATTERNS, SCALABLE_COLOR, TAMURA

    CCED("BITSAMPLING"),

    JCD("BIT_SAMPLING"),
    LSH("LSH");


    private String hash = "";

    LireFeatures(String hashs) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }
}
