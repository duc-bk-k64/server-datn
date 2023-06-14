package hust.project3.service.Tour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import hust.project3.entity.Tour.TourTrip;
import hust.project3.model.Tour.FindTourModel;
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
	public ResponMessage findAllAvailable() {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			ArrayList<TourModel> tourModels = new ArrayList<>();
			tourRepository.findTourAvailable().forEach(e -> {
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

	public ResponMessage deleteById(Long id) {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			Tour tour =tourRepository.findTourById(id);
			if(tour.getTourTrips().size() ==0 && tour.getPitStops().size() ==0 ) {
				tour.setDestinations(null);
				tourRepository.save(tour);
				Thread.sleep(1000);
				tourRepository.deleteById(id);
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			}
			else {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage(Constant.MESSAGE.ERROR);
			}
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage findTour(FindTourModel data) {
		ResponMessage responMessage = new ResponMessage();
		try {
			String[] time = data.getTime().split("-");
			int minTime = Integer.valueOf(time[0]);
			int maxTime = Integer.valueOf(time[1]);
			String[] price = data.getPrice().split("-");
			long minPrice = Long.parseLong(price[0]);
			long maxPrice = Long.parseLong(price[1]);

			List<Tour> tourList = tourRepository.findTour(data.getDeparture(), minTime,maxTime);
			List<TourModel> selectedTour = new ArrayList<>();
			Destination destination = tourDestinationRepository.findDesById(data.getDestination());
			for(int i = 0; i<tourList.size() ; i++) {
				boolean isAdd = false;
				if(tourList.get(i).getDestinations().contains(destination)) {
					Set<TourTrip> tourTrip = tourList.get(i).getTourTrips();
					for(TourTrip trip : tourTrip){
						if(trip.getPrice() >= minPrice & trip.getPrice() <= maxPrice & !isAdd ) {
							selectedTour.add(tourList.get(i).toModel());
							isAdd = true;
						}
					}

				}
			}

			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(selectedTour);

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return  responMessage;


	}

}
