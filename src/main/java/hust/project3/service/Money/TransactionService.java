package hust.project3.service.Money;

import hust.project3.Utils.DateUtils;
import hust.project3.Utils.GenerateCode;
import hust.project3.common.Constant;
import hust.project3.entity.Money.Transaction;
import hust.project3.model.Money.StatisticModel;
import hust.project3.model.Money.TransactionModel;
import hust.project3.model.ResponMessage;
import hust.project3.repository.Money.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    public ResponMessage findAll() {
        ResponMessage responMessage = new ResponMessage();
        try {
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            List<TransactionModel> transactionModels = new ArrayList<>();
            transactionRepository.findAllTransaction().forEach(e -> {
                transactionModels.add(e.toModel());
            });
            responMessage.setData(transactionModels);
        }catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
         return  responMessage;
    }
    public ResponMessage totalInOneDay() {
        ResponMessage responMessage = new ResponMessage();
        try {
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
//            List<TransactionModel> transactionModels = new ArrayList<>();
            List<Transaction> transactions = transactionRepository.findInTransactionByTime(1L);
            Long total = 0L;
            for(int i= 0; i<transactions.size();i++) {
                total+=transactions.get(i).getTotalMoney();
            }
            responMessage.setData(total);
        }catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }

    public ResponMessage totalOutOneDay() {
        ResponMessage responMessage = new ResponMessage();
        try {
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
//            List<TransactionModel> transactionModels = new ArrayList<>();
            List<Transaction> transactions = transactionRepository.findOutTransactionByTime(1L);
            Long total = 0L;
            for(int i= 0; i<transactions.size();i++) {
                total+=transactions.get(i).getTotalMoney();
            }
            responMessage.setData(total);
        }catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }

    public ResponMessage findAllInByTime(Long day) {
        ResponMessage responMessage = new ResponMessage();
        try {
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            List<TransactionModel> transactionModels = new ArrayList<>();
            transactionRepository.findInTransactionByTime(day).forEach(e -> {
                transactionModels.add(e.toModel());
            });
            responMessage.setData(transactionModels);
        }catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }

    public ResponMessage findAllByTime(Long day) {
        ResponMessage responMessage = new ResponMessage();
        try {
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            List<TransactionModel> transactionModels = new ArrayList<>();
            transactionRepository.findAllTransactionByTime(day).forEach(e -> {
                transactionModels.add(e.toModel());
            });
            responMessage.setData(transactionModels);
        }catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }

    public ResponMessage crete(TransactionModel transactionModel) {
        ResponMessage responMessage = new ResponMessage();
        try {
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            String code = GenerateCode.generateCode();
            while (transactionRepository.existsByCode(code)) {
                code = GenerateCode.generateCode();
            }
            Transaction transaction = transactionModel.toObject();
            transaction.setTimeCreated(Instant.now());
            transaction.setCode(code);
            transaction.setStatus(Constant.STATUS.CONFIMRED);
            responMessage.setData(transactionRepository.save(transaction).toModel());
        }catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }

    public ResponMessage findAllOutByTime(Long day) {
        ResponMessage responMessage = new ResponMessage();
        try {
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            List<TransactionModel> transactionModels = new ArrayList<>();
            transactionRepository.findOutTransactionByTime(day).forEach(e -> {
                transactionModels.add(e.toModel());
            });
            responMessage.setData(transactionModels);
        }catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return  responMessage;
    }

    public ResponMessage statisticIn(Long day) {
        ResponMessage responMessage = new ResponMessage();
        try {
            List<StatisticModel> statisticModels = new ArrayList<>();
            for(int i = 1 ;i <= day ; i++) {
                List<Transaction> transactions = transactionRepository.findInTransactionByTime((long) (i-1), (long) i);
                Long total = 0L;
                for(int j = 0; j<transactions.size() ;j++) {
                    total+=transactions.get(j).getTotalMoney();
                }
                StatisticModel statisticModel = new StatisticModel();
                statisticModel.setValue(total);
                Instant instant = Instant.now();
                Instant ago = instant.minus(Duration.ofDays(i-1));
                statisticModel.setDay(DateUtils.Date2String(Date.from(ago)));
                statisticModels.add(statisticModel);

            }
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(statisticModels);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());

        }
        return  responMessage;
    }
    public ResponMessage statisticOut(Long day) {
        ResponMessage responMessage = new ResponMessage();
        try {
            List<StatisticModel> statisticModels = new ArrayList<>();
            for(int i = 1 ;i <= day ; i++) {
                List<Transaction> transactions = transactionRepository.findOutTransactionByTime((long) (i-1), (long) i);
                Long total = 0L;
                for(int j = 0; j<transactions.size() ;j++) {
                    total+=transactions.get(j).getTotalMoney();
                }
                StatisticModel statisticModel = new StatisticModel();
                statisticModel.setValue(total);
                Instant instant = Instant.now();
                Instant ago = instant.minus(Duration.ofDays(i-1));
                statisticModel.setDay(DateUtils.Date2String(Date.from(ago)));
                statisticModels.add(statisticModel);

            }
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(statisticModels);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());

        }
        return  responMessage;
    }
}
