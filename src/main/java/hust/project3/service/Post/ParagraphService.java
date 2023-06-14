package hust.project3.service.Post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.project3.common.Constant;
import hust.project3.entity.Post.Paragraph;
import hust.project3.entity.Post.Post;
import hust.project3.model.ResponMessage;
import hust.project3.repository.Post.ParagraphRepository;
import hust.project3.repository.Post.PostRepository;

@Service
public class ParagraphService {
	@Autowired
	private ParagraphRepository paragraphRepository;
	@Autowired
	private PostRepository postRepository;
	
	public ResponMessage createList(Long postId,List<Paragraph> paragraphs) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Post post = postRepository.findPostById(postId);
			if(post == null) {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage("Post not found");
			} else {
				paragraphs.forEach(e -> {
					e.setPost(post);
					paragraphRepository.save(e);
				});
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
				responMessage.setData(paragraphs);
			}
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	public ResponMessage findByPostId(Long postId) {
		ResponMessage responMessage = new ResponMessage();
		try {
			
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
				responMessage.setData(paragraphRepository.findByPostId(postId));
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	
	public ResponMessage updateList(Long postId,List<Long> deleteId,List<Paragraph> update) {
		ResponMessage responMessage = new ResponMessage();
		try {
//			delete
			
			deleteId.forEach(id -> {
				paragraphRepository.deleteById(id);
			});
//			update paragraph
			Post post = postRepository.findPostById(postId);
			update.forEach(paragraph -> {
			
					paragraph.setPost(post);
					paragraphRepository.save(paragraph);
				
			});
			
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(update);
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
		
	}
	
	

}