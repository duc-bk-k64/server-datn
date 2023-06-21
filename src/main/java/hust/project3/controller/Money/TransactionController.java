package hust.project3.controller.Money;

import hust.project3.common.Constant;
import hust.project3.model.Money.TransactionModel;
import hust.project3.model.ResponMessage;
import hust.project3.service.Money.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.API.PREFIX)
@PreAuthorize("hasRole('ROLE_STAFF')")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction/findAll")
    @ResponseBody
    public ResponMessage findAll() {
        return  transactionService.findAll();
    }

    @GetMapping("/transaction/findInByTime")
    @ResponseBody
    public ResponMessage findInByTime(@RequestParam Long day) {
        return  transactionService.findAllInByTime(day);
    }

    @GetMapping("/transaction/findAllByTime")
    @ResponseBody
    public ResponMessage findAllByTime(@RequestParam Long day) {
        return  transactionService.findAllByTime(day);
    }

    @PostMapping("/transaction/create")
    @ResponseBody
    public ResponMessage create(@RequestBody TransactionModel data) {
        return  transactionService.crete(data);
    }

    @GetMapping("/transaction/findOutByTime")
    @ResponseBody
    public ResponMessage findOutByTime(@RequestParam Long day) {
        return  transactionService.findAllOutByTime(day);
    }
    @GetMapping("/transaction/totalInOneDay")
    @ResponseBody
    public ResponMessage totalInOneDay() {
        return  transactionService.totalInOneDay();
    }
    @GetMapping("/transaction/totalOutOneDay")
    @ResponseBody
    public ResponMessage totalOutOneDay() {
        return  transactionService.totalOutOneDay();
    }


    @GetMapping("/transaction/statisticInByTime")
    @ResponseBody
    public ResponMessage statisticInByTime(@RequestParam Long day) {
        return  transactionService.statisticIn(day);
    }

    @GetMapping("/transaction/statisticOutByTime")
    @ResponseBody
    public ResponMessage statisticOutByTime(@RequestParam Long day) {
        return  transactionService.statisticOut(day);
    }
}
