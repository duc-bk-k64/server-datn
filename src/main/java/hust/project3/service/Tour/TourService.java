package hust.project3.service.Tour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hust.project3.model.Tour.TourModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.project3.Utils.GenerateCode;
import hust.project3.common.Constant;
import hust.project3.entity.Tour.Destination;
import hust.project3.entity.Tour.Tour;
import hust.project3.model.ResponMessage;
import hust.project3.repository.Tour.TourDestinationRepository;
import hust.project3.repository.Tour.TourRepository;

@Service
public class TourService {
	@Autowired
	private TourRepository tourRepository;
	@Autowired
	private TourDestinationRepository tourDestinationRepository;
	
	public ResponMessage create(List<Long> desId, Tour tour) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Set<Destination> destinations = new HashSet<>();
			desId.forEach(id-> {
				destinations.add(tourDestinationRepository.findDesById(id));
			});
			tour.setDestinations(destinations);
			tour.setStatus(Constant.STATUS.AVAILABLE);
			String code = GenerateCode.generateCode();
			while(tourRepository.existsByCode(code)) {
				code = GenerateCode.generateCode();
			}
			tour.setCode(code);
			tourRepository.save(tour);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(tour.toModel());
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	
	public ResponMessage findAll() {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			ArrayList<TourModel> tourModels = new ArrayList<>();
			tourRepository.findAll().forEach(e -> {
				tourModels.add(e.toModel());
			});
			responMessage.setData(tourModels);
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage update(Tour tour, Long tourId) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Tour currentTour = tourRepository.findTourById(tourId);
			currentTour.setDeparture(tour.getDeparture());
			currentTour.setImageUrl(tour.getImageUrl());
			currentTour.setName(tour.getName());
			currentTour.setNumberOfDay(tour.getNumberOfDay());
			currentTour.setNumberOfNight(tour.getNumberOfNight());
			tourRepository.save(currentTour);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(currentTour.toModel());
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage openTour( Long tourId) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Tour currentTour = tourRepository.findTourById(tourId);
			currentTour.setStatus(Constant.STATUS.AVAILABLE);
			tourRepository.save(currentTour);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(currentTour.toModel());
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage closeTour( Long tourId) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Tour currentTour = tourRepository.findTourById(tourId);
			currentTour.setStatus(Constant.STATUS.UNAVAILABLE);
			tourRepository.save(currentTour);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(currentTour.toModel());
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	public ResponMessage findById(Long id) {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(tourRepository.findTourById(id).toModel());
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

}
