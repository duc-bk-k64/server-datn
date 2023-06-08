package hust.project3.model.Tour;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PitstopUpdate {
    private List<Long> deletePitstopId;
    private  List<PitStopModel> pitStopModels;
}
