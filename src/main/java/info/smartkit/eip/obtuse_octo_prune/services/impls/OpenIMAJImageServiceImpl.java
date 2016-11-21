package info.smartkit.eip.obtuse_octo_prune.services.impls;

import info.smartkit.eip.obtuse_octo_prune.VOs.AnalysisResponseVO;
import info.smartkit.eip.obtuse_octo_prune.services.OpenIMAjImageService;
import org.apache.log4j.Logger;
import org.openimaj.feature.FloatFV;
import org.openimaj.feature.FloatFVComparison;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.feature.local.engine.asift.ASIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.image.processing.face.detection.CLMDetectedFace;
import org.openimaj.image.processing.face.detection.CLMFaceDetector;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.image.processing.face.feature.DoGSIFTFeature;
import org.openimaj.image.processing.face.feature.FacePatchFeature;
import org.openimaj.image.processing.face.feature.comparison.DoGSIFTFeatureComparator;
import org.openimaj.image.processing.face.feature.comparison.FaceFVComparator;
import org.openimaj.image.processing.face.similarity.FaceSimilarityEngine;
import org.openimaj.image.processing.face.util.CLMDetectedFaceRenderer;
import org.openimaj.image.processing.face.util.KEDetectedFaceRenderer;
import org.openimaj.image.processing.face.util.SimpleDetectedFaceRenderer;
import org.openimaj.math.geometry.transforms.HomographyRefinement;
import org.openimaj.math.geometry.transforms.estimation.RobustHomographyEstimator;
import org.openimaj.math.model.fit.RANSAC;
import org.openimaj.tools.faces.FaceSimilarityTool;
import org.openimaj.tools.faces.PredefinedStrategy;
import org.openimaj.util.pair.Pair;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by smartkit on 2016/11/21.
 */
@Service
public class OpenIMAjImageServiceImpl implements OpenIMAjImageService {

    private static Logger LOG = org.apache.log4j.LogManager.getLogger(OpenIMAjImageServiceImpl.class);

    @Override
    public AnalysisResponseVO analysis(File imgFile) throws IOException {
        MBFImage mbf = ImageUtilities.readMBF(imgFile);
        // A simple Haar-Cascade face detector
        AnalysisResponseVO analysisResponseVO = new AnalysisResponseVO();
        HaarCascadeDetector det1 = new HaarCascadeDetector();
        DetectedFace face1 = det1.detectFaces(mbf.flatten()).get(0);
        new SimpleDetectedFaceRenderer()
                .drawDetectedFace(mbf,10,face1);
        analysisResponseVO.setDetectedFace(face1);
//// Get the facial keypoints
        FKEFaceDetector det2 = new FKEFaceDetector();
        KEDetectedFace face2 = det2.detectFaces(mbf.flatten()).get(0);
        new KEDetectedFaceRenderer()
                .drawDetectedFace(mbf,10,face2);

        analysisResponseVO.setKeDetectedFace(face2);
////// With the CLM Face Model
        CLMFaceDetector det3 = new CLMFaceDetector();
        CLMDetectedFace face3 = det3.detectFaces(mbf.flatten()).get(0);
        new CLMDetectedFaceRenderer()
                .drawDetectedFace(mbf,10,face3);
        analysisResponseVO.setClmDetectedFace(face3);
//        DisplayUtilities.displayName(mbf, "OPenIMAJ Analysis");//for GUI testing.

        return analysisResponseVO;
    }

    //@see http://grepcode.com/file/repo1.maven.org/maven2/org.openimaj/examples/1.3/org/openimaj/examples/image/feature/local/ASIFTMatchingExample.java#ASIFTMatchingExample.createFastBasicMatcher%28%29
    private static LocalFeatureMatcher<Keypoint> createConsistentRANSACHomographyMatcher() {
        final ConsistentLocalFeatureMatcher2d<Keypoint> matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(
                createFastBasicMatcher());
        matcher.setFittingModel(new RobustHomographyEstimator(10.0, 1000, new RANSAC.BestFitStoppingCondition(),
                HomographyRefinement.NONE));

        return matcher;
    }
    private static LocalFeatureMatcher<Keypoint> createFastBasicMatcher() {
        return new FastBasicKeypointMatcher<Keypoint>(8);
    }

    @Override
    public List<Pair<Keypoint>> matching(File input1, File input2) throws IOException {

        // Read the images from two streams
        final FImage input_1 = ImageUtilities.readF(new FileInputStream(input1));
        final FImage input_2 = ImageUtilities.readF(new FileInputStream(input2));

        // Prepare the engine to the parameters in the IPOL demo
        final ASIFTEngine engine = new ASIFTEngine(false, 7);

        // Extract the keypoints from both images
        final LocalFeatureList<Keypoint> input1Feats = engine.findKeypoints(input_1);
        System.out.println("Extracted input1: " + input1Feats.size());
        final LocalFeatureList<Keypoint> input2Feats = engine.findKeypoints(input_2);
        System.out.println("Extracted input2: " + input2Feats.size());

        // Prepare the matcher, uncomment this line to use a basic matcher as
        // opposed to one that enforces homographic consistency
        // LocalFeatureMatcher<Keypoint> matcher = createFastBasicMatcher();
        final LocalFeatureMatcher<Keypoint> matcher = createConsistentRANSACHomographyMatcher();

        // Find features in image 1
        matcher.setModelFeatures(input1Feats);
        // ... against image 2
        matcher.findMatches(input2Feats);

        // Get the matches
        final List<Pair<Keypoint>> matches = matcher.getMatches();
        System.out.println("NMatches: " + matches.size());

        // Display the results
        final MBFImage inp1MBF = input_1.toRGB();
        final MBFImage inp2MBF = input_2.toRGB();
//        		DisplayUtilities.display(MatchingUtilities.drawMatches(inp1MBF, inp2MBF, matches, RGBColour.RED));//for GUI testing.

        return matches;
    }
    //@see http://www.programcreek.com/java-api-examples/index.php?api=org.openimaj.feature.FloatFVComparison
    //@see http://codenav.org/code.html?project=/org/openimaj/FaceTools/1.3.1&path=/Source%20Packages/org.openimaj.tools.faces/PredefinedStrategy.java
    @Override
    public Map<String, Map<String, Double>> similarity(File input1, File input2) throws IOException {
        FaceSimilarityTool fst = new FaceSimilarityTool();
        final FImage image1 = ImageUtilities.readF(new FileInputStream(input1));
        final FImage image2 = ImageUtilities.readF(new FileInputStream(input2));
        Map<String, Map<String, Double>> results = new Hashtable<>();
//        final HaarCascadeDetector detector = HaarCascadeDetector.BuiltInCascade.frontalface_default.load();
//        final DoGSIFTFeature.Extractor extractor = new DoGSIFTFeature.Extractor();
//        final DoGSIFTFeatureComparator comparator = new DoGSIFTFeatureComparator();
//        final FaceFVComparator<FacePatchFeature, FloatFV> comparator = new FaceFVComparator<FacePatchFeature, FloatFV>(FloatFVComparison.EUCLIDEAN);
//        PredefinedStrategy.FACEPATCH_EUCLIDEAN.strategy();
//        PredefinedStrategy.LOCAL_BINARY_PATTERN.strategy();
//        PredefinedStrategy.LOCAL_TRINARY_PATTERN.strategy();
//        PredefinedStrategy.SIFT.strategy();
        // then we set up a face detector; will use a haar cascade detector to
        // find faces, followed by a keypoint-enhanced detector to find facial
        // keypoints for our feature. There are many different combinations of
        // features and detectors to choose from.
        final HaarCascadeDetector detector = HaarCascadeDetector.BuiltInCascade.frontalface_alt2.load();
        final FKEFaceDetector kedetector = new FKEFaceDetector(detector);

        // now we construct a feature extractor - this one will extract pixel
        // patches around prominant facial keypoints (like the corners of the
        // mouth, etc) and build them into a vector.
        final FacePatchFeature.Extractor extractor = new FacePatchFeature.Extractor();

        // in order to compare the features we need a comparator. In this case,
        // we'll use the Euclidean distance between the vectors:
        final FaceFVComparator<FacePatchFeature, FloatFV> comparator =
                new FaceFVComparator<FacePatchFeature, FloatFV>(FloatFVComparison.EUCLIDEAN);

        // Now we can construct the FaceSimilarityEngine. It is capable of
        // running the face detector on a pair of images, extracting the
        // features and then comparing every pair of detected faces in the two
        // images:
        final FaceSimilarityEngine<KEDetectedFace, FacePatchFeature, FImage> engine =
                new FaceSimilarityEngine<KEDetectedFace, FacePatchFeature, FImage>(kedetector, extractor, comparator);
        List<File> others = new ArrayList<File>();
        others.add(input2);
        results = fst.getDistances(input1,others,engine);//new FaceSimilarityEngine<>(detector,extractor,comparator)PredefinedStrategy.SIFT.strategy()
        LOG.info("similarity results:"+results.toString());
//        final HaarCascadeDetector detector = HaarCascadeDetector.BuiltInCascade.frontalface_alt2.load();
//        final FKEFaceDetector kedetector = new FKEFaceDetector(detector);
//        final FacePatchFeature.Extractor extractor = new FacePatchFeature.Extractor();
//        final FaceFVComparator<FacePatchFeature, FloatFV> comparator = new FaceFVComparator<FacePatchFeature, FloatFV>(FloatFVComparison.EUCLIDEAN);
//        final FaceSimilarityEngine<KEDetectedFace, FacePatchFeature, FImage> engine = new FaceSimilarityEngine<KEDetectedFace, FacePatchFeature, FImage>(kedetector, extractor, comparator);
//        engine.setQuery(image1, "image1");
//        engine.setTest(image2, "image2");
//        engine.performTest();
//        for (final Map.Entry<String, Map<String, Double>> e : engine.getSimilarityDictionary().entrySet()) {
//            double bestScore = Double.MAX_VALUE;
//            String best = null;
//            for (final Map.Entry<String, Double> matches : e.getValue().entrySet()) {
//                if (matches.getValue() < bestScore) {
//                    bestScore = matches.getValue();
//                    best = matches.getKey();
//                }
//            }
//            LOG.info("similarityBest:"+best);
//        }
        return results;
    }


    @Override
    public Map<String,Map<String,Double>> similarity(List<File> imgFiles, Boolean withFirst) {
        FaceSimilarityTool fst = new FaceSimilarityTool();
        final HaarCascadeDetector detector = HaarCascadeDetector.BuiltInCascade.frontalface_default.load();
        final DoGSIFTFeature.Extractor extractor = new DoGSIFTFeature.Extractor();
        final DoGSIFTFeatureComparator comparator = new DoGSIFTFeatureComparator();
        Map<String,Map<String,Double>> results = fst.getDistances(imgFiles,withFirst,new FaceSimilarityEngine<>(detector,extractor,comparator));
        LOG.info("similarity results:"+results.toString());
        return results;
    }
}
