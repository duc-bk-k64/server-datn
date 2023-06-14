package hust.project3.controller.Tour;

import hust.project3.common.Constant;
import hust.project3.entity.Tour.TourTrip;
import hust.project3.model.ResponMessage;
import hust.project3.model.Tour.TourTripModel;
import hust.project3.service.Tour.TourTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.API.PREFIX)
public class TourTripController {
    @Autowired
    private TourTripService tourTripService;

    @PostMapping("/trip/create")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    @ResponseBody
    public ResponMessage create(@RequestBody List<TourTripModel> tourTrip, @RequestParam Long tourId) {
        return tourTripService.createList(tourTrip,tourId);
    }

    @GetMapping("/trip/findByTourId")
    @ResponseBody
    public  ResponMessage findByTourId(@RequestParam Long tourId) {
        return tourTripService.findByToudId(tourId);
    }
    @GetMapping("/trip/findAll")
    @ResponseBody
    public  ResponMessage findAll() {
        return tourTripService.findByAll();
    }

    @GetMapping("/trip/findByTourGuide")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_TOURGUIDE')")
    public  ResponMessage findByTourGuide(@RequestParam String username) {
        return tourTripService.findByTourGuideUsername(username);
    }
    @GetMapping("/trip/addTourguide")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public  ResponMessage addTourguide(@RequestParam String username,@RequestParam Long tripId) {
        return tourTripService.addTourguide(tripId,username);
    }


    @PutMapping("/trip/update")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    @ResponseBody
    public  ResponMessage update(@RequestParam Long id, @RequestBody TourTripModel tourTripModel) {
        return tourTripService.update(id,tourTripModel);
    }
    @DeleteMapping("/trip/delete")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    @ResponseBody
    public  ResponMessage delete(@RequestParam Long id) {
        return tourTripService.delete(id);
    }

    @GetMapping("/trip/findByAccount")
    @ResponseBody
    public  ResponMessage findByAccount(@RequestParam String username) {
        return tourTripService.findByUsername(username);
    }

    @PostMapping("/trip/addToAccount")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    @ResponseBody
    public ResponMessage addToAccount( @RequestParam String tripCode,@RequestParam String username) {
        return tourTripService.addTourTripToAccount(username,tripCode);
    }

    @GetMapping("/trip/findTripFeedback")
    @ResponseBody
    public  ResponMessage findTripNeedFeedback(@RequestParam String username) {
        return tourTripService.findTripFeedback(username);
    }

    @GetMapping("/trip/start")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TOURGUIDE')")
    public  ResponMessage startTrip(@RequestParam Long id) {
        return tourTripService.start(id);
    }

    @PreAuthorize("hasRole('ROLE_TOURGUIDE')")
    @GetMapping("/trip/finish")
    @ResponseBody
    public  ResponMessage finishTrip(@RequestParam Long id) {
        return tourTripService.finish(id);
    }

    @GetMapping("/trip/findPitstopStatus")
    @ResponseBody
    public  ResponMessage findPitstopStatus(@RequestParam String tripCode) {
        return tourTripService.findPitstopStatus(tripCode);
    }
    @PreAuthorize("hasRole('ROLE_TOURGUIDE')")
    @GetMapping("/trip/pitstop/confirm")
    @ResponseBody
    public  ResponMessage confirmPitstop(@RequestParam Long tripPitstopId) {
        return tourTripService.confirmPitstop(tripPitstopId);
    }
}
