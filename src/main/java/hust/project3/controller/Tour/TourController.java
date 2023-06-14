package hust.project3.controller.Tour;

import hust.project3.model.Tour.TourModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import hust.project3.common.Constant;
import hust.project3.model.ResponMessage;
import hust.project3.model.Tour.TourCreate;
import hust.project3.service.Tour.TourService;

@RestController
@RequestMapping(Constant.API.PREFIX)
public class TourController {
	@Autowired
	private TourService tourService;

	@PreAuthorize("hasRole('ROLE_STAFF')")
	@PostMapping("/tour/create")
	@ResponseBody
	public ResponMessage create(@RequestBody TourCreate data) {
		return tourService.create(data.getDesId(), data.getTour().toObject());
	}

	@PreAuthorize("hasRole('ROLE_STAFF')")
	@GetMapping("/tour/findAll")
	@ResponseBody
	public ResponMessage findAll() {
		return tourService.findAll();
	}

	@PutMapping("/tour/open")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	@ResponseBody
	public  ResponMessage openTour(@RequestParam Long tourId) {
		return tourService.openTour(tourId);
	}

	@PutMapping("/tour/update")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	@ResponseBody
	public  ResponMessage updateTour(@RequestParam Long tourId, @RequestBody TourModel  data) {
		return tourService.update(data.toObject(),tourId);
	}

	@DeleteMapping("/tour/delete")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	@ResponseBody
	public  ResponMessage deleteTour(@RequestParam Long tourId) {
		return tourService.deleteById(tourId);
	}

	@PutMapping("/tour/close")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	@ResponseBody
	public  ResponMessage closeTour(@RequestParam Long tourId) {
		return tourService.closeTour(tourId);
	}
	

}
