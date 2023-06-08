package hust.project3.model.Post;

import java.util.List;

import hust.project3.entity.Post.Paragraph;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParagraphUpdate {
	private List<Long> deleteId;
	private List<Paragraph> update;

}
