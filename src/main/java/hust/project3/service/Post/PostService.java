package hust.project3.service.Post;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.project3.common.Constant;
import hust.project3.entity.Post.Post;
import hust.project3.entity.Tour.Destination;
import hust.project3.model.ResponMessage;
import hust.project3.repository.Post.PostRepository;
import hust.project3.repository.Tour.TourDestinationRepository;
@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private TourDestinationRepository tourDestinationRepository;
	
	public ResponMessage create(Post post, Long desId) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Destination destination = tourDestinationRepository.findDesById(desId);
			post.setTimeCreated(new Date());
			post.setStatus(Constant.STATUS.AVAILABLE);
			post.setDestination(destination);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(postRepository.save(post));
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	public ResponMessage update(Long id, Post post) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Post oldPost = postRepository.findPostById(id);
			oldPost.setTitle(post.getTitle());
			oldPost.setImageUrl(post.getImageUrl());
//			oldPost.setStatus(oldPost.getStatus());
			oldPost.setTimeCreated(new Date());
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(postRepository.save(oldPost));
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	
	public ResponMessage findAll() {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(postRepository.findAll());
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	
	public ResponMessage findByDestination(Long id) {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(postRepository.findPostByDestinationId(id));
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	
	public ResponMessage unavailable(Long id) {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			Post post = postRepository.findPostById(id);
			post.setStatus(Constant.STATUS.UNAVAILABLE);
			responMessage.setData(postRepository.save(post));
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
		
	}
	public ResponMessage available(Long id) {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			Post post = postRepository.findPostById(id);
			post.setStatus(Constant.STATUS.AVAILABLE);
			responMessage.setData(postRepository.save(post));
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
		
	}
	
	
	

}
