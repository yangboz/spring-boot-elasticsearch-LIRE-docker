package org.elasticsearch.index.mapper.image;


import net.semanticmetadata.lire.imageanalysis.features.LireFeature;
import net.semanticmetadata.lire.imageanalysis.features.global.*;
import net.semanticmetadata.lire.imageanalysis.features.global.joint.*;
import net.semanticmetadata.lire.imageanalysis.features.global.centrist.*;
import net.semanticmetadata.lire.imageanalysis.features.global.spatialpyramid.*;

/**
 * Features supported by LIRE
 * Subclass of {@link LireFeature}
 */
public enum FeatureEnum {

    AUTO_COLOR_CORRELOGRAM(AutoColorCorrelogram.class),
    BINARY_PATTERNS_PYRAMID(BinaryPatternsPyramid.class),
    CEDD(net.semanticmetadata.lire.imageanalysis.features.global.CEDD.class),
    EDGE_HISTOGRAM(EdgeHistogram.class),
    COLOR_LAYOUT(ColorLayout.class),
    FCTH(FCTH.class),
    FUZZY_COLOR_HISTOGRAM(FuzzyColorHistogram.class),
    FUZZY_OPPONENT_HISTOGRAM(FuzzyOpponentHistogram.class),    
    GABOR(Gabor.class),
    JCD(JCD.class),
    JOINT_HISTOGRAM(JointHistogram.class),
    JPEG_COEFFICIENT_HISTOGRAM(JpegCoefficientHistogram.class),
    LOCAL_BINARY_PATTERNS(LocalBinaryPatterns.class),
    LOCAL_BINARY_PATTERNS_AND_OPPONENT(LocalBinaryPatternsAndOpponent.class),    
    LUMINANCE_LAYOUT(LuminanceLayout.class),
    OPPONENT_HISTOGRAM(OpponentHistogram.class),
    PHOG(PHOG.class),
    RANK_AND_OPPONENT(RankAndOpponent.class),    
    ROTATION_INVARIANT_LOCAL_BINARY_PATTERNS(RotationInvariantLocalBinaryPatterns.class),
    SCALABLE_COLOR(ScalableColor.class),
    SIMPLE_CENTRIST(SimpleCentrist.class),
    SIMPLE_COLOR_HISTOGRAM(SimpleColorHistogram.class),
    SPACC(SPACC.class),
    SPATIAL_PYRAMID_CENTRIST(SpatialPyramidCentrist.class),
    SPCEDD(SPCEDD.class),
    SPFCTH(SPFCTH.class),
    SPJCD(SPJCD.class),
    SPLBP(SPLBP.class),
    TAMURA(Tamura.class);

    private Class<? extends LireFeature> featureClass;

    FeatureEnum(Class<? extends LireFeature> featureClass) {
        this.featureClass = featureClass;
    }

    public Class<? extends LireFeature> getFeatureClass() {
        return featureClass;
    }

    public static FeatureEnum getByName(String name) {
        return valueOf(name.toUpperCase());
    }

}
