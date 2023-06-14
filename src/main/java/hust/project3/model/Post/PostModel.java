package hust.project3.model.Post;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
public class PostModel {
    private Long id;
    private String title;
    private String imageUrl;
    private String timeCreated;
    private String createdBy;
    private String status;
}
