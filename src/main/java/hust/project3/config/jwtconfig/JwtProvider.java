package hust.project3.config.jwtconfig;

import java.net.InetAddress;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import hust.project3.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hust.project3.entity.Account;
import hust.project3.repository.AccountRepository;
import hust.project3.repository.ManageTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class JwtProvider {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	ManageTokenRepository manageTokenRepository;
//	@Value("$(secret_key)")
	private String jwtSecret = "CUDANVUV@10021050";
//	private final String LOCALHOST_IPV4 = "127.0.0.1";
//	private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
	public static final Long time_expire = 7200000L;

	public String generateToken(String userName) {
		Date date = new Date(System.currentTimeMillis() + JwtProvider.time_expire);
		return Jwts.builder().setSubject(userName).setExpiration(date).signWith(SignatureAlgorithm.HS512, jwtSecret).claim("role","ROLE_STUDENT")
				.compact();
	}

	public boolean validateToken(String token) {
		if (manageTokenRepository.existsByToken(token))
			return false;
		if(accountRepository.findUserByUsername(getLoginFormToke(token)).getStatus() == Constant.STATUS.DE_ACTIVE) {
			return false;
		}
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
//			System.out.println("invalid token");
		}
		return false;

	}

	public String getLoginFormToke(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

//	@SuppressWarnings("deprecation")
//	public String getClientIp() {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//				.getRequest();
//
//		String ipAddress = request.getHeader("X-Forwarded-For");
//		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getHeader("Proxy-Client-IP");
//		}
//
//		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getHeader("WL-Proxy-Client-IP");
//		}
//
//		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getRemoteAddr();
//			if (LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
//				try {
//					InetAddress inetAddress = InetAddress.getLocalHost();
//					ipAddress = inetAddress.getHostAddress();
//				} catch (UnknownHostException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		if (!StringUtils.isEmpty(ipAddress) && ipAddress.length() > 15 && ipAddress.indexOf(",") > 0) {
//			ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
//		}
//
//		return ipAddress;
//	}

}
