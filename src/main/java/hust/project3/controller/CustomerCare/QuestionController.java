package hust.project3.controller.CustomerCare;

import hust.project3.common.Constant;
import hust.project3.entity.CustomerCare.Question;
import hust.project3.model.ResponMessage;
import hust.project3.service.CustormerCare.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin
@RequestMapping(Constant.API.PREFIX)
//@PreAuthorize("hasRole('ROLE_USER')")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/question/create")
    @ResponseBody
    public ResponMessage create(@RequestBody Question question,@RequestParam Long threadId) {
        return questionService.create(question,threadId);
    }
}
