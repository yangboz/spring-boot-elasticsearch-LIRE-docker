package info.smartkit.eip.obtuse_octo_prune.controllers;

import com.wordnik.swagger.annotations.ApiOperation;
import info.smartkit.eip.obtuse_octo_prune.VOs.*;
import info.smartkit.eip.obtuse_octo_prune.services.OpenIMAJImageService;
import info.smartkit.eip.obtuse_octo_prune.utils.ImageUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.util.pair.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.*;

/**
 * Created by smartkit on 2016/10/28.
 */
@RestController
@RequestMapping("/openimaj/image")
public class OpenIMAJImageController {

    @Autowired
    private OpenIMAJImageService imageService;

    private static Logger LOG = LogManager.getLogger(OpenIMAJImageController.class);

    private String getImageDataString(@RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile file) {
        String imageDataString = null;
        if (!file.isEmpty()) {
            LOG.info("uploaded file:"+file.toString());
            try{
                // Reading a Image file from file system
//                MultipartFile  file;
                byte [] byteArr=file.getBytes();
                InputStream imageInFile = new ByteArrayInputStream(byteArr);
//            FileInputStream imageInFile = new FileInputStream(file);
                byte imageData[] = new byte[byteArr.length];
                imageInFile.read(imageData);

                // Converting Image byte array into Base64 String
                imageDataString = ImageUtils.encodeImage(imageData);
                LOG.info("Image Successfully Manipulated!base64:"+imageDataString);
                IndexImageVO indexImageVO = new IndexImageVO(imageDataString);
                LOG.info("indexImageVO:"+indexImageVO.toString());
            } catch (FileNotFoundException e) {
                LOG.error("Image not found" + e);
            } catch (IOException ioe) {
                LOG.error("Exception while reading the Image " + ioe);
            }
        } else {
            LOG.error("You failed to upload " + file.getName() + " because the file was empty.");
            //
        }
        return imageDataString;
    }

    // @see: https://spring.io/guides/gs/uploading-files/
    @RequestMapping(method = RequestMethod.POST, value = "/analysis", consumes = MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(httpMethod = "POST", value = "Response a AnalysisResponseVO describing picture is successfully uploaded and with face detections.")
    public
    @ResponseBody
    AnalysisResponseVO openIMAJAnalysis(
            @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile file

    )  {
        AnalysisResponseVO analysisResponseVO = new AnalysisResponseVO();
        if (!file.isEmpty()) {
            LOG.info("uploaded file:"+file.toString());
            try{
                //multipartToFile
                File imgFile = multipartToFile(file);
                return imageService.analysis(imgFile);
            } catch (FileNotFoundException e) {
                LOG.error("Image not found" + e);
            } catch (IOException ioe) {
                LOG.error("Exception while reading the Image " + ioe);
            }
        } else {
            LOG.error("You failed to upload " + file.getName() + " because the file was empty.");
            //

        }
        return analysisResponseVO;
    }


    // @see: http://grepcode.com/file/repo1.maven.org/maven2/org.openimaj/examples/1.3/org/openimaj/examples/image/feature/local/ASIFTMatchingExample.java
    @RequestMapping(method = RequestMethod.POST, value = "/matching", consumes = MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(httpMethod = "POST", value = "Response a extract ASIFT features and match them.")
    public
    @ResponseBody
    List<Pair<Keypoint>> ASIFTMatching(
            @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile input1,
            @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile input2

    )  {
        List<Pair<Keypoint>> results = new ArrayList<Pair<Keypoint>>();
        if (!input1.isEmpty() && !input2.isEmpty()) {
            LOG.info("uploaded file:s"+input1+input2);
            try{
                //multipartToFile
                File imgFile1 = multipartToFile(input1);
                File imgFile2 = multipartToFile(input2);
                return imageService.matching(imgFile1,imgFile2);
            } catch (FileNotFoundException e) {
                LOG.error("Image not found" + e);
            } catch (IOException ioe) {
                LOG.error("Exception while reading the Image " + ioe);
            }
        } else {
            LOG.error("You failed to upload " + input1.getName() + input2.getName() + " because the file was empty.");
            //

        }
        return results;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/similarity/{withFirst}", consumes = MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(httpMethod = "POST", value = "Response actual comparison function that performs the nested loops as necessary to match all the faces against each other."
            ,notes = "inputList A list of File to process\n" +
            "withFirst Whether to compare the first against all others (TRUE) or compare all against each other (FALSE)")
    public
    @ResponseBody
    Map<String,Map<String,Double>> FaceSimilarityTool(
            @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile[] files
            ,@PathVariable("withFirst") Boolean withFirst

    )  {
        Map<String,Map<String,Double>> results = new Hashtable<>();
        if (files.length>2) {//at least 2
            LOG.info("uploaded fileS:"+files.toString());
            try{
                //multipartToFile
                List<File> imgFiles = multipartToFiles(files);
                return imageService.similarity(imgFiles,withFirst);
            } catch (FileNotFoundException e) {
                LOG.error("Image not found" + e);
            } catch (IOException ioe) {
                LOG.error("Exception while reading the Image " + ioe);
            }
        } else {
            LOG.error("You failed to upload " + files + " because the file was empty.");
            //
        }
        return results;
    }

    private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                multipart.getOriginalFilename());
        multipart.transferTo(tmpFile);
        return tmpFile;
    }

    private List<File> multipartToFiles(MultipartFile[] multiparts) throws IllegalStateException, IOException {
        List<File> tmpFiles = new ArrayList<File>();

        for (MultipartFile multipart : multiparts
                ) {
            tmpFiles.add(this.multipartToFile(multipart));
        }
        return tmpFiles;
    }
}
