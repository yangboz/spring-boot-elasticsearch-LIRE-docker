package info.smartkit.eip.obtuse_octo_prune.controllers;

import com.wordnik.swagger.annotations.ApiOperation;
import info.smartkit.eip.obtuse_octo_prune.VOs.*;
import info.smartkit.eip.obtuse_octo_prune.services.ImageService;
import info.smartkit.eip.obtuse_octo_prune.utils.ImageUtils;
import info.smartkit.eip.obtuse_octo_prune.utils.LireFeatures;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import java.io.*;

/**
 * Created by smartkit on 2016/10/28.
 */
@RestController
@RequestMapping("/es/image")
public class ImageController {

    @Autowired
private ImageService imageService;

    private static Logger LOG = LogManager.getLogger(MovieController.class);

    //
    @RequestMapping(value = "setting/{index}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(httpMethod = "PUT", value = "Response a string describing if the el_index  is successfully updated or not.",notes = "e.g. el_index")
    public HttpResponseVO setting(@PathVariable("index") String index, @RequestBody SettingsVO value) {
         return imageService.setting(index,value);

    }

    @RequestMapping(value = "mapping/{index}/item/{item}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(httpMethod = "PUT", value = "Response a string describing if the el_index/el_image_item  is successfully updated or not.",
            notes = "e.g.index: el_index,item: el_image_item,type: image,hash: BIT_SAMPLING,LSH,store: yes")
    public HttpResponseVO mapping(@PathVariable("index") String index,@PathVariable("item") String item, @RequestBody MappingVO value) {
         return imageService.mapping(index,item,value);
    }


    @RequestMapping(value = "search/{database}/{table}/",method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the SearchVO is successfully created or not.",notes = "e.g. database: test,table: test")
    public SearchResponseVO search(@PathVariable("database") String database,@PathVariable("table") String table,
                                   @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile file) {
        SearchVO searchVO = new SearchVO();
        searchVO.getQuery().getImage().getEl_image().setFeature(LireFeatures.CEDD);
        searchVO.getQuery().getImage().getEl_image().setImage(this.getImageDataString(file));
        return imageService.search(database,table,searchVO);
    }

    @RequestMapping(value = "searchExisted/{database}/{table}/",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the SearchQueryVO is successfully created or not.",notes = "e.g. database: test,table: test")
    public SearchResponseVO searchExisted(@PathVariable("database") String database,@PathVariable("table") String table, @RequestBody SearchExistedVO value) {
        return imageService.searchExisted(database,table,value);
    }

    @RequestMapping(method = RequestMethod.POST, value = "index/{database}/{table}/", consumes = MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing picture is successfully uploaded or not with face detect option."
            ,notes = "e.g. database: test,table: test,key(fixed): el_image")
    public
    @ResponseBody
    IndexResponseVO index(
            @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile file,
            @PathVariable("database") String database,@PathVariable("table") String table)  {
        IndexImageVO indexImageVO = new IndexImageVO();
        indexImageVO.setEl_image(this.getImageDataString(file));
        IndexResponseVO indexResponseVO = imageService.index(database,table,indexImageVO);
        return indexResponseVO;
    }

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
    @RequestMapping(method = RequestMethod.POST, value = "/upload/{face}", consumes = MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing picture is successfully uploaded or not with face feature option.")
    public
    @ResponseBody
    String singleImageFileUploadToBase64String(
            @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile file,
            @PathVariable("face") Boolean face

    )  {
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
                String imageDataString = ImageUtils.encodeImage(imageData);


//            // Converting a Base64 String into Image byte array
//            byte[] imageByteArray = ImageUtils.decodeImage(imageDataString);
//
//            // Write a image byte array into file system
//            FileOutputStream imageOutFile = new FileOutputStream(
//                    "/Users/jeeva/Pictures/wallpapers/water-drop-after-convert.jpg");
//
//            imageOutFile.write(imageByteArray);
//
//            imageInFile.close();
//            imageOutFile.close();

                LOG.info("Image Successfully Manipulated!base64:"+imageDataString);
                return imageDataString;
            } catch (FileNotFoundException e) {
                LOG.error("Image not found" + e);
            } catch (IOException ioe) {
                LOG.error("Exception while reading the Image " + ioe);
            }
        } else {
            LOG.error("You failed to upload " + file.getName() + " because the file was empty.");
            //

        }
        LOG.info("With image face detect? " + face);
        if (face) {

        }
        return "Undefined value";
    }
}
