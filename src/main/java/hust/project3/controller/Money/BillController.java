package hust.project3.controller.Money;

import hust.project3.common.Constant;
import hust.project3.model.ResponMessage;
import hust.project3.service.Money.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin
@RequestMapping(Constant.API.PREFIX)
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping("/bill/findByAccount")
    @ResponseBody
    public ResponMessage findByAccount(@RequestParam String username) {
        return billService.findByAccount(username);
    }
}
