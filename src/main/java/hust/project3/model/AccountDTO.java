package hust.project3.model;

import hust.project3.Utils.DateUtils;
import hust.project3.entity.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.Instant;

@Getter
@Setter
public class AccountDTO {
    private Long id;
    private String username;
    private String password;
    private Long status;
    private String tokenForgotPassword;

    private String timeCreatioToken;
    private String email;
    private String code;
    private String name;

    private  String provider;

    private String role;
    public Account toObject() {
        Account account = new Account();
        account.setId(id);
        account.setUsername(username);
        account.setPassword(password);
        account.setStatus(status);
        account.setTokenForgotPassword(tokenForgotPassword);
        account.setEmail(email);
        account.setCode(code);
        account.setName(name);
        return  account;
    }
}
