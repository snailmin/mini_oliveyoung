package com.sh.user.view;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.sh.admin.view.AdminMenu;
import com.sh.user.controller.UserController;
import com.sh.user.model.entity.User;
import com.sh.user.model.validation.validation;
/**
 * - 메인화면
 * 	1. 로그인
 * 	2. 회원가입
 *
 */
public class UserMenu {
	private Scanner sc = new Scanner(System.in);
	private UserController userController = new UserController();
	private validation validation = new validation();
	
	public void loginMenu() {
		String loginMenu = """
					===============================
						    올x브영
					===============================
					1. 로그인
					2. 회원가입
					0. 종료
					===============================
					[선택] :  """;
		
		while(true) {
			System.out.println();
			System.out.print(loginMenu);
			String choice = sc.next();
			
			switch (choice) {
			case "1": 
				displayLoginMenu(); 
				break;
			case "2": 
				displayJoinUserMenu(); 
				break;
			case "0": return;
			default:
				System.out.println("🚨 잘못 입력하셨습니다.");
				break;
			}
		}
	}

	/**
	 * 로그인 메소드
	 * 	1. 아이디 존재여부
	 * 	2. 해당 아이디의 비밀번호 일치여부
	 */
	private void displayLoginMenu() {
		String id = null;
		String password = null;

		while (true) {
			String login = """
					========= 🔓 로그인 🔓 =========
					""";
			System.out.println();
			System.out.print(login);
			System.out.print("✅ 아이디를 입력하세요 : ");
			id = sc.next();
			System.out.print("✅ 비밀번호를 입력하세요 : ");
			password = sc.next();

			User userId = userController.findById(id);
			User userTest = userController.findByUser(id, password);

			if (userId == null) {
				System.out.println("🚨 [실패] 아이디가 존재하지 않습니다. 😭");
				return;
			} else if (userTest == null) {
				System.out.println("🚨 [실패] 비밀번호가 일치하지 않습니다. 😭");
				// 비밀번호 변경하기 ~
				
			} else {
				System.out.println("🔊 [성공] 메인화면으로 이동합니다. 🥰");
				System.out.println("===============================");
				break;
			}
		}
		if ("admin".equals(id) && "admin123!".equals(password)) {
			new AdminMenu().adminMenu();
		} else {
			// 다음 메소드 부르기
			new MainMenu().mainMenu(id);
		}
	}
	
	/**
	 * 회원가입 메소드
	 *  1. 아이디 유효성 검사
	 *  2. 비밀번호 유효성 검사 -> 비밀번호 확인
	 *  3. 회원가입 성공여부 출력
	 */
	private void displayJoinUserMenu() {
		String join = """
				========= ✒️ 회원가입 ✒️ ========
				""";
		User user = new User();
		String id = null;
		
		System.out.println();
		System.out.print(join);
		System.out.println("✅ 회원정보를 입력하세요.");
		for (;;) {
			System.out.print("✅ 아이디 : ");
			
			id = sc.next();
			if (userController.findById(id) == null) {
				System.out.println("🔊 사용가능한 아이디입니다. 😄");
				break;
			} else if ("admin".equals(id)) {
                System.out.println("🚨 사용불가능한 아이디입니다. 다시 입력하세요.");
                continue;
            } else {
				System.out.println("🚨 사용불가능한 아이디입니다. 다시 입력하세요.");
				continue;
			}
		}
		
		user.setId(id);
		System.out.print("✅ 이름 : ");
		user.setName(sc.next());
		
		String password = passwordValid("✅ 비밀번호 : ");
		user.setPassword(password);
		passwordVerify(password);
		
		System.out.print("✅ 생년월일(ex. 19990909) : ");
		String birthday = sc.next();
		user.setBirthday(Date.valueOf(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyyMMdd"))));
		
		int skinType = 0;
		while (true) {
			System.out.print("✅ 피부 타입(1.건성, 2.지성, 3.민감성) : ");
			skinType = sc.nextInt();
			if (skinType == 1 || skinType == 2 || skinType == 3)
				break;
			else {
				System.out.println("🚨 잘못 입력하셨습니다.");
			}
		}
		if (skinType == 1) {
			user.setSkinType("건성");
		} else if (skinType == 2) {
			user.setSkinType("지성");
		} else if (skinType == 3) {
			user.setSkinType("민감성");
		}
		
		System.out.println("===============================");
		
		// insert
		int result = userController.insertUser(user);
		displayResult("[ 회원가입 ]", result);
		
		displayLoginMenu();
		
	}
	
	// 비밀번호가 조건에 맞는지 확인하는 메소드
	public String passwordValid(String explain) {
		String password = null;
		
		password: while (true) {
			System.out.print(explain);
			password = sc.next();
			boolean[] validPassword = new boolean[3];
			validPassword = validation.isValidPassword(password);
			
			// 출력할 안내문을 저장할 객체
			StringBuilder valid = new StringBuilder();
			
			if(validPassword[0] == false)
				valid.append("🚨 비밀번호 길이는 8자리에서 16자리로 입력하세요.");
			else if(validPassword[1] == false)
				valid.append("🚨 비밀번호에는 숫자, 영어, 특수문자가 포함되어야합니다.");
			else if(validPassword[2] == true) {
				valid.append("🚨 사용가능한 비밀번호입니다.");
				break password;
			}
			System.out.println(valid);
		}
		return password;
	}
	
	
	public void passwordVerify(String password) {
		String verifyPassword = null;
		while(true) {
			System.out.print("✅ 비밀번호 확인 : ");
			verifyPassword = sc.next();
			if(validation.isVerifyPassword(password, verifyPassword)){
				System.out.println("✅ 비밀번호가 일치합니다.");
				break;
			}
			else
				System.out.println("🚨 비밀번호가 일치하지 않습니다.");
		}
	}
	
	
	// DDL result 출력문
	private void displayResult(String menu, int result) {
		if(result > 0) {
			System.out.println(menu + " 성공 🆗🆗");
		}
		else {
			System.out.println(menu + " 실패 🆖🆖");
		}
	}	
}
