package hust.project3.Utils;

import java.util.Random;

public class GenerateCode {
	public static String generateCode() {
		Random random = new Random();
		StringBuilder token = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			token.append(random.nextInt(10));
		}
		return token.toString();
	}

}
