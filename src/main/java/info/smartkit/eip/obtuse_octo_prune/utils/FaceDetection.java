package info.smartkit.eip.obtuse_octo_prune.utils;


import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;

public class FaceDetection {
 
    // The cascade definition to be used for detection.
    private static final String CASCADE_FILE = "/Users/smartkit/git/spring-boot-elasticsearch-LIRE-docker/src/main/resources/haarcascade_frontalface_alt.xml";
 
    public static void main(String arg[]) throws Exception {
         
 
        // Load the original image.
        opencv_core.IplImage originalImage = cvLoadImage("/Users/smartkit/git/spring-boot-elasticsearch-LIRE-docker/src/main/resources/face.jpg",1);
 
        // We need a grayscale image in order to do the recognition, so we
        // create a new image of the same size as the original one.
        opencv_core.IplImage grayImage = opencv_core.IplImage.create(originalImage.width(),
                originalImage.height(), IPL_DEPTH_8U, 1);
 
        // We convert the original image to grayscale.
         cvCvtColor(originalImage, grayImage, CV_BGR2GRAY);
 
        opencv_core.CvMemStorage storage = opencv_core.CvMemStorage.create();
 
        // We instantiate a classifier cascade to be used for detection, using
        // the cascade definition.
        opencv_objdetect.CvHaarClassifierCascade cascade = new opencv_objdetect.CvHaarClassifierCascade(
                cvLoad(CASCADE_FILE));
 
        // We detect the faces.
        opencv_core.CvSeq faces = cvHaarDetectObjects(grayImage, cascade, storage, 1.1, 1,
                0);
 
        // We iterate over the discovered faces and draw yellow rectangles
        // around them.
        for (int i = 0; i < faces.total(); i++) {
            opencv_core.CvRect r = new opencv_core.CvRect(cvGetSeqElem(faces, i));
            cvRectangle(originalImage, cvPoint(r.x(), r.y()),
                    cvPoint(r.x() + r.width(), r.y() + r.height()),
                    CvScalar.YELLOW, 1, CV_AA, 0);
 
        }
 
        // Save the image to a new file.
        cvSaveImage("/Users/smartkit/git/spring-boot-elasticsearch-LIRE-docker/src/main/resources/face_fd.jpg", originalImage);
 
    }
 
}