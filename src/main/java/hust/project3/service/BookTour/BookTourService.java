package hust.project3.service.BookTour;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import hust.project3.Utils.GenerateCode;
import hust.project3.entity.Money.Bill;
import hust.project3.entity.Notification;
import hust.project3.entity.Tour.TourTrip;
import hust.project3.repository.Money.BillRepository;
import hust.project3.service.NotificationService;
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

	@Autowired
	private NotificationService notificationService;
	@Autowired
	private BillRepository billRepository;

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
//			send notification to staff
			Notification notification = new Notification();
			notification.setUsername("SYSTEM");
			notification.setTitle("Thông báo có đơn đặt tour mới");
			notification.setContent("Hệ thông Travel xin thông báo hệ thống có đơn đặt tour mới vỡi mã "+bookTour.getCode() +".Nhân viên vui lòng kiểm tra, liên hệ và xác nhận khách hàng. Xin trân trọng cảm ơn.");
			this.notificationService.sendToStaff(notification);
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
				bookTour2.setPhoneNumber(bookTour.getPhoneNumber());
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
			bookTourRepository.findAllBooktour().forEach(e -> {
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
			BookTour bookTour = bookTourRepository.findBookTourById(id);
			if(!bookTour.getStatus() .equals(Constant.STATUS.PAID)) {
				bookTourRepository.deleteById(id);
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			} else {
				responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
				responMessage.setMessage("Không thể xóa vì đơn đặt tour đã được thanh toán");
			}

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
				Notification notification = new Notification();
				notification.setUsername(bookTour.getAccount().getUsername());
				notification.setTitle("Thông báo xác nhận đặt tour");
				notification.setContent("Hệ thông Travel xin thông báo đơn đặt tour vỡi mã "+bookTour.getCode() +" đã được xác nhận. Qúy khách hàng vui lòng thanh toán để hoàn tất quy trình đặt tour. Xin trân trọng cảm ơn.");
				this.notificationService.sendNotifcationToUser(notification.getUsername(),notification);
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
				Notification notification = new Notification();
				notification.setUsername(bookTour.getAccount().getUsername());
				notification.setTitle("Thông báo thanh toán  đặt tour");
				notification.setContent("Hệ thông Travel xin thông báo đơn đặt tour vỡi mã "+bookTour.getCode() +" đã được thanh toán. Qúy khách hàng vui lòng thường xuyên kiểm tra điện thoại để nhận được hướng dẫn và thông tin chi tiết. Xin trân trọng cảm ơn.");
				this.notificationService.sendNotifcationToUser(notification.getUsername(),notification);
				tourTripService.addTourTripToAccount(bookTour.getAccount().getUsername(),bookTour.getTourTripCode());
				responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
				responMessage.setMessage(Constant.MESSAGE.SUCCESS);
				responMessage.setData(bookTour.toModel());
//				add bill to user
				Bill bill = new Bill();
				bill.setTotalMoney(bookTour.getMoneyToPay());
				bill.setStatus(Constant.STATUS.CONFIMRED);
				bill.setAccount(bookTour.getAccount());
				bill.setTimeCreated(Instant.now());
				bill.setContent("Thanh toán cho đơn đặt tour "+ bookTour.getCode()+" của quý khách đã được xác nhận.");
				String code = GenerateCode.generateCode();
				while (billRepository.existsByCode(code)) {
					code =GenerateCode.generateCode();
				}
				bill.setCode(code);
				billRepository.save(bill);
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
