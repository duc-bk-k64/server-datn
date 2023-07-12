package hust.project3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hust.project3.common.Constant;
import hust.project3.entity.Notification;
import hust.project3.entity.cache;
import hust.project3.model.ResponMessage;
import hust.project3.model.SignInData;
import hust.project3.repository.cacheRepository;
import hust.project3.service.NotificationService;

@RestController
@RequestMapping(Constant.API.PREFIX)
@CacheConfig(cacheNames = "cache")
public class TestController {
	@Autowired
	private cacheRepository cacheRepository;
	
	@Autowired
	private NotificationService notificationService;

	@GetMapping("/")
	@ResponseBody
//	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponMessage test() {
		ResponMessage responMessage = new ResponMessage();
		responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
		responMessage.setMessage(Constant.MESSAGE.SUCCESS);
		responMessage.setData("Fix CORS");
		this.notificationService.test();
		return responMessage;
	}

	@GetMapping("/abc")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponMessage testAdmin() {
		ResponMessage responMessage = new ResponMessage();
		responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
		responMessage.setMessage(Constant.MESSAGE.SUCCESS);
		responMessage.setData("Admin");
		return responMessage;
	}

	@GetMapping("/redis")
	@ResponseBody
	@Cacheable(value = "cache", key = "#id")
	public ResponMessage testCache(@RequestParam Long id) {
		ResponMessage responMessage = new ResponMessage();
		responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
		responMessage.setMessage(Constant.MESSAGE.SUCCESS);
		responMessage.setData(cacheRepository.findCacheById(id));
		System.out.println("no cache");
		return responMessage;
//		return cacheRepository.findCacheById(id);
	}

	@PutMapping
	@ResponseBody
	@CachePut(value = "cache", key = "#cache.id")
	public ResponMessage testCaches(@RequestBody cache cache) {
		ResponMessage responMessage = new ResponMessage();
		responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
		responMessage.setMessage(Constant.MESSAGE.SUCCESS);
		responMessage.setData(cacheRepository.save(cache));
		return responMessage;
//		return cacheRepository.save(cache);
	}

	@DeleteMapping("/remove")
	@ResponseBody
	@CacheEvict(key = "#id", value = "cache")
	public ResponMessage redis(@RequestParam Long id) {
		ResponMessage responMessage = new ResponMessage();
		responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
		responMessage.setMessage(Constant.MESSAGE.SUCCESS);
		responMessage.setData(null);
		cacheRepository.deleteById(id);
		return responMessage;

	}
	@GetMapping("/notifi")
	@ResponseBody
	public ResponMessage notifi(@RequestParam String username) {
		ResponMessage responMessage = new ResponMessage();
		try {
			responMessage.setResultCode(Constant.RESULT_CODE.SUCCESS);
			responMessage.setMessage(Constant.MESSAGE.SUCCESS);
			responMessage.setData(null);
			Notification notification = new Notification();
			notification.setUsername(username);
			notification.setTitle("abcd dddd");
			this.notificationService.sendNotifcationToUser(username,notification);

		} catch (Exception e) {
			responMessage.setResultCode(Constant.RESULT_CODE.ERROR);
			responMessage.setMessage(e.getMessage());
		}
		return responMessage;
	
	}

}
