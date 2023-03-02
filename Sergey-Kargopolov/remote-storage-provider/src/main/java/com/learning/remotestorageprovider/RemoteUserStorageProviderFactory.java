package com.learning.remotestorageprovider;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class RemoteUserStorageProviderFactory implements UserStorageProviderFactory<RemoteUserStorageProvider>{

	private static final String PROVIDER_NAME = "custom-storage-provider";
	
	@Override
	public RemoteUserStorageProvider create(KeycloakSession session, ComponentModel model) {
		return new RemoteUserStorageProvider(session,model);
	}

	@Override
	public String getId() {
		return PROVIDER_NAME;
	}

}
