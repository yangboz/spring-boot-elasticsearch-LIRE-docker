package info.smartkit.eip.obtuse_octo_prune.controllers;

import com.wordnik.swagger.annotations.ApiOperation;
import info.smartkit.eip.obtuse_octo_prune.VOs.MappingVO;
import info.smartkit.eip.obtuse_octo_prune.VOs.SettingsVO;
import info.smartkit.eip.obtuse_octo_prune.daos.MovieRepository;
import info.smartkit.eip.obtuse_octo_prune.domains.Movie;
import info.smartkit.eip.obtuse_octo_prune.services.ImageService;
import info.smartkit.eip.obtuse_octo_prune.services.MovieServiceItf;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

/**
 * Created by smartkit on 2016/10/28.
 */
@RestController
@RequestMapping("/es/image")
public class ImageController {

    @Autowired
private ImageService imageService;

    private static Logger LOG = LogManager.getLogger(MovieController.class);

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Movie info is successfully created or not.")
//    public Map<String, Float> create(@RequestBody @Valid Map<String, Float> value) {
    public void search(@RequestBody Movie value) {

    }

    //
    @RequestMapping(value = "setting/{index}", method = RequestMethod.PUT)
    @ApiOperation(httpMethod = "PUT", value = "Response a string describing if the el_index  is successfully updated or not.",notes = "e.g. el_index")
    public void setting(@PathVariable("index") String index, @RequestBody SettingsVO value) {
        imageService.setting(index,value);

    }

    @RequestMapping(value = "mapping/{index}/item/{item}", method = RequestMethod.PUT)
    @ApiOperation(httpMethod = "PUT", value = "Response a string describing if the el_index/el_image_item  is successfully updated or not.",notes = "e.g.index:el_index,item:el_image_item,type:image,hash:BIT_SAMPLING,LSH,store:yes")
    public void mapping(@PathVariable("index") String index,@PathVariable("item") String item, @RequestBody MappingVO value) {
        imageService.mapping(index,item,value);
    }
}
