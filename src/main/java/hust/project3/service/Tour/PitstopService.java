package hust.project3.service.Tour;

import hust.project3.common.Constant;
import hust.project3.entity.Tour.PitStop;
import hust.project3.entity.Tour.Tour;
import hust.project3.model.ResponMessage;
import hust.project3.model.Tour.PitStopModel;
import hust.project3.repository.Tour.PitStopRepository;
import hust.project3.repository.Tour.TourRepository;
import hust.project3.repository.Tour.TourTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//@CacheConfig(cacheNames = {"pitstop"})
public class PitstopService {
    @Autowired
    private PitStopRepository pitStopRepository;
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourTripRepository tourTripRepository;
//    @CacheEvict(key = "#tourId+'_pitstop'", value = "responMessage")
    public ResponMessage createList(List<PitStopModel> pitStops, Long tourId) {
        ResponMessage responMessage = new ResponMessage();
        try {
            Tour tour = tourRepository.findTourById(tourId);
            if(tour == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("Not found tour");
            } else {
                pitStops.forEach(e -> {
                    PitStop pitStop = e.toObject();
                    pitStop.setTour(tour);
                    pitStopRepository.save(pitStop);
                    e.setId(pitStop.getId());
                });
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                responMessage.setData(pitStops);
            }
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }
//    @Cacheable(value = "responMessage", key = "#tourId+'_pitstop'")
    public ResponMessage findByTourId(Long tourId) {
        ResponMessage responMessage = new ResponMessage();
        try {

                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                List<PitStopModel> pitStopModels = new ArrayList<>();
                pitStopRepository.findPitStopById(tourId).forEach(e -> {
                    pitStopModels.add(e.toModel());
                });
                responMessage.setData(pitStopModels);
//                System.out.println("no cache");
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }
//    @CacheEvict(key = "#tourId+'_pitstop'", value = "responMessage")
    public ResponMessage updateList(List<PitStopModel> pitStops, Long tourId,List<Long> deleteId) {
        ResponMessage responMessage = new ResponMessage();
        try {
            Tour tour = tourRepository.findTourById(tourId);
            if(tour == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("Not found tour");
            } else {
                deleteId.forEach(id -> {
                    pitStopRepository.deleteById(id);
                });
//                 update
                pitStops.forEach( e -> {
                    PitStop pitStop = e.toObject();
                    pitStop.setTour(tour);
                    pitStopRepository.save(pitStop);
                });


                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                responMessage.setData(tour.getPitStops());
            }
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage confirmPitStop(Long tripId, Long pitstopId) {
        ResponMessage responMessage = new ResponMessage();
        try {

            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);


        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

}
