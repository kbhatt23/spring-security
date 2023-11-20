package com.learning.ldapinjection.helper;

import java.util.Hashtable;
import java.util.Optional;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LdapHelper {

	@Value("${ldap.url}")
	private String ldapUrl;

	@Value("${ldap.base.dn}")
	private String ldapBaseDn;

	@Value("${ldap.username}")
	private String ldapUsername;

	@Value("${ldap.password}")
	private String ldapPassword;

	public Optional<SearchResult> queryLdap(String query, Object... params) {
		String formattedQuery = String.format(query, params);
		log.info("Querying user with {}", formattedQuery);
		SearchControls constraints = new SearchControls();
		constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
		InitialLdapContext ldapContext = null;
		NamingEnumeration<SearchResult> searchResult = null;

		try {
			ldapContext = connect();
			searchResult = ldapContext.search("", formattedQuery, constraints);
			if (searchResult.hasMore()) {
				log.info("Returning result for query {}", formattedQuery);
				return Optional.of(searchResult.next());
			}
		} catch (NamingException e) {
			log.error("Error during ldap query: {}", formattedQuery);
		} finally {
			try {
				if (searchResult != null) {
					searchResult.close();
				}
				if (ldapContext != null) {
					ldapContext.close();
				}
			} catch (NamingException e) {
				log.error("Could not connect to Ldap: {}", e.getMessage());
			}
		}
		return Optional.empty();
	}

	private InitialLdapContext connect() {
		Hashtable<String, Object> env = getLdapEnvironment();
		InitialLdapContext ldapContext;
		try {
			ldapContext = new InitialLdapContext(env, null);
		} catch (NamingException e) {
			log.error("Error connecting to Ldap server: {}, cause: {}", ldapUrl, e.getCause().getMessage());
			throw new RuntimeException("Error connecting to Ldap server: " + ldapUrl);
		}
		log.info("Connected to Ldap server: {}", ldapUrl);
		return ldapContext;
	}

	private Hashtable<String, Object> getLdapEnvironment() {
		validateLdapEnvironment();
		Hashtable<String, Object> env = new Hashtable<>();
		env.put(Context.PROVIDER_URL, ldapUrl + ldapBaseDn);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
		env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
		return env;
	}

	private void validateLdapEnvironment() {
		if (!StringUtils.hasLength(ldapUrl) || !StringUtils.hasLength(ldapBaseDn)
				|| !StringUtils.hasLength(ldapUsername) || !StringUtils.hasLength(ldapPassword)) {
			throw new RuntimeException("Ldap environment does not set up properly!");
		}
	}
}
