package hust.project3.controller.BookTour;

import hust.project3.model.BookTourModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import hust.project3.common.Constant;
import hust.project3.entity.BookTour.BookTour;
import hust.project3.model.ResponMessage;
import hust.project3.service.BookTour.BookTourService;

//@CrossOrigin
@RestController
@RequestMapping(Constant.API.PREFIX)
public class BookTourController {
	@Autowired
	private BookTourService bookTourService;
	
	@PostMapping("/booktour/create")
	@ResponseBody
	public ResponMessage create(@RequestParam String username,@RequestBody BookTour bookTour) {
		return bookTourService.create(username,bookTour );
	}
	
	
	@PutMapping("/booktour/update")
	@ResponseBody
	public ResponMessage update(@RequestParam Long id, @RequestBody BookTourModel bookTour) {
		return bookTourService.update(id, bookTour.toObject());
	}
	
	@PreAuthorize("hasRole('ROLE_STAFF')")
	@PutMapping("/booktour/confirm")
	@ResponseBody
	public ResponMessage confirm(@RequestParam Long id) {
		return bookTourService.confirm(id);
	}
	
	@PreAuthorize("hasRole('ROLE_STAFF')")
	@PutMapping("/booktour/paid")
	@ResponseBody
	public ResponMessage paid(@RequestParam Long id) {
		return bookTourService.paid(id);
	}
	
	
	
	@GetMapping("/booktour/findByUsername")
	@ResponseBody
	public ResponMessage findByUsername(@RequestParam String username) {
		return bookTourService.getByUsername(username);
	}
	
	@PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_TOURGUIDE')")
	@GetMapping("/booktour/findByTourTripCode")
	@ResponseBody
	public ResponMessage findByTourTripCode(@RequestParam String tourTripCode) {
		return bookTourService.findByTourTripCode(tourTripCode);
	}
	
	@PreAuthorize("hasRole('ROLE_STAFF')")
	@GetMapping("booktour/findAll")
	@ResponseBody
	public ResponMessage findAll() {
		return bookTourService.getAll();
	}

	@PreAuthorize("hasRole('ROLE_STAFF')")
	@GetMapping("booktour/totalOneDay")
	@ResponseBody
	public ResponMessage totalInOneDay() {
		return bookTourService.findAllInOneDay();
	}
	
	@PreAuthorize("hasRole('ROLE_STAFF')")
	@DeleteMapping("/booktour/deleteById")
	@ResponseBody
	public ResponMessage deleteById(@RequestParam Long id) {
		return bookTourService.deleteById(id);
	}
	

}
