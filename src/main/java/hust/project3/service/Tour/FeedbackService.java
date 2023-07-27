package hust.project3.service.Tour;


import hust.project3.common.Constant;
import hust.project3.entity.Tour.FeedBack;
import hust.project3.entity.Tour.Tour;
import hust.project3.entity.Tour.TourTrip;
import hust.project3.model.ResponMessage;
import hust.project3.model.Tour.FeedbackModel;
import hust.project3.repository.Tour.FeedbackRepository;
import hust.project3.repository.Tour.TourRepository;
import hust.project3.repository.Tour.TourTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourTripRepository tourTripRepository;

    public ResponMessage create(Long tourTripId, FeedBack  feedBack) {
        ResponMessage responMessage = new ResponMessage();
        try {
            TourTrip tourTrip = tourTripRepository.findTripById(tourTripId);
            if(tourTrip == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("TourTrip not found");
            } else  {
                if(tourTrip.getStatus().equals(Constant.STATUS.FINISH)) {
                    Tour tour = tourTrip.getTour();
                    feedBack.setTour(tour);
                    responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                    responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                    feedBack = feedbackRepository.save(feedBack);
                    responMessage.setData(feedBack);
                    FeedbackModel feedbackModel = new FeedbackModel();
                    feedbackModel.setId(feedBack.getId());
                    feedbackModel.setContent(feedBack.getContent());
                    kafkaTemplate.send("feedbackDATN",feedbackModel);
                }
                else {
                    responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                    responMessage.setMessage("TourTrip not finish");
                }

            }
        }
        catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }
    public ResponMessage findByTourId(Long tourId) {
        ResponMessage responMessage = new ResponMessage();
        try {
            Tour tour = tourRepository.findTourById(tourId);
            if(tour == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("Tour not found");
            } else  {
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                responMessage.setData(feedbackRepository.findByTourId(tourId));
            }
        }
        catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage delete(Long id) {
        ResponMessage responMessage = new ResponMessage();
        try {
                feedbackRepository.deleteById(id);
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
        }
        catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }
//    @KafkaListener(id = "notification", topics = "feedbackDATN")
//    public void checkFeedback(FeedbackModel feedBack) {
//        System.out.println(feedBack.getContent());
//    }

}
