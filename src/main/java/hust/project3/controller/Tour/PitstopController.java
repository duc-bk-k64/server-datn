package hust.project3.controller.Tour;

import hust.project3.common.Constant;
import hust.project3.model.ResponMessage;
import hust.project3.model.Tour.PitStopModel;
import hust.project3.model.Tour.PitstopUpdate;
import hust.project3.service.Tour.PitstopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.API.PREFIX)
@PreAuthorize("hasRole('ROLE_STAFF')")
public class PitstopController {
    @Autowired
    private PitstopService pitstopService;

    @PostMapping("/pitstop/create")
    @ResponseBody
    public ResponMessage create(@RequestBody List<PitStopModel> pitStopModels, @RequestParam Long tourId) {
        return  pitstopService.createList(pitStopModels,tourId);
    }

    @GetMapping("/pitstop/findByTourId")
    @ResponseBody
    public ResponMessage findByTourId(@RequestParam Long tourId) {
        return pitstopService.findByTourId(tourId);
    }

    @PutMapping("/pitstop/updateList")
    @ResponseBody
    public ResponMessage updateList(@RequestBody PitstopUpdate data, @RequestParam Long tourId) {
        return pitstopService.updateList(data.getPitStopModels(),tourId,data.getDeletePitstopId());
    }
}
