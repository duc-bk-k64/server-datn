package hust.project3.service.Tour;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hust.project3.model.Tour.DestinationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.project3.common.Constant;
import hust.project3.entity.Post.Post;
import hust.project3.entity.Tour.Destination;
import hust.project3.model.ResponMessage;
import hust.project3.repository.Tour.TourDestinationRepository;

@Service
public class DestinationService {
	@Autowired
	private TourDestinationRepository tourDestinationRepository;
	
	public ResponMessage create(String name) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Destination destination = new Destination();
			destination.setName(name);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(tourDestinationRepository.save(destination));
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
			List<DestinationModel> destinationModels = new ArrayList<>();
			tourDestinationRepository.findAll().forEach( e -> {
				destinationModels.add(e.toModel());
			});

			responMessage.setData(destinationModels);
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	public ResponMessage findByTourId(Long tourId) {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			List<DestinationModel> destinationModels = new ArrayList<>();
			tourDestinationRepository.findByTourId(tourId).forEach(e -> {
				destinationModels.add(e.toModel());
			});
			responMessage.setData(destinationModels);
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

}
