package hust.project3.service.BookTour;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import hust.project3.Utils.GenerateCode;
import hust.project3.entity.Tour.TourTrip;
import hust.project3.service.Tour.TourTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;

import hust.project3.common.Constant;
import hust.project3.entity.Account;
import hust.project3.entity.BookTour.BookTour;
import hust.project3.model.BookTourModel;
import hust.project3.model.ResponMessage;
import hust.project3.repository.AccountRepository;
import hust.project3.repository.BookTour.BookTourRepository;

@Service
public class BookTourService {
	@Autowired
	private BookTourRepository bookTourRepository;
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TourTripService tourTripService;

	public ResponMessage create(String username, BookTour bookTour) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Account account = accountRepository.findUserByUsername(username);
			TourTrip tourTrip = tourTripService.findByTripCode(bookTour.getTourTripCode());
			Long totalMoney = tourTrip.getPrice()*bookTour.getNumberOfAdjust() + tourTrip.getPriceForChidren()*bookTour.getNumberOfChildren();
			bookTour.setStatus(Constant.STATUS.UN_CONFIMRED);
			String code = GenerateCode.generateCode();
			while (bookTourRepository.existsByCode(code)) {
				code =GenerateCode.generateCode();
			}
			bookTour.setCode(code);
			bookTour.setTimeCreate(Instant.now());
			bookTour.setMoneyToPay(totalMoney);
			bookTour.setAccount(account);
			bookTourRepository.save(bookTour);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(bookTour.toModel());
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage update(Long id, BookTour bookTour) {
		ResponMessage responMessage = new ResponMessage();
		try {
			BookTour bookTour2 = bookTourRepository.findBookTourById(id);
			if (bookTour2 == null) {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage("Not found booktour");
			} else {
				bookTour2.setEmail(bookTour.getEmail());
				bookTour2.setName(bookTour.getName());
				bookTourRepository.save(bookTour2);
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
				responMessage.setData(bookTour2.toModel());
			}
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage getByUsername(String username) {
		ResponMessage responMessage = new ResponMessage();
		try {
			Account account = accountRepository.findUserByUsername(username);
			ArrayList<BookTourModel> bookTourList = new ArrayList<>();
		    account.getBookTours().forEach(e -> {
				bookTourList.add(e.toModel());
			});
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(bookTourList);
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage getAll() {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			ArrayList<BookTourModel> bookTours = new ArrayList<>();
			bookTourRepository.findAll().forEach(e -> {
				bookTours.add(e.toModel());
			});
			responMessage.setData(bookTours);

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

	public ResponMessage deleteById(Long id) {
		ResponMessage responMessage = new ResponMessage();
		try {
			bookTourRepository.deleteById(id);
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	
	public ResponMessage confirm(Long id) {
		ResponMessage responMessage = new ResponMessage();
		try {
			BookTour bookTour = bookTourRepository.findBookTourById(id);
			if (bookTour == null) {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage("Not found booktour");
			} else {
			    bookTour.setStatus(Constant.STATUS.CONFIMRED);
				bookTourRepository.save(bookTour);
//		send notification
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
				responMessage.setData(bookTour.toModel());
			}
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	public ResponMessage paid(Long id) {
		ResponMessage responMessage = new ResponMessage();
		try {
			BookTour bookTour = bookTourRepository.findBookTourById(id);
			if (bookTour == null) {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage("Not found booktour");
			} else {
			    bookTour.setStatus(Constant.STATUS.PAID);
				bookTourRepository.save(bookTour);
				tourTripService.addTourTripToAccount(bookTour.getAccount().getUsername(),bookTour.getTourTripCode());
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
				responMessage.setData(bookTour.toModel());
//				send notification
			}
		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}
	
	public ResponMessage findByTourTripCode(String code) {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			ArrayList<BookTourModel> bookTours = new ArrayList<>();
			bookTourRepository.findByTourTripCode(code).forEach(e -> {
				bookTours.add(e.toModel());
			});
			responMessage.setData(bookTours);

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	}

}
