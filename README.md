# spring-security
Security concepts using spring

Project overview:
auth-server: This acts as a custom oauth server for a clinet with project name hello-world-custom-oauth 
			-> Here we can define password/authroization grant users and different clients
			-> this exposes Endpoints like http://localhost:9090/auth/oauth/token
										   http://localhost:9090/auth/oauth/check_token?token=
										

hello-world-custom-oauth: A client project that act as a client for oauth 2 server present at auth-server project

auth-server-jwt: Auth server project with JWT implementation, needs vault server to be up and apis should be sealed
			Here we can define password/authroization grant users and different clients
			-> this exposes Endpoints like http://localhost:9090/auth/oauth/token
										   http://localhost:9090/auth/oauth/token_key : public key so that jwt can be used

hello-world: Project having basic authentication using spring for httpbasic and formbasic, Contatins jbbc, inmemory data configurations

hello-world-google-oauth2: A project that acts as client for Google oauth server configured

hello-world-https: HTTPS implementation of hello-world project

resource-server: This project act as resource server, Exposing apis and do chekc_token flow on each api request to its controller using interceptors
				Uses APIS: http://localhost:9090/auth/oauth/token
						   http://localhost:9090/auth/oauth/check_token?token=
			
resource-server-jwt: This project act as resource server, Exposing apis and do oauth/token and  http://localhost:9090/auth/oauth/token_key :public key token 
				Uses APIS: http://localhost:9090/auth/oauth/token
						   http://localhost:9090/auth/oauth/token_key 		

Endpoints:
http://localhost:9090/auth/oauth/token
http://localhost:9090/auth/oauth/check_token?token=
http://localhost:9091/resource?access_token=
