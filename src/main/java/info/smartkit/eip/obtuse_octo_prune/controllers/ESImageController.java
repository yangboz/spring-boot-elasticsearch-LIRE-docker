package info.smartkit.eip.obtuse_octo_prune.controllers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.wordnik.swagger.annotations.ApiOperation;
import info.smartkit.eip.obtuse_octo_prune.VOs.*;
import info.smartkit.eip.obtuse_octo_prune.services.ESImageService;
import info.smartkit.eip.obtuse_octo_prune.utils.FileUtil;
import info.smartkit.eip.obtuse_octo_prune.utils.LireFeatures;
import info.smartkit.eip.obtuse_octo_prune.utils.LireHashs;
import org.apache.commons.codec.binary.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.elasticsearch.action.index.IndexResponse;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static info.smartkit.eip.obtuse_octo_prune.utils.ImageUtils.decodeImage;
import static info.smartkit.eip.obtuse_octo_prune.utils.ImageUtils.encodeImage;

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
    @ApiOperation(httpMethod = "PUT", value = "Response a string describing if the my_index  is successfully updated or not.", notes = "e.g.index name: my_index, setting body: 5,1,1070499")
    public HttpResponseVO setting(@PathVariable("index") String index, @RequestBody SettingsVO value) {
        return imageService.setting(index, value);

    }


    @RequestMapping(value = "mapping/{index}/item/{item}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(httpMethod = "PUT", value = "Response a string describing if the my_index/my_image_item  is successfully updated or not.",
            notes = "e.g.index: my_index,item: my_image_item,type: image,hash: BIT_SAMPLING,LSH,store: yes")
    public HttpResponseVO mapping(@PathVariable("index") String index, @PathVariable("item") String item) {
        return imageService.mapping(index, item);
    }


    @RequestMapping(value = "search/{index}/{item}/", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the SearchVO is successfully created or not.", notes = "e.g. index: my_index,item: my_image_Item")
    public SearchResponseVO search(@PathVariable("index") String index, @PathVariable("item") String item,
                                 @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile file) throws IOException {
        SimpleSearchVO simpleSearchVO  = new SimpleSearchVO();
        simpleSearchVO.setItem(item);
        simpleSearchVO.setIndex(index);
        byte[] imageData = decodeImage(this.getMultipartImageDataString(file));
        return imageService.search(simpleSearchVO, imageData);
    }

    @RequestMapping(value = "searchExisted/{index}/{item}/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the SearchQueryVO is successfully created or not.",
            notes = "e.g. database: test,table: test,index: AVhgkCmlo6Smc5eMO6E2 ,index: test ,type: test ,hash: BIT_SAMPLING")
    public SearchResponseVO searchExisted(@PathVariable("index") String index, @PathVariable("item") String item, @PathVariable("id") String id) {
        SearchExistedVO searchExistedVO = new SearchExistedVO(new SearchExistedQueryVO(new SearchExistedQueryImageVO(new SearchExistedQueryELImageVO(LireFeatures.CEDD, index, item, id,"my_img", LireHashs.CEDD))));
        SearchResponseVO response = imageService.searchExisted(index, item, searchExistedVO);
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
        return response;
    }

    @RequestMapping(value = "searchUrl/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the SearchQueryVO is successfully created or not.",
            notes = "e.g. database: test,table: test,index: AVhgkCmlo6Smc5eMO6E2 ,index: test ,type: test ,hash: BIT_SAMPLING, url:http://xyz.com/a.jpg")
    public SearchResponseVO searchUrl(@RequestBody @Valid SimpleSearchVO sSearchVO) throws IOException {
        SimpleSearchVO simpleSearchVO  = new SimpleSearchVO();
        simpleSearchVO.setItem(sSearchVO.getItem());
        simpleSearchVO.setIndex(sSearchVO.getIndex());
        //read image url.
        URL imageUrl = new URL(sSearchVO.getUrl());
        InputStream is = imageUrl.openStream();
        byte[] imageData = IOUtils.toByteArray(is);
        // Converting Image byte array into Base64 String
//        String imageDataString = encodeImage(imageData);
//       LOG.debug("imageDataString : " + imageDataString);
        //
        return imageService.search(simpleSearchVO,imageData);
    }

    @RequestMapping(method = RequestMethod.POST, value = "index/{name}/{item}/", consumes = MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing picture is successfully uploaded for indexing or not."
            , notes = "e.g. name: my_index,item: my_image_item,key(fixed): my_img")
    public
    @ResponseBody
    IndexResponse index(
            @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile file,
            @PathVariable("name") String name, @PathVariable("item") String item) throws IOException {
        IndexImageVO indexImageVO = new IndexImageVO();
        byte[] imageData = decodeImage(this.getMultipartImageDataString(file));
        String imagebase64Str  = StringUtils.newStringUtf8(org.apache.commons.codec.binary.Base64.encodeBase64(imageData, false));
        indexImageVO.setMy_img(imagebase64Str);
        IndexResponse indexResponseVO = imageService.index(name, item, indexImageVO);
        return indexResponseVO;
    }

    @RequestMapping(value = "query/{index}/{from}/{size}/{q}",method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Response a list describing all of es-image that is successfully get or not."
            ,notes = "e.g. index: my_index,from: 0,size: 10,q: *:*")
    public SearchResponseVO queryIndex(@PathVariable("index") String index,@PathVariable("from") int from,@PathVariable("size") int size,@PathVariable("q") String query) {
        return imageService.query(index,from,size,query);
    }

    @RequestMapping(method = RequestMethod.POST, value = "indexZip/{name}/{item}/", consumes = MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing zipped pictures is successfully uploaded for indexing or not."
            , notes = "e.g. name: my_index,item: my_image_item,key(fixed): my_img")
    public
    @ResponseBody
    List<IndexResponse> indexZip(
            @RequestPart(value = "file") @Valid @NotNull @NotBlank MultipartFile file,
            @PathVariable("name") String name, @PathVariable("item") String item) throws Exception {
        IndexImageVO indexImageVO = new IndexImageVO();
        List<IndexResponse>  indexResponses= new ArrayList<IndexResponse>();
        if (!file.isEmpty()) {
            ZipUploadVO zipResult = this.unZipIt(file, FileUtil.getUploads(false));
            LOG.info("upload zipResult:"+zipResult.toString());
            //for loop indexing detail files:
            for(File detailFile : zipResult.getDetails()) {
                indexImageVO.setMy_img(this.getImageDataString(detailFile));
                indexResponses.add( imageService.index(name, item, indexImageVO) );
            }
        } else {
            LOG.error("You failed to upload " + file.getName() + " because the file was empty.");
        }
        return indexResponses;
    }

    private String getMultipartImageDataString(MultipartFile file) {
        String imageDataString = null;
        if (!file.isEmpty()) {
            LOG.info("uploaded file:" + file.toString());
            try {
                // Reading a Image file from file system
//                MultipartFile  file;
                byte[] byteArr = file.getBytes();
                InputStream imageInFile = new ByteArrayInputStream(byteArr);
//            FileInputStream imageInFile = new FileInputStream(file);
                byte imageData[] = new byte[byteArr.length];
                imageInFile.read(imageData);

                // Converting Image byte array into Base64 String
                imageDataString = encodeImage(imageData);
                LOG.debug("Image Successfully Manipulated!base64:" + imageDataString);
                IndexImageVO indexImageVO = new IndexImageVO(imageDataString);
                LOG.debug("indexImageVO:" + indexImageVO.toString());
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
    private String getImageDataString(File file) {
        String imageDataString = null;
        if (file.isFile()) {
            LOG.info("uploaded file:" + file.toString());
            try {
                // Reading a Image file from file system
            FileInputStream imageInFile = new FileInputStream(file);
                byte imageData[] = new byte[(int)file.length()];
                imageInFile.read(imageData);
                // Converting Image byte array into Base64 String
                imageDataString = encodeImage(imageData);
                LOG.info("Image Successfully Manipulated!base64:" + imageDataString);
                IndexImageVO indexImageVO = new IndexImageVO(imageDataString);
                LOG.info("indexImageVO:" + indexImageVO.toString());
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

    public ZipUploadVO unZipIt(MultipartFile file, String outputFolder) throws IOException {

        Map<String, String> suffixMap = new HashMap<String, String>();//记录文件后辍名
        byte[] buffer = new byte[1024000];// BufferSize
        String fullFileName = null;
        ZipUploadVO results = new ZipUploadVO();
        //
        byte[] bytes = file.getBytes();
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        final String fileNameAppendix
                = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date());

        fullFileName = fileNameAppendix + "." + fileExt;

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fullFileName)));
        stream.write(bytes);
        stream.close();
        LOG.info("Upload (zip)file success." + fullFileName);

        // create output directory is not exists
        File folder = new File(outputFolder + File.separator + fileNameAppendix);
        if (!folder.exists()) {
            folder.mkdir();
        }
        List<File> detailfiles = new ArrayList<File>();
        File zFile = new File(fullFileName);
        ZipFile zip = new ZipFile(zFile);
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.getEntries();
        while (entries.hasMoreElements()) {
            ZipEntry ze = entries.nextElement();
            if (ze.isDirectory()) {
            } else {
                InputStream is = null;
                FileOutputStream fos = null;
                if (!ze.getName().startsWith("_") && !ze.getName().startsWith(".")) {

                    String fileName = ze.getName();

                    String[] sStr = fileName.split("\\.");
                    suffixMap.put(sStr[0], sStr[1]);

                    File newFile = new File(
                            outputFolder + File.separator + fileNameAppendix + File.separator + fileName);
//
                    LOG.debug("file unzip : " + newFile.getAbsoluteFile());

                    detailfiles.add(newFile);
                    results.setDetails(detailfiles);
  //
                    new File(newFile.getParent()).mkdirs();
                    is = zip.getInputStream(ze);
                    fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                }
            }
        }
        return results;
    }
}