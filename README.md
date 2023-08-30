# Preparations
## Update iam-keycloak-poc-realm.json file
Go to Keycloak console and in left menu find `Export`. <p>
Select all sliders click `Export` and confirm export. <p>
![img.png](img.png) <p>
Open file and copy all json to `iam-keycloak-poc-realm.json` file in project.<p>
Now all configurations from previous task is imported into this. <p>

# Configuring role based access to endpoints
## Configuring endpoints

### Step 1. Add new dependency into gradle file
To work with Spring Security using Keycloak you need to add this dependency 
(`'org.springframework.boot:spring-boot-starter-oauth2-resource-server'`)
to `gradle.build` file.

### Step 2. Create WebSecurity class
Create new class `WebSecurity`.
Add annotations `@Configuration` and `@EnableWebSecurity`. <p>
`@EnableWebSecurity` annotation works only for `@Configuration` class. 
It allows to configure Spring Security configuration defined in any `WebSecurityConfigurer` class.<p>

### Step 3. Configure role based access
In newest versions of Spring Security you need to define `@Beans` with needed configurations.<p>
For role based configuration you need to define `SecurityFilterChain` bean. <p>
For role based configuration you also need converter for Keycloak roles. <p>
Here is code for converter class:

    private static class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

            if (realmAccess == null || realmAccess.isEmpty()) {
                return new ArrayList<>();
            }

            Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles"))
                    .stream().map(roleName -> "ROLE_" + roleName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return returnValue;
        }

    }
In this class you override base `convert` function of class `Converrter`.<p>
It allows to manipulate with converting role by you own.<p>
The main difference between simple JWT converter and this is that you need to get all data 
from `realm_access` key.<p> 
Also as we are using role based approach we need to match with`ROLE_` prefix in role name. <p>

Now we can define `SecurityFilterChain` bean<p>
Here is code example: 

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/users/welcome")
                .permitAll()
                .requestMatchers("/employees/search", "/some/manager/access")
                .hasAnyRole("manager", "other_important_role")
        ).oauth2ResourceServer(oauth2 -> oauth2
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter)
        );

        return http.build();
    }

Firstly we define `JwtAuthenticationConverter` to convert JWT that comes from Keycloak.<p>
Then using `HttpSecurity` we define all what type of access each endpoint have. <p>
For example `/users/welcome` endpoint can access anybody as it has `permitAll()` grant.<p>
For `/employees/search` there is direct configuration that this endpoint can only 
access users that have role `manager`. Also `requestMatchers()` and `hasAnyRole()` functions
allow you to define any number of endpoints and roles. Look at the example above.
After configuring role based access to endpoint you need to configure OAuth2 resource server support
and define how to work with JWT token.<p>
As you previously define hwo to work with JWT from Keycloak you need to define converter for OAuth2
in `jwtAuthenticationConverter()`.

### Step 4. Final configurations to communicate with Keycloak
In `application.yaml` file you need to define where jwt token will be verified<p>

    security:
      oauth2:
        resource-server:
          jwt:
            jwk-set-uri: ${KEYCLOAK_JWK_SET_URI:http://localhost:8080/auth/realms/iam-keycloak-poc/protocol/openid-connect/certs}

You can find this URI using this URL: http://{keycloak_host:keycloak_port}/auth/realms/{your_realm_name}/.well-known/openid-configuration <p>
Replace `{keycloak_host:keycloak_port}` with your host and port and `{your_realm_name}` with your realm name. <p>
There you can see different URI for JWT validation. Any of them can be used in configuration. <p>
In our approach we are using jwk URI, so find your JWK URI by key `jwks_uri` and copy it into 
`jwk-set-uri` property in `application.yaml`. <p>
Also if you run your app via `docker-compose up` command to control your jwk URI you need to define environment key in `docker-compose.yaml` file for `iam-app` container 
as `KEYCLOAK_JWK_SET_URI=http://keycloak:8080/auth/realms/iam-keycloak-poc/protocol/openid-connect/certs`. <p>
Notice that for working host we are using `keycloak`, the name of container in with Keycloak is running.<p>

### Step 5. Testing using Postman.

Firstly let's create new user that doesn't have `manager` role. Use previous guid to do it.
Log in using new user. Try to call both API endpoints of service and you should see
this result:
![img_3.png](img_3.png) <p>
![img_4.png](img_4.png) <p>
After you verified that new user cannot access to `Get All Employees By Regex Name` 
endpoint try to log in using manager user. and you should see this result:
![img_1.png](img_1.png) <p>
![img_2.png](img_2.png) <p>

If everything is working as shown on pictures, then you have configured all successfully.