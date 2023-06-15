package hust.project3.service.Money;

import hust.project3.common.Constant;
import hust.project3.entity.Account;
import hust.project3.entity.Money.Bill;
import hust.project3.model.Money.BillModel;
import hust.project3.model.Money.RefundModel;
import hust.project3.model.ResponMessage;
import hust.project3.repository.Money.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    public ResponMessage findByAccount(String username) {
        ResponMessage responMessage = new ResponMessage();
        try {

            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            List<BillModel> bills = new ArrayList<>();
            billRepository.findBillsByAccount(username).forEach(e -> {
                bills.add(e.toModel());
            });
            responMessage.setData(bills);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }
}
