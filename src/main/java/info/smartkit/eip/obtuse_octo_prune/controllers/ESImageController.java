package info.smartkit.eip.obtuse_octo_prune.controllers;

import com.wordnik.swagger.annotations.ApiOperation;
import info.smartkit.eip.obtuse_octo_prune.VOs.*;
import info.smartkit.eip.obtuse_octo_prune.services.ESImageService;
import info.smartkit.eip.obtuse_octo_prune.utils.ImageUtils;
import info.smartkit.eip.obtuse_octo_prune.utils.LireFeatures;
import info.smartkit.eip.obtuse_octo_prune.utils.LireHashs;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/image/es")
public class ESImageController {

    @Autowired
    private ESImageService imageService;

    private static Logger LOG = LogManager.getLogger(ESImageController.class);

    //
    @RequestMapping(value = "setting/{index}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(httpMethod = "PUT", value = "Response a string describing if the my_index  is successfully updated or not.",notes = "e.g. my_index")
    public HttpResponseVO setting(@PathVariable("index") String index , @RequestBody SettingsVO value) {
         return imageService.setting(index,value);

    }


    @RequestMapping(value = "mapping/{index}/item/{item}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(httpMethod = "PUT", value = "Response a string describing if the my_index/my_image_item  is successfully updated or not.",
            notes = "e.g.index: my_index,item: my_image_item,type: image,hash: BIT_SAMPLING,LSH,store: yes")
    public HttpResponseVO mapping(@PathVariable("index") String index,@PathVariable("item") String item) {
        return imageService.mapping(index,item);
    }


    @RequestMapping(value = "search/{index}/{item}/",method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the SearchVO is successfully created or not.",notes = "e.g. index: my_index,item: my_image_Item")
    public SearchResponseVO search(@PathVariable("index") String index,@PathVariable("item") String item,
                                   @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile file) {
        SearchVO searchVO = new SearchVO();
        searchVO.getQuery().getImage().getMy_img().setFeature(LireFeatures.CEDD);
        searchVO.getQuery().getImage().getMy_img().setImage(this.getImageDataString(file));
        return imageService.search(index,item,searchVO);
    }

    @RequestMapping(value = "searchExisted/{index}/{item}/{id}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the SearchQueryVO is successfully created or not.",
            notes = "e.g. database: test,table: test,index: AVhgkCmlo6Smc5eMO6E2 ,index: test ,type: test ,hash: BIT_SAMPLING")
    public SearchResponseVO searchExisted(@PathVariable("index") String index,@PathVariable("item") String item, @PathVariable("id") String id) {
        SearchExistedVO searchExistedVO = new SearchExistedVO(new SearchExistedQueryVO(new SearchExistedQueryImageVO(new SearchExistedQueryELImageVO(LireFeatures.CEDD,index,item,id, LireHashs.CEDD))));
        return imageService.searchExisted(index,item,searchExistedVO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "index/{name}/{item}/", consumes = MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing picture is successfully uploaded or not with face detect option."
            ,notes = "e.g. index: my_index,item: my_image_item,key(fixed): my_img")
    public
    @ResponseBody
    IndexResponseVO index(
            @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile file,
            @PathVariable("name") String name,@PathVariable("item") String item)  {
        IndexImageVO indexImageVO = new IndexImageVO();
        indexImageVO.setMy_img(this.getImageDataString(file));
        IndexResponseVO indexResponseVO = imageService.index(name,item,indexImageVO);
        return indexResponseVO;
    }

    private String getImageDataString(MultipartFile file) {
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


    private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                multipart.getOriginalFilename());
        multipart.transferTo(tmpFile);
        return tmpFile;
    }

}
