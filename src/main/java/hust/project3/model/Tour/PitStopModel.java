package hust.project3.model.Tour;

import hust.project3.entity.Tour.PitStop;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
public class PitStopModel implements Serializable {
    private Long id;
    private String name;
    private String content;
    private String imageUrl;
    private int order;

    public PitStop toObject() {
        PitStop pitStop = new PitStop();
        pitStop.setId(id);
        pitStop.setContent(content);
        pitStop.setName(name);
        pitStop.setOrder(order);
        pitStop.setImageUrl(imageUrl);
        return pitStop;
    }
}
