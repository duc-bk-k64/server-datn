package hust.project3.controller.Tour;

import hust.project3.common.Constant;
import hust.project3.entity.Tour.FeedBack;
import hust.project3.model.ResponMessage;
import hust.project3.service.Tour.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin
@RequestMapping(Constant.API.PREFIX)
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/feedback/create")
    @ResponseBody
    public ResponMessage create(@RequestParam Long tripId, @RequestBody FeedBack feedBack) {
        return feedbackService.create(tripId,feedBack);
    }

    @GetMapping("/feedback/findByTourId")
    @ResponseBody
    public  ResponMessage findByTourId(@RequestParam Long tourId) {
        return feedbackService.findByTourId(tourId);
    }

    @DeleteMapping("/feedback/delete")
    @ResponseBody
    public  ResponMessage delete(@RequestParam Long id) {
        return feedbackService.delete(id);
    }
}
