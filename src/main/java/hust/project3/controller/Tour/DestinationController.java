package hust.project3.controller.Tour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import hust.project3.common.Constant;
import hust.project3.entity.Tour.Destination;
import hust.project3.model.ResponMessage;
import hust.project3.service.Tour.DestinationService;

@RestController
//@CrossOrigin
@RequestMapping(Constant.API.PREFIX)
@PreAuthorize("hasRole('ROLE_STAFF')")
public class DestinationController {
	@Autowired
	private DestinationService destinationService;
	
	@PostMapping("/des/create")
	@ResponseBody
	public ResponMessage create(@RequestParam String name) {
		return destinationService.create(name);
	}
	@GetMapping("/des/findAll")
	@ResponseBody
	public ResponMessage findAll() {
		return destinationService.findAll();
	}
	@GetMapping("/des/findByTourId")
	@ResponseBody
	public ResponMessage findByTourId(@RequestParam Long tourId) {
		return destinationService.findByTourId(tourId);
	}

}
