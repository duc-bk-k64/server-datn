package hust.project3.service.CustormerCare;

import hust.project3.common.Constant;
import hust.project3.entity.CustomerCare.Question;
import hust.project3.entity.CustomerCare.Reply;
import hust.project3.entity.CustomerCare.ThreadMessage;
import hust.project3.entity.Notification;
import hust.project3.model.ResponMessage;
import hust.project3.repository.CustomerCare.ReplyRepository;
import hust.project3.repository.CustomerCare.ThreadRepository;
import hust.project3.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private NotificationService notificationService;

    public ResponMessage create(Reply reply, Long threadId) {
        ResponMessage responMessage = new ResponMessage();
        try {
            ThreadMessage threadMessage = threadRepository.findThreadMessageById(threadId);
            if(threadMessage == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("Đã  xảy ra lỗi");
                return  responMessage;
            }
            reply.setThread(threadMessage);
            reply.setTimeCreated(Instant.now());
            threadMessage.setStatus(Constant.STATUS.HANDLED);
            threadRepository.save(threadMessage);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(replyRepository.save(reply));
//            Send notification to user
            Notification notification = new Notification();
            notification.setUsername(threadMessage.getAccount().getUsername());
            notification.setTitle("Yêu cầu hỗ trợ của bạn đã được phản hồi");
            notification.setContent(reply.getContent());
//            notification.setContent("Hệ thông Travel xin thông báo đơn đặt tour vỡi mã "+bookTour.getCode() +" đã được thanh toán. Qúy khách hàng vui lòng thường xuyên kiểm tra điện thoại để nhận được hướng dẫn và thông tin chi tiết. Xin trân trọng cảm ơn.");
            notificationService.sendNotifcationToUser(notification.getUsername(),notification);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }
}
