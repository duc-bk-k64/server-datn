package hust.project3.config.jwtconfig;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import hust.project3.repository.AccountRepository;
import hust.project3.repository.ManageTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	ManageTokenRepository manageTokenRepository;
//	@Value("$(secret_key)")
	private String jwtSecret = "CUDANVUV@10021050";
	public static final Long time_expire = 7200000L;

	public String generateToken(String userName) {
		Date date = new Date(System.currentTimeMillis() + JwtProvider.time_expire);
		return Jwts.builder().setSubject(userName).setExpiration(date).signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public boolean validateToken(String token) {
		if (manageTokenRepository.existsByToken(token))
			return false;

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

}
