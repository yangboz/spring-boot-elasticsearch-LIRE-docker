package info.smartkit.eip.obtuse_octo_prune.VOs;

import org.openimaj.image.processing.face.detection.CLMDetectedFace;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;

/**
 * Created by smartkit on 2016/11/20.
 */
public class AnalysisResVO {
    private DetectedFace detectedFace;
    private KEDetectedFace keDetectedFace;
    private CLMDetectedFace clmDetectedFace;

    public AnalysisResVO(DetectedFace detectedFace, KEDetectedFace keDetectedFace, CLMDetectedFace clmDetectedFace) {
        this.detectedFace = detectedFace;
        this.keDetectedFace = keDetectedFace;
        this.clmDetectedFace = clmDetectedFace;
    }

    public AnalysisResVO() {
    }

    public DetectedFace getDetectedFace() {
        return detectedFace;
    }

    public void setDetectedFace(DetectedFace detectedFace) {
        this.detectedFace = detectedFace;
    }

    public KEDetectedFace getKeDetectedFace() {
        return keDetectedFace;
    }

    public void setKeDetectedFace(KEDetectedFace keDetectedFace) {
        this.keDetectedFace = keDetectedFace;
    }

    public CLMDetectedFace getClmDetectedFace() {
        return clmDetectedFace;
    }

    public void setClmDetectedFace(CLMDetectedFace clmDetectedFace) {
        this.clmDetectedFace = clmDetectedFace;
    }
}