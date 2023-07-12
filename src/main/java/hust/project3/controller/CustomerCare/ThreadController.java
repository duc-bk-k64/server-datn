package hust.project3.controller.CustomerCare;

import hust.project3.common.Constant;
import hust.project3.entity.CustomerCare.ThreadMessage;
import hust.project3.model.ResponMessage;
import hust.project3.service.CustormerCare.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin
@RequestMapping(Constant.API.PREFIX)
public class ThreadController {
    @Autowired
    private ThreadService threadService;

    @PostMapping("/thread/create")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponMessage create(@RequestBody ThreadMessage threadMessage, @RequestParam String username) {
        return threadService.create(threadMessage,username);
    }

    @GetMapping("/thread/findByUsername")
    @ResponseBody
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponMessage findByUsername(@RequestParam String username) {
        return threadService.findByUsername(username);
    }


    @GetMapping("/thread/handle")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponMessage handle(@RequestParam Long id) {
        return threadService.handle(id);
    }

    @GetMapping("/thread/findAll")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponMessage findAll() {
        return threadService.findAll();
    }
}
