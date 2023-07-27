package hust.project3.controller.Tour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hust.project3.common.Constant;
import hust.project3.model.ResponMessage;
import hust.project3.service.Tour.DepartureService;

@RestController
@RequestMapping(Constant.API.PREFIX)
@PreAuthorize("hasRole('ROLE_STAFF')")
public class DepartureController {
	@Autowired
	private DepartureService departureService;
	
	@PostMapping("/departure/create")
	@ResponseBody
	public ResponMessage create(@RequestBody String name) {
		return departureService.create(name);
	}
	

}
