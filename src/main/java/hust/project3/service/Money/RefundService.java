package hust.project3.service.Money;

import hust.project3.common.Constant;
import hust.project3.entity.Account;
import hust.project3.entity.Money.Refund;
import hust.project3.model.Money.RefundModel;
import hust.project3.model.ResponMessage;
import hust.project3.repository.AccountRepository;
import hust.project3.repository.Money.RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RefundService {
    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ResponMessage confirmRefund(String code) {
        ResponMessage responMessage = new ResponMessage();
        try {
            Refund refund = refundRepository.findRefundByCode(code);
            refund.setStatus(Constant.STATUS.CONFIMRED);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(refundRepository.save(refund).toModel());
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
            List<RefundModel> refundModels = new ArrayList<>();
            refundRepository.findAllRefund().forEach(e -> {
                RefundModel refundModel = e.toModel();
                refundModel.setAccount(e.getAccount().toDTO());
                refundModels.add(refundModel);
            });
            responMessage.setData(refundModels);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }
    public ResponMessage findByAccount(String username) {
        ResponMessage responMessage = new ResponMessage();
        try {
            Account account = accountRepository.findUserByUsername(username);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            List<RefundModel> refundModels = new ArrayList<>();
            account.getRefunds().forEach(e -> {
                refundModels.add(e.toModel());
            });
            responMessage.setData(refundModels);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }
}
