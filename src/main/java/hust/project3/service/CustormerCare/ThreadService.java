package hust.project3.service.CustormerCare;

import hust.project3.common.Constant;
import hust.project3.entity.Account;
import hust.project3.entity.CustomerCare.ThreadMessage;
import hust.project3.model.ResponMessage;
import hust.project3.repository.AccountRepository;
import hust.project3.repository.CustomerCare.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {
    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ResponMessage create(ThreadMessage threadMessage,String username) {
        ResponMessage responMessage = new ResponMessage();
        try {
            Account account = accountRepository.findUserByUsername(username);
            if(account == null) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage(Constant.MESSAGE.NOT_FOUND_USER);
                return  responMessage;
            }
            threadMessage.setAccount(account);
            threadMessage.setStatus(Constant.STATUS.UNHANDLED);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(threadRepository.save(threadMessage));
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }

    public ResponMessage findByUsername(String username) {
        ResponMessage responMessage = new ResponMessage();
        try {
            Account account = accountRepository.findUserByUsername(username);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(threadRepository.findThreadMessageByAccountId(account.getId()));
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }

    public ResponMessage handle(Long id) {
        ResponMessage responMessage = new ResponMessage();
        try {
            ThreadMessage threadMessage = threadRepository.findThreadMessageById(id);
            threadMessage.setStatus(Constant.STATUS.HANDLED);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(threadRepository.save(threadMessage));
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }

    public ResponMessage findAll() {
        ResponMessage responMessage = new ResponMessage();
        try {
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(threadRepository.findAllThreadMessage());
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }
}
