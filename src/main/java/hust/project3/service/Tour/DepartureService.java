package hust.project3.service.Tour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.project3.common.Constant;
import hust.project3.entity.Tour.Departure;
import hust.project3.entity.Tour.Destination;
import hust.project3.model.ResponMessage;
import hust.project3.repository.Tour.DepartureRepository;

@Service
public class DepartureService {
	@Autowired
	private DepartureRepository departureRepository;

	public ResponMessage create(String name) {
		ResponMessage responMessage = new ResponMessage();
		try {
			if (!departureRepository.existsByName(name)) {
				Departure departure = new Departure();
				departure.setName(name);
				departure.setValue(name);
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
				responMessage.setData(departureRepository.save(departure));
			} else {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage("Điểm khởi hành đã tồn tại");
			}
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
			responMessage.setData(departureRepository.findAll());
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

}
