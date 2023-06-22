package hust.project3.service.CustormerCare;

import hust.project3.common.Constant;
import hust.project3.entity.Account;
import hust.project3.entity.CustomerCare.Question;
import hust.project3.entity.CustomerCare.ThreadMessage;
import hust.project3.entity.Notification;
import hust.project3.model.ResponMessage;
import hust.project3.repository.CustomerCare.QuestionRepository;
import hust.project3.repository.CustomerCare.ThreadRepository;
import hust.project3.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private NotificationService notificationService;

    public ResponMessage create(Question question, Long threadId) {
        ResponMessage responMessage = new ResponMessage();
        try {
            ThreadMessage threadMessage = threadRepository.findThreadMessageById(threadId);
            if(threadMessage == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("Đã  xảy ra lỗi");
                return  responMessage;
            }
            question.setThread(threadMessage);
            question.setTimeCreated(Instant.now());
            threadMessage.setStatus(Constant.STATUS.UNHANDLED);
            threadRepository.save(threadMessage);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(questionRepository.save(question));
//            Send notification to staff
            Notification notification = new Notification();
            notification.setUsername("SYSTEM");
            notification.setTitle("Có yêu cầu hỗ trợ từ khách hàng");
            notification.setContent(question.getContent());
//            notification.setContent("Hệ thông Travel xin thông báo đơn đặt tour vỡi mã "+bookTour.getCode() +" đã được thanh toán. Qúy khách hàng vui lòng thường xuyên kiểm tra điện thoại để nhận được hướng dẫn và thông tin chi tiết. Xin trân trọng cảm ơn.");
            notificationService.sendToStaff(notification);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }
}
