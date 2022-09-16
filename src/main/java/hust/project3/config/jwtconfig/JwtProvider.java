package hust.project3.config.jwtconfig;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import hust.project3.repository.AccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	@Autowired
	AccountRepository accountRepository;
//	@Value("$(secret_key)")
	private String jwtSecret = "CUDANVUV@10021050";

	public String generateToken(String userName) {
		Date date = new Date(System.currentTimeMillis() + 7200000);
		return Jwts.builder().setSubject(userName).setExpiration(date).signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			System.out.println("invalid token");
		}
		return false;

	}

	public String getLoginFormToke(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

}
