package com.learning.weblogin.controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.weblogin.dto.WebLoginRequestModel;
import com.learning.weblogin.entity.DefaultUserDetail;
import com.learning.weblogin.entity.UserDetail;
import com.learning.weblogin.service.ExcelGeneratorService;
import com.learning.weblogin.service.UserDetailService;
import com.learning.weblogin.service.UserService;
import com.learning.weblogin.validator.InputValidator;

@Controller
public class WebLoginController {

	private final InputValidator inputValidator;

	private final UserService userService;

	private final UserDetailService userDetailService;

	private final ExcelGeneratorService excelGeneratorService;

	private static final Logger LOG = LoggerFactory.getLogger(WebLoginController.class);

	@Value("${app.log-file-path}")
	private String logFilePath;

	public WebLoginController(InputValidator inputValidator, UserService userService,
			UserDetailService userDetailService, ExcelGeneratorService excelGeneratorService) {
		this.inputValidator = inputValidator;
		this.userService = userService;
		this.userDetailService = userDetailService;
		this.excelGeneratorService = excelGeneratorService;
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("loginRequestModel", WebLoginRequestModel.builder().build());
		return "/login";
	}

	@GetMapping("/home")
	public String home() {
		return "/home";
	}

	@GetMapping("/user_info")
	public String userInfo(@RequestParam String userId, Model model) {
		inputValidator.valdiateUserId(userId);

		userService.findUserByUserId(userId).ifPresentOrElse(user -> {
			LOG.info("Returning user information for user {}", user);
			model.addAttribute("user", user);
		}, () -> {
			LOG.error("Couldn't find user with id: {}", userId);
		});

		return "user_info";

	}

	@GetMapping("/user-info-by-username")
	public String userInfoByUsername(String username, Model model) {
		inputValidator.valdiateUserName(username);

		userService.findUserInfoByUsername(username).ifPresentOrElse(user -> {
			LOG.info("Returning user information for user {}", username);
			model.addAttribute("user", user);
		}, () -> {
			LOG.error("Couldn't find user with username: {}", username);
		});

		return "user_info";
	}

	@GetMapping("/log-data")
	public String readLogData(Model model) {
		try {
			String logs = String.join(System.lineSeparator(), Files.readAllLines(Paths.get(logFilePath)));
			model.addAttribute("log_data", logs);
		} catch (IOException e) {
			LOG.error("Couldn't read log file!");
		}
		return "log_data";
	}

	@PostMapping("/user-detail")
	public String userDetail(UserDetail userDetail, Model model) {
		if (userDetail.getId() == null) {
			userDetail.setId(UUID.randomUUID());
		}
		userDetailService.addUserDetail(userDetail);
		model.addAttribute("user_detail", userDetail);
		return "user_detail";
	}

	@GetMapping("/user-detail")
	public String userDetail(@RequestParam BigInteger userId, Model model) {
		userDetailService.getUserDetailByUserId(userId).ifPresentOrElse(userDetail -> {
			LOG.info("Returning user details");
			model.addAttribute("user_detail", userDetail);
		}, () -> {
			LOG.info("No user details found!");
			model.addAttribute("user_detail", new DefaultUserDetail());
		});
		return "user_detail";
	}

	@GetMapping("/export-user-details")
	public void exportUserDetails(HttpServletResponse response) {
		Workbook workbook = null;
		try {
			Optional<List<UserDetail>> userDetails = userDetailService.getUserDetails();
			if (userDetails.isPresent()) {
				List<UserDetail> userDetailsList = userDetails.get();
				LOG.info("Exporting user details with size:{} ",userDetailsList.size());
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=user_details.xls");
				workbook = excelGeneratorService.generateExcel(userDetailsList);
				workbook.write(response.getOutputStream());
			} else {
				LOG.info("No user details found!");
			}
		} catch (IOException e) {
			LOG.error("Error generating excel! {}", e.getMessage());
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
				}
			} catch (IOException e) {
				LOG.error("Error closing excel workbook! {}", e.getMessage());
			}
		}
	}
}
