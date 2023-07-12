package hust.project3.controller.Post;

import java.util.List;

import javax.crypto.spec.DHPublicKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import hust.project3.common.Constant;
import hust.project3.entity.Post.Paragraph;
import hust.project3.model.ResponMessage;
import hust.project3.model.Post.ParagraphUpdate;
import hust.project3.service.Post.ParagraphService;

@RestController
//@CrossOrigin
@RequestMapping(Constant.API.PREFIX)
@PreAuthorize("hasRole('ROLE_STAFF')")
public class ParagraphController {
	@Autowired
	private ParagraphService paragraphService;
	
	@PostMapping("/paragraph/create")
	@ResponseBody
	public ResponMessage create(@RequestBody List<Paragraph> paragraphs, @RequestParam Long postId) {
		return paragraphService.createList(postId, paragraphs);
				
	}
	@GetMapping("/paragraph/findByPostId")
	@ResponseBody
	public ResponMessage findByPostId(@RequestParam  Long postId) {
		return paragraphService.findByPostId(postId);
	}
	
	@PostMapping("/paragraph/update")
	@ResponseBody
	public ResponMessage update(@RequestBody ParagraphUpdate data,@RequestParam Long postId) {
		return paragraphService.updateList(postId,data.getDeleteId(), data.getUpdate());
	}
	

}
