package hust.project3.controller.Money;

import hust.project3.common.Constant;
import hust.project3.model.ResponMessage;
import hust.project3.service.Money.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin
@RequestMapping(Constant.API.PREFIX)
public class RefundController {
    @Autowired
    private RefundService refundService;

    @GetMapping("/refund/findAll")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponMessage findAll() {
        return refundService.findAll();
    }

    @GetMapping("/refund/confirm")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponMessage confirm(@RequestParam String code) {
        return refundService.confirmRefund(code);
    }
    @GetMapping("/refund/findByAccount")
    @ResponseBody
    public ResponMessage findByAccount(@RequestParam String username) {
        return refundService.findByAccount(username);
    }
}
