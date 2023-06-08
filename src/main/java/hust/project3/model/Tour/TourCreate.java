package hust.project3.model.Tour;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourCreate {
	private List<Long> desId;
	private TourModel tour;

}
