package info.smartkit.eip.obtuse_octo_prune.services;

import info.smartkit.eip.obtuse_octo_prune.VOs.*;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.util.pair.Pair;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * Created by smartkit on 2016/10/28.
 * @see:http://openimaj.org/
 */
public interface OpenIMAJImageService {
    public AnalysisResponseVO analysis(File imgFile) throws IOException;
    public List<Pair<Keypoint>> matching(File input1, File input2) throws IOException;
    public Map<String,Map<String,Double>> similarity(File input1, File input2) throws IOException;
    public Map<String,Map<String,Double>> similarity(List<File> imgFiles, Boolean withFirst) throws FileNotFoundException;
}
