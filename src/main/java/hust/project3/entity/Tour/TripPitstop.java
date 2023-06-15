package hust.project3.entity.Tour;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TripPitstop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private  String tripCode;
    @Column
    private  Long pitstopId;
    @Column
    private  String status;
    @Column
    private  String note;
}
