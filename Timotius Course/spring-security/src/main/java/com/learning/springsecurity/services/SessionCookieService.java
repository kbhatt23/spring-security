package com.learning.springsecurity.services;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.learning.springsecurity.constants.SessionCookieConstants;
import com.learning.springsecurity.dtos.SessionCookieToken;
import com.learning.springsecurity.util.HashUtil;
import com.learning.springsecurity.util.SecureStringUtil;

@Service
public class SessionCookieService {

	public String store(HttpServletRequest request, SessionCookieToken token) {

		// session fixation fix
		HttpSession existingSession = request.getSession(false);
		if (existingSession != null)
			existingSession.invalidate();

		// if session not present create one and store token in session cookie
		HttpSession session = request.getSession(true);

		session.setAttribute(SessionCookieConstants.SESSION_ATTRIBUTE_USERNAME, token.getUserName());

		// to prevent CSRF return hashed session id
		// after login client get session id hashed
		// during each call of other apis he must pass the validation header

		try {
			return HashUtil.sha256(session.getId(), token.getUserName());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Optional<SessionCookieToken> read(HttpServletRequest request, String csrfTokenID) {

		// if session do not exist or empty then that is bad request
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(SessionCookieConstants.SESSION_ATTRIBUTE_USERNAME) == null
				|| csrfTokenID == null) {
			return Optional.empty();
		}

		String userName = (String) session.getAttribute(SessionCookieConstants.SESSION_ATTRIBUTE_USERNAME);
		try {
			String currentSessionHash = HashUtil.sha256(session.getId(), userName);
			// prevent timing attack
			if (!SecureStringUtil.equals(currentSessionHash, csrfTokenID)) {
				return Optional.empty();
			}

			SessionCookieToken sessionCookieToken = new SessionCookieToken();
			sessionCookieToken.setUserName(userName);

			return Optional.of(sessionCookieToken);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Optional.empty();

	}
	
	//logout functionality
	//this will be open only for logged in valid user only
	public void deleteSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.invalidate();
	}
}
