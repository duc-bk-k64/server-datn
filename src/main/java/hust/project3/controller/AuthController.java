package hust.project3.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hust.project3.model.Tour.FindTourModel;
import hust.project3.service.Post.ParagraphService;
import hust.project3.service.Post.PostService;
import hust.project3.service.Tour.*;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import hust.project3.common.*;
import hust.project3.config.jwtconfig.AuthEntryPointJWT;
import hust.project3.config.jwtconfig.JwtProvider;
import hust.project3.entity.Account;
import hust.project3.entity.Permission;
import hust.project3.entity.Provider;
import hust.project3.entity.Role;
import hust.project3.model.ResponMessage;
import hust.project3.model.SignInData;
import hust.project3.model.facebookModel;
import hust.project3.model.facebookResponse;
import hust.project3.model.fotgotPW;
import hust.project3.model.googleModel;
import hust.project3.model.googleResponse;
import hust.project3.model.loginResponse;
import hust.project3.repository.AccountRepository;
import hust.project3.repository.PermissionRepository;
import hust.project3.repository.RoleRepository;
import hust.project3.service.AccountService;

@RestController
//@CrossOrigin
@RequestMapping(Constant.API.PREFIX_AUTH)
public class AuthController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private AccountService accountService;

    @Autowired
    private TourService tourService;

    @Autowired
    private PostService postService;
    @Autowired
    private ParagraphService paragraphService;
    @Autowired
    private TourTripService tourTripService;
    @Autowired
    private PitstopService pitstopService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private DestinationService destinationService;
    @Autowired
    private DepartureService departureService;

    private RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @SuppressWarnings("unused")
    @PostMapping("/signin")
    @ResponseBody
    public ResponMessage signIn(@RequestBody SignInData data) {

        Account account = accountRepository.findUserByUsername(data.getUserName());
        ResponMessage responMessage = new ResponMessage();
//		logger.info(jwtProvider.getClientIp());
        if (account == null) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(Constant.MESSAGE.NOT_FOUND_USER);
            return responMessage;
        } else if (!passwordEncoder.matches(data.getPassWord(), account.getPassword())) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(Constant.MESSAGE.PASSWORD_INCORRECT);
            return responMessage;

        } else if (account.getStatus() == Constant.STATUS.DE_ACTIVE) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(Constant.MESSAGE.ACCOUNT_DEACTIVE);
            return responMessage;
        } else {
            String token = jwtProvider.generateToken(data.getUserName());
            Set<Role> roles = account.getRoles();
            loginResponse loginResponse = new loginResponse();
            loginResponse.setToken(token);
            loginResponse.setUsername(data.getUserName());
            loginResponse.setRole(roles.iterator().next().getName());
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(loginResponse);
            return responMessage;
        }
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponMessage signup(@RequestBody SignInData signUp) throws Exception {
        ResponMessage responMessage = new ResponMessage();
        if (accountRepository.existsByUsername(signUp.getUserName())) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(Constant.MESSAGE.USERNAME_EXIST);
            return responMessage;

        } else if (accountRepository.existsByEmail(signUp.getEmail())) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(Constant.MESSAGE.EMAIL_EXIST);
            return responMessage;
        } else {
            Account account = new Account();
            account.setStatus(Constant.STATUS.DE_ACTIVE);
            account.setUsername(signUp.getUserName());
            account.setPassword(passwordEncoder.encode(signUp.getPassWord()));
            account.setProvider(Provider.LOCAL);
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName(Constant.ROLE.USER);
            roles.add(role);
            Set<Permission> permissions = new HashSet<>();
            Permission permission = permissionRepository.findByName(Constant.PERMISSION.READ);
            permissions.add(permission);
            account.setRoles(roles);
            account.setPermissions(permissions);
            account.setEmail(signUp.getEmail());
            String token = accountService.generateToken();
            while (accountRepository.existsByCode(token)) {
                token = accountService.generateToken();
            }
            account.setCode(token);
            accountRepository.save(account);
            accountService.sendMailActiveAccount(token, signUp.getEmail());
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(account);
            return responMessage;
        }

    }

    @PostMapping("/forgotpw")
    @ResponseBody
    public ResponMessage forgotPW(@RequestBody fotgotPW forgot) throws Exception {

        ResponMessage responMessage = new ResponMessage();
        try {
            String message = accountService.forgotPassword(forgot.getUserName());
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(message);
            return responMessage;
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(Constant.MESSAGE.ERROR);
            responMessage.setData(e.getMessage());
            return responMessage;
        }

    }

    @PostMapping("/resetpw")
    @ResponseBody
    public ResponMessage resetPW(@RequestBody hust.project3.model.resetPW reset) {
        ResponMessage responMessage = new ResponMessage();
        try {
            String message = accountService.resetPassword(reset.getToken(), reset.getPassword());
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(message);
            return responMessage;
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(Constant.MESSAGE.ERROR);
            responMessage.setData(e.getMessage());
            return responMessage;
        }
    }

    @GetMapping("/authFail")
    @ResponseBody
    public ResponMessage authFail() {
        ResponMessage responMessage = new ResponMessage();
        responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
        responMessage.setMessage(Constant.MESSAGE.ERROR);
        responMessage.setData("Authentication failure");
        return responMessage;
    }

    @GetMapping("/accessDenied")
    @ResponseBody
    public ResponMessage accessDenied() {
        ResponMessage responMessage = new ResponMessage();
        responMessage.setResultCode(Constant.RESULT_CODE.UNAUTHORIZED);
        responMessage.setMessage(Constant.MESSAGE.ERROR);
        responMessage.setData("Access denied");
        return responMessage;
    }

    @PostMapping("/loginFacebook")
    @ResponseBody
    public ResponMessage loginFacebook(@RequestBody facebookModel facebookModel) {
        ResponMessage responMessage = new ResponMessage();
        try {
            facebookResponse facebookResponse = restTemplate.getForObject(
                    "https://graph.facebook.com/me?access_token=" + facebookModel.getAuthToken(),
                    facebookResponse.class);
            if (!facebookResponse.getId().equals(facebookModel.getId())) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("Infomation is incorrect");
//				responMessage.setData(e.getMessage());
            } else if (accountRepository.existsByCode(facebookModel.getId())) {
                // đã đăng nhập trước đó
                Account account = accountRepository.findUserByUsername(facebookModel.getId());
                if(account.getStatus() == Constant.STATUS.DE_ACTIVE) {
                    responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                    responMessage.setMessage("Account is deactivate");
                } else {
                    String token = jwtProvider.generateToken(facebookModel.getId());
                    responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                    responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                    responMessage.setData(token);
                }
            } else {
                // đăng nhập lần đầu
                Account account = new Account();
                account.setStatus(Constant.STATUS.ACTIVE);
                account.setUsername(facebookModel.getId());
                account.setPassword(passwordEncoder.encode(facebookModel.getId()));
                account.setProvider(Provider.FACEBOOK);
                Set<Role> roles = new HashSet<>();
                Role role = roleRepository.findByName(Constant.ROLE.USER);
                roles.add(role);
                Set<Permission> permissions = new HashSet<>();
                Permission permission = permissionRepository.findByName(Constant.PERMISSION.READ);
                permissions.add(permission);
                account.setRoles(roles);
                account.setPermissions(permissions);
                account.setEmail(facebookModel.getEmail());
                account.setName(facebookModel.getName());
                account.setCode(facebookModel.getId());
                accountRepository.save(account);
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                String token = jwtProvider.generateToken(facebookModel.getId());
                responMessage.setData(token);

            }
//			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
//			responMessage.setData(facebookResponse);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(Constant.MESSAGE.ERROR);
            responMessage.setData(e.getMessage());
        }
        return responMessage;
    }

    @PostMapping("/loginGoogle")
    @ResponseBody
    public ResponMessage loginGoogle(@RequestBody googleModel googleModel) {
        ResponMessage responMessage = new ResponMessage();
        try {
            googleResponse googleResponse = restTemplate.getForObject(
                    "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + googleModel.getCredential(),
                    googleResponse.class);
            if (!googleResponse.getEmail().equals(googleModel.getEmail())) {
                responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                responMessage.setMessage("Infomation is incorrect");
//				responMessage.setData(e.getMessage());
            } else if (accountRepository.existsByUsername(googleModel.getEmail())) {
                // đã đăng nhập trước đó
                Account account = accountRepository.findUserByUsername(googleModel.getEmail());
                if(account.getStatus() == Constant.STATUS.DE_ACTIVE) {
                    responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
                    responMessage.setMessage("Account is deactivate");
                } else {
                    String token = jwtProvider.generateToken(googleModel.getEmail());
                    responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                    responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                    responMessage.setData(token);
                }

            } else {
                // đăng nhập lần đầu
                Account account = new Account();
                account.setStatus(Constant.STATUS.ACTIVE);
                account.setUsername(googleModel.getEmail());
                account.setPassword(passwordEncoder.encode(googleModel.getEmail()));
                account.setProvider(Provider.GOOGLE);
                Set<Role> roles = new HashSet<>();
                Role role = roleRepository.findByName(Constant.ROLE.USER);
                roles.add(role);
                Set<Permission> permissions = new HashSet<>();
                Permission permission = permissionRepository.findByName(Constant.PERMISSION.READ);
                permissions.add(permission);
                account.setRoles(roles);
                account.setPermissions(permissions);
                account.setEmail(googleModel.getEmail());
                account.setName(googleModel.getName());
                String code = accountService.generateToken();
                while (accountRepository.existsByCode(code)) {
                    code = accountService.generateToken();
                }
                account.setCode(code);
                accountRepository.save(account);
                responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
                responMessage.setMessage(Constant.MESSAGE.SUCCESS);
                String token = jwtProvider.generateToken(googleModel.getEmail());
                responMessage.setData(token);

            }
//			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
//			responMessage.setData(facebookResponse);
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(Constant.MESSAGE.ERROR);
            responMessage.setData(e.getMessage());
        }
        return responMessage;
    }

    @PostMapping("/active")
    @ResponseBody
    public ResponMessage activeAccount(@RequestParam String code) {
        ResponMessage responMessage = new ResponMessage();
        Account account = accountRepository.findByCode(code);
        if (account != null) {
            account.setStatus(Constant.STATUS.ACTIVE);
            accountRepository.save(account);
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
        } else {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(Constant.MESSAGE.ERROR);
        }
        return responMessage;
    }

    @GetMapping("/tour/findAlls")
    @ResponseBody
    public ResponMessage findAllTour() {
        return tourService.findAllAvailable();
    }

    @GetMapping("/trip/findByTourId")
    @ResponseBody
    public ResponMessage findByTourId(@RequestParam Long tourId) {
        return tourTripService.findTripAvailableByTourId(tourId);
    }

    @GetMapping("/trip/findTripByTripCode")
    @ResponseBody
    public ResponMessage findTripByTripCode(@RequestParam String code) {
        ResponMessage responMessage = new ResponMessage();
        try {
            responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
            responMessage.setMessage(Constant.MESSAGE.SUCCESS);
            responMessage.setData(tourTripService.findByTripCode(code).toModel());
        } catch (Exception e) {
            responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
            responMessage.setMessage(e.getMessage());
        }
        return responMessage;
    }

    @GetMapping("/trip/findTourTripInfor")
    @ResponseBody
    public ResponMessage findTourTripInforByTripCode(@RequestParam String code) {
        return tourTripService.findTourTripInfor(code);

    }

    @GetMapping("/pitstop/findByTourId")
    @ResponseBody
    public ResponMessage findPitstopByTourId(@RequestParam Long tourId) {
        return pitstopService.findByTourId(tourId);
    }

    @GetMapping("/feedback/findByTourId")
    @ResponseBody
    public ResponMessage findFeedbackByTourId(@RequestParam Long tourId) {
        return feedbackService.findByTourId(tourId);
    }

    @GetMapping("/des/findAll")
    @ResponseBody
    public ResponMessage findAllDes() {
        return destinationService.findAll();
    }

    @GetMapping("/des/findByTourId")
    @ResponseBody
    public ResponMessage findDesByTourId(@RequestParam Long tourId) {
        return destinationService.findByTourId(tourId);
    }

    @GetMapping("/post/findByDestiantion")
    @ResponseBody
    public ResponMessage findByDestination(@RequestParam Long desId) {
        return postService.findByDestination(desId);
    }

    @GetMapping("/post/findById")
    @ResponseBody
    public ResponMessage findPostById(@RequestParam Long id) {
        return postService.findById(id);
    }

    @GetMapping("/paragraph/findByPostId")
    @ResponseBody
    public ResponMessage findParagraphByPostId(@RequestParam Long postId) {
        return paragraphService.findByPostId(postId);
    }

    @GetMapping("/tour/findTourId")
    @ResponseBody
    public ResponMessage findTourById(@RequestParam Long tourId) {
        return tourService.findById(tourId);
    }

    @PostMapping("/tour/findTour")
    @ResponseBody
    public ResponMessage findTour(@RequestBody FindTourModel data) {
        return tourService.findTour(data);
    }

    @GetMapping("/tour/findTourByDesId")
    @ResponseBody
    public ResponMessage findTourByDesId(@RequestParam Long desId) {
        return destinationService.findTourByDesId(desId);

    }
    @GetMapping("/departure/findAll")
	@ResponseBody
	public ResponMessage findAll() {
		return departureService.findAll();
	}


}
