package hust.project3.controller.CustomerCare;

import hust.project3.common.Constant;
import hust.project3.entity.CustomerCare.Question;
import hust.project3.entity.CustomerCare.Reply;
import hust.project3.model.ResponMessage;
import hust.project3.service.CustormerCare.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.API.PREFIX)
@PreAuthorize("hasRole('ROLE_STAFF')")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @PostMapping("/reply/create")
    @ResponseBody
    public ResponMessage create(@RequestBody Reply reply, @RequestParam Long threadId) {
        return replyService.create(reply,threadId);
    }
}
