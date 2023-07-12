package hust.project3.service.Tour;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import hust.project3.Utils.DateUtils;
import hust.project3.entity.Account;
import hust.project3.entity.BookTour.BookTour;
import hust.project3.entity.Money.Refund;
import hust.project3.entity.Money.Transaction;
import hust.project3.entity.Notification;
import hust.project3.entity.Tour.TripPitstop;
import hust.project3.model.AccountModel;
import hust.project3.model.Tour.TourTripInfor;
import hust.project3.model.Tour.TourTripModel;
import hust.project3.repository.AccountRepository;
import hust.project3.repository.BookTour.BookTourRepository;
import hust.project3.repository.Money.RefundRepository;
import hust.project3.repository.Money.TransactionRepository;
import hust.project3.repository.Tour.TripPitstopRepository;
import hust.project3.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.project3.Utils.GenerateCode;
import hust.project3.common.Constant;
import hust.project3.entity.Tour.Tour;
import hust.project3.entity.Tour.TourTrip;
import hust.project3.model.ResponMessage;
import hust.project3.repository.Tour.TourRepository;
import hust.project3.repository.Tour.TourTripRepository;

@Service
public class TourTripService {
    @Autowired
    private TourTripRepository tourTripRepository;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TripPitstopRepository tripPitstopRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BookTourRepository bookTourRepository;

    @Autowired
    private RefundRepository refundRepository;

    @Autowired private TransactionRepository transactionRepository;

    public ResponMessage createList(List<TourTripModel> tourTrips, Long tourId) {
        ResponMessage responMessage = new ResponMessage();
        try {
            Tour tour = tourRepository.findTourById(tourId);
            if (tour == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("Tour not found");
            } else {
                tourTrips.forEach(e -> {
                    String code = GenerateCode.generateCode();
                    while (tourTripRepository.existsByCode(code)) {
                        code = GenerateCode.generateCode();
                    }
                    try {
                        TourTrip tourTrip = e.toObject();
                        tourTrip.setCode(code);
                        tourTrip.setTour(tour);
                        tourTrip.setStatus(Constant.STATUS.AVAILABLE);
                        tourTripRepository.save(tourTrip);
                        tour.getPitStops().forEach(pitStop -> {
                            TripPitstop tripPitsop = new TripPitstop();
                            tripPitsop.setTripCode(tourTrip.getCode());
                            tripPitsop.setPitstopId(pitStop.getId());
                            tripPitsop.setStatus(Constant.STATUS.UN_CONFIMRED);
                            tripPitstopRepository.save(tripPitsop);
                        });
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }

                });
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                responMessage.setData(tourTrips);
            }
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage findByToudId(Long tourId) {
        ResponMessage responMessage = new ResponMessage();
        try {
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            ArrayList<TourTripModel> tourTripModels = new ArrayList<>();
            tourTripRepository.findByTourId(tourId).forEach(e -> {
                tourTripModels.add(e.toModel());
            });

            responMessage.setData(tourTripModels);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage findByAll() {
        ResponMessage responMessage = new ResponMessage();
        try {

            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            ArrayList<TourTripModel> tourTripModels = new ArrayList<>();
            tourTripRepository.findAll().forEach(e -> {
                tourTripModels.add(e.toModel());
            });

            responMessage.setData(tourTripModels);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage update(Long id, TourTripModel tourTripModel) {
        ResponMessage responMessage = new ResponMessage();
        try {
            TourTrip tourTrip = tourTripRepository.findTripById(id);
            if (tourTrip == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("TourTrip not found");
            }
            else if (tourTrip.getAccount().size() == 0) {
                tourTrip.setPriceForChidren(tourTripModel.getPriceForChidren());
                tourTrip.setPrice(tourTripModel.getPrice());
                tourTrip.setNote(tourTripModel.getNote());
                tourTrip.setDepartureDay(DateUtils.String2Date(tourTripModel.getDepartureDay()));
                if (tourTripModel.getStatus().equals(Constant.STATUS.AVAILABLE) || tourTripModel.getStatus().equals(Constant.STATUS.UNAVAILABLE))
                    tourTrip.setStatus(tourTripModel.getStatus());
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                responMessage.setData(tourTripRepository.save(tourTrip).toModel());
            } else  {
                tourTrip.setNote(tourTripModel.getNote());
                if (tourTripModel.getStatus().equals(Constant.STATUS.AVAILABLE) || tourTripModel.getStatus().equals(Constant.STATUS.UNAVAILABLE))
                    tourTrip.setStatus(tourTripModel.getStatus());
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                responMessage.setData(tourTripRepository.save(tourTrip).toModel());
            }
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }
    public ResponMessage addTourguide(Long id, String username) {
        ResponMessage responMessage = new ResponMessage();
        try {
            TourTrip tourTrip = tourTripRepository.findTripById(id);
            Account account = accountRepository.findUserByUsername(username);
            if (tourTrip == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("TourTrip not found");
            } else if (account ==null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage(Constant.MESSAGE.NOT_FOUND_USER);
            }
            else {
                tourTrip.setTourGuide(username);
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                responMessage.setData(tourTripRepository.save(tourTrip).toModel());
            }
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }
    public ResponMessage findByTourGuideUsername(String username) {
        ResponMessage responMessage = new ResponMessage();
        try {

            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            ArrayList<TourTripModel> tourTripModels = new ArrayList<>();
            tourTripRepository.findTripByTourGuide(username).forEach(e -> {
                tourTripModels.add(e.toModel());
            });

            responMessage.setData(tourTripModels);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage delete(Long id) {
        ResponMessage responMessage = new ResponMessage();
        try {
            TourTrip tourTrip = tourTripRepository.findTripById(id);
            if (tourTrip.getAccount().size() == 0) {
                tourTripRepository.delete(tourTrip);
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            } else {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("Can not remove tourtrip code "+tourTrip.getCode());
            }


        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage findByUsername(String username) {
        ResponMessage responMessage = new ResponMessage();
        try {
            Account account = accountRepository.findUserByUsername(username);
            if (account != null) {
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                ArrayList<TourTripModel> tourTripModels = new ArrayList<>();
                account.getTourTrips().forEach(e -> {
                    tourTripModels.add(e.toModel());
                });

                responMessage.setData(tourTripModels);
            } else {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage(Constant.MESSAGE.NOT_FOUND_USER);
            }


        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage addTourTripToAccount(String username, String tripCode) {
        ResponMessage responMessage = new ResponMessage();
        try {
            Account account = accountRepository.findUserByUsername(username);
            if (account != null) {
                TourTrip tourTrip = tourTripRepository.findByCode(tripCode);
                account.getTourTrips().add(tourTrip);
                accountRepository.save(account);
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            } else {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage(Constant.MESSAGE.NOT_FOUND_USER);
            }


        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }




    public ResponMessage findTripFeedback(String username) {
        ResponMessage responMessage = new ResponMessage();
        try {
            Account account = accountRepository.findUserByUsername(username);
            if (account != null) {
                Set<TourTrip> tourTrips = account.getTourTrips();
                ArrayList<TourTripModel> tourTripModels = new ArrayList<>();
                tourTrips.forEach(e -> {
                    if (e.getStatus().equals(Constant.STATUS.FINISH)) {
                        tourTripModels.add(e.toModel());
                    }
                });
                responMessage.setData(tourTripModels);
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            } else {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage(Constant.MESSAGE.NOT_FOUND_USER);
            }


        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage start(Long id) {
        ResponMessage responMessage = new ResponMessage();
        try {
            TourTrip tourTrip = tourTripRepository.findTripById(id);
            if (tourTrip == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("TourTrip not found");
            } else {
                if(tourTrip.getStatus().equals(Constant.STATUS.CONFIMRED)) {
                    tourTrip.setStatus(Constant.STATUS.ONTRIP);
                    responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                    responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                    responMessage.setData(tourTripRepository.save(tourTrip).toModel());
                } else {
                    responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                    responMessage.setMessage("Chuyến đi chưa được xác nhận");
                }
            }
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }
    public ResponMessage confirmTrip(Long id) {
        ResponMessage responMessage = new ResponMessage();
        try {
            TourTrip tourTrip = tourTripRepository.findTripById(id);
            if (tourTrip == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("TourTrip not found");
            } else {

                    tourTrip.setStatus(Constant.STATUS.CONFIMRED);
                    responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                    responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                    responMessage.setData(tourTripRepository.save(tourTrip).toModel());

            }
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage cancelTrip(Long id) {
        ResponMessage responMessage = new ResponMessage();
        try {
            TourTrip tourTrip = tourTripRepository.findTripById(id);
            if (tourTrip == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("TourTrip not found");
            } else if(tourTrip.getStatus().equals(Constant.STATUS.CANCEL)) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("Không thể hủy chuyến đi");
            }
            else {

                tourTrip.setStatus(Constant.STATUS.CANCEL);
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);

//                send notification
                tourTrip.getAccount().forEach(e -> {
                    Notification notification = new Notification();
                    notification.setTitle("Thông báo hủy chuyến đi");
                    notification.setContent("Hệ thông Travel xin thông báo chuyến đi vỡi mã "+ tourTrip.getCode() +" đã bị hủy. Xin lỗi quý khách hàng vì sự bất tiện này. Xin cảm ơn quý khách đã tin tưởng sử dụng dịch vụ của Travel.");
                    notification.setUsername(e.getUsername());
                    try {
                        notificationService.sendNotifcationToUser(e.getUsername(),notification);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                });
//                create refund
                List<BookTour> bookTours = bookTourRepository.findByTourTripCode(tourTrip.getCode());
                bookTours.forEach( e -> {
                    if(e.getStatus().equals(Constant.STATUS.PAID)) {
                        Refund refund = new Refund();
                        refund.setStatus(Constant.STATUS.UN_CONFIMRED);
                        refund.setTotalMoney(e.getMoneyToPay());
                        refund.setContent("Hoàn tiền do chuyến đi "+ tourTrip.getCode() + " bị hủy, mã đặt tour "+ e.getCode());
                        refund.setTimeCreated(Instant.now());
                        refund.setAccount(e.getAccount());
                        String code = GenerateCode.generateCode();
                        while (refundRepository.existsByCode(code)) {
                            code = GenerateCode.generateCode();
                        }
                        refund.setCode(code);

                        Transaction transaction = new Transaction();
                        transaction.setCode(code);
                        transaction.setContent("Hoàn tiền do chuyến đi "+ tourTrip.getCode() + " bị hủy, mã đặt tour "+ e.getCode());
                        transaction.setStatus(Constant.STATUS.CONFIMRED);
                        transaction.setTimeCreated(Instant.now());
                        transaction.setType(Constant.TYPE.OUT);
                        transaction.setCreatedBy("SYSTEM");
                        transaction.setTotalMoney(e.getMoneyToPay());
                        transactionRepository.save(transaction);
                        refundRepository.save(refund);
                    }
                });

                responMessage.setData(tourTripRepository.save(tourTrip).toModel());

            }
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage finish(Long id) {
        ResponMessage responMessage = new ResponMessage();
        try {
            TourTrip tourTrip = tourTripRepository.findTripById(id);
            if (tourTrip == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("TourTrip not found");
            } else {
                tourTrip.setStatus(Constant.STATUS.FINISH);

                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                responMessage.setData(tourTripRepository.save(tourTrip).toModel());
            }
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage findPitstopStatus(String tripCode) {
        ResponMessage responMessage = new ResponMessage();
        try {
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(tripPitstopRepository.findByTripCode(tripCode));
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage confirmPitstop(Long tripPitstopId,String note) {
        ResponMessage responMessage = new ResponMessage();
        try {
            TripPitstop tripPitsop = tripPitstopRepository.findTripPitstopById(tripPitstopId);
            tripPitsop.setNote(note);
            tripPitsop.setStatus(Constant.STATUS.CONFIMRED);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(tripPitstopRepository.save(tripPitsop));
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage cancelPitstop(Long tripPitstopId,String note) {
        ResponMessage responMessage = new ResponMessage();
        try {
            TripPitstop tripPitsop = tripPitstopRepository.findTripPitstopById(tripPitstopId);
            tripPitsop.setStatus(Constant.STATUS.CANCEL);
            tripPitsop.setNote(note);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(tripPitstopRepository.save(tripPitsop));
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public ResponMessage findTripAvailableByTourId(Long tourId) {
        ResponMessage responMessage = new ResponMessage();
        try {
            ArrayList<TourTripModel> tourTripModels = new ArrayList<>();
            tourTripRepository.findTripAvailableByTourId(tourId).forEach(e -> {
                tourTripModels.add(e.toModel());
            });
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(tourTripModels);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    public TourTrip findByTripCode(String code) throws Exception {
        try {
            return tourTripRepository.findByCode(code);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public ResponMessage findTourTripInfor(String tripCode) {
        ResponMessage responMessage = new ResponMessage();
        try {
            TourTripInfor tourTripModels = new TourTripInfor();
            TourTrip tourTrip = tourTripRepository.findByCode(tripCode);
            tourTripModels.setId(tourTrip.getId());
            tourTripModels.setDepartureDay(DateUtils.Date2String(tourTrip.getDepartureDay()));
            tourTripModels.setCode(tourTrip.getCode());
            tourTripModels.setNote(tourTrip.getNote());
            tourTripModels.setStatus(tourTrip.getStatus());
            tourTripModels.setPrice(tourTrip.getPrice());
            tourTripModels.setPriceForChidren(tourTrip.getPriceForChidren());
            tourTripModels.setTourModel(tourTrip.getTour().toModel());
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(tourTripModels);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }


}
