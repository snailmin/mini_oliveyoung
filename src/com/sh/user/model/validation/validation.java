package com.sh.user.model.validation;

import java.util.regex.Pattern;

import com.sh.user.controller.UserController;

public class validation{
	private UserController userController = new UserController();
	
	public boolean[] isValidPassword(String password) {
		boolean[] validPassword = new boolean[3];
		
		final int MIN = 8;
		final int MAX = 16;
		String regexp_password = "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{" + MIN + "," + MAX + "})$";
//		대소문자 + 숫자 + 특수문자 조합으로 8 ~ 16
		// \\W 특수문자 허용
		// \\d 숫자만 허용
		
		validPassword[0] = true;
		validPassword[1] = true;
		validPassword[2] = false;
		
		// 길이가 적합하지 않을 때 false로 저장
		if(password.length() < 8 || password.length() > 16)
			validPassword[0] = false; 
		// 패턴에 일치할때 true를 반환하지만 이를 false로 변경
		else if(!Pattern.matches(regexp_password, password))
			validPassword[1] = false; 
		else {
			validPassword[2] = true;
		}
		return validPassword;
	}
	
	public boolean isVerifyPassword(String password, String verifyPassword) {

		return verifyPassword.equals(password);
	}
}