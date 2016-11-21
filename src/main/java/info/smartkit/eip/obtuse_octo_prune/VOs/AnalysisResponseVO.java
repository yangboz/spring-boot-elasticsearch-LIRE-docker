package info.smartkit.eip.obtuse_octo_prune.VOs;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.openimaj.image.processing.face.detection.CLMDetectedFace;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;

/**
 * Created by smartkit on 2016/11/20.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="@facePatch")
public class AnalysisResponseVO {
//    @JsonManagedReference("parent")
    private DetectedFace detectedFace;
    private KEDetectedFace keDetectedFace;
    private CLMDetectedFace clmDetectedFace;

    public AnalysisResponseVO(DetectedFace detectedFace, KEDetectedFace keDetectedFace, CLMDetectedFace clmDetectedFace) {
        this.detectedFace = detectedFace;
        this.keDetectedFace = keDetectedFace;
        this.clmDetectedFace = clmDetectedFace;
    }

    public AnalysisResponseVO() {
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