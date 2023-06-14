package hust.project3.controller.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import hust.project3.common.Constant;
import hust.project3.entity.Post.Post;
import hust.project3.model.ResponMessage;
import hust.project3.service.Post.PostService;

@RestController
@RequestMapping(Constant.API.PREFIX)
@PreAuthorize("hasRole('ROLE_STAFF')")
public class PostController {
	@Autowired
	private PostService postService;
	
	@PostMapping("/post/create")
	@ResponseBody
	public ResponMessage create(@RequestBody Post post,@RequestParam Long desId) {
		return postService.create(post,desId);
	}
	@PutMapping("/post/update")
	@ResponseBody
	public ResponMessage update(@RequestParam Long id, @RequestBody Post post) {
		return postService.update(id, post);
	}
	@GetMapping("/post/findAll")
	@ResponseBody
	public ResponMessage findAll() {
		return  postService.findAll();
	}
	
	@GetMapping("/post/findByDestiantion")
	@ResponseBody
	public ResponMessage findByDestination(@RequestParam Long  desId) {
		return postService.findByDestination(desId);
	}
	
	@GetMapping("/post/available")
	@ResponseBody
	public ResponMessage available(@RequestParam Long  id) {
		return postService.available(id);
	}
	@GetMapping("/post/unavailable")
	@ResponseBody
	public ResponMessage unavailable(@RequestParam Long  id) {
		return postService.unavailable(id);
	}

	@GetMapping("/post/findDes")
	@ResponseBody
	public ResponMessage findDesByPostId(@RequestParam Long  id) {
		return postService.findDesByPostId(id);
	}
	@DeleteMapping ("/post/delete")
	@ResponseBody
	public ResponMessage deleteByPostId(@RequestParam Long  id) {
		return postService.deleteById(id);
	}


}
