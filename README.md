# What are micro-services?
A postman will only do mail delivery not also laundadry. My bad, the pipe program in *nux systen can only send the output of one program to another program nothing less nothing more. If processing is needed use another program. Similarly, Microservices is a specialisation of an implementation approach for service-oriented architectures (SOA) used to build flexible, independently deployable software systems, wherein each service will do just one thing.
Representational state transfer** (REST)**  or RESTful Web services are one way of providing interoperability between computer systems on the Internet, & they provide capability to access and manipulate textual representations of Web resources using a uniform and predefined set of stateless operations, which are inturn a great way of expressing micro-services. for eg lets say, there is a rest interface to manage user which lets say is our user-management-micro-service; the next big thing is how to secure it (authorize and authenticate)? **JWT** can help

# What is JSON Web Token?

JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact and self-contained way for securely transmitting information between parties as a JSON object. This information can be verified and trusted because it is digitally signed. JWTs can be signed using a secret (with the HMAC algorithm) or a public/private key pair using RSA.

# JWT & MICRO-services
REST based services are stateless by nature until we add security, but this needn't be the case if JWT is used. OAuth is another option but its complex, bloated & service to service communication without a web browser is tricky.
With JWT there are following entities 
-- User
-- Client
-- Auth server
-- Services
TODO: flow diagram

# Project Overview
There is  **single**  Authorization Server responsible to issue access token which can be consumed by  **multiple**  Resource Servers.  In overall Spring Security authentication includes 2 steps, creating an authentication object for each request and applying authorization check depending on authentication.

Following are the basic componets in  JWT (jot) based  Authetication service  framework

1. Autherization server
The server which is responsible for managing authorizations and issuing access tokens to the  **clients**  for validating the  **Resource** identity. Authentication server is generating the token in JWT format, which is encrypted by using the Public /private key mechanism. An Authentication server contains following components.

  1. Data source configuration
  H2 database is embedded with the authorization server, which is used to store the client configurations for validate the client. The client details are kept inside e the **oauth\_client\_details** table

  2. Web security configuration
  This is an extension of spring _WebSecurityConfigurerAdapter_. Enable the web-security parameters and user authentication provider set through this implementation.

  3. Oauth2 Token generator
  This is an extension of spring AuthorizationServerConfigurerAdapter_._ Which willcreate a jwt token store.  (Using the key tool to generate the key store, which contains the public/ private key) and the token is getting encrypted by using this private key. The public key is shared across the Resource server for decrypt the token. Please make sure that all the resource server contains the same public key when the key is again generated.

  4. User Authentication
  Currently User authentication can done by following two ways
    1. LDAP authentication
    For LDAP authentication, Authorization server needs to configure the below property in application.properties file under the resource directory.

| authentication.AuthenticationBuilder=com.flytxt.security.jwtoauthserver.authBuilder.DBAuthenticationBuilder |
| --- |

If the authentication type is LDAP, it looking some more parameters from the application.properties **active directory domain name** and **directory URL**. Please find the following example configuration.

Configure the organizational unit and appropriate role along with the directory URL, if required

| authentication.mode.ldap.activeDirectoryDomain=flytxt.com 
  authentication.mode.ldap.activeDirectoryUrl=ldap://192.168.125.10:389 
|

    
    2. JDBC based authtication (DB)
    Which is the traditional way of the user validation with user name, password and roles which are validated against the database table **users** and **Use\_roles.**

Configure the spring property authentication.AuthenticationBuilderin application .properties

| authentication.AuthenticationBuilder=com.flytxt.security.jwtoauthserver.authBuilder.LdapAuthenticationBuilder |
| --- |

2. Resource Server

The server which hosts the protected resources, this server should be able to accept the access tokens issued by the  **Authorization Server**  and respond with the protected resource if the access token is valid.

# **How authorization work?**

Each client resource service will send a request to Authorization server for the authorization token and the authorization server send back the jwt token which is encrypted by a private key.  The client request is proceed with this token. A resource server embedded with each resource service, which validate each resource request. The public key associated with the resource server is using to decrypt the token.

##### Common Configuration

oauth\_client\_details

| client\_id | Client Id EG : web\_app |
| --- | --- |
| resource\_ids | Comma separated resource ids   |
| client\_secret | Client secret key , |
| scope | Scope eg: read,write |
| authorized\_grant\_types | Possible values are :  implicit, refresh\_token, client\_credentials, password, authorization\_code |
| web\_server\_redirect\_uri | Redirect URL |
| authorities | Role/ User , multiple values are Comma separated |
| access\_token\_validity | Token validity period in seconds |
| refresh\_token\_validity | Refresh Token validity period in seconds |
| additional\_information | Additional information |
| autoapprove | Auto approve |

Set the **resource id** through the resource application.yaml

Please find the below sample resource configuration

resource:

 id: foo

##### **How to write a Resource API?**

For embed a resource server, import the following configuration class from the resource server to resource application.

@Import ({ResourceServerConfig. **class** , JwtConfig.class})

Configure the API service with appropriate security. Please refer the below Example

@PreAuthorize(&quot;hasAuthority(&#39;userid/role&#39;)&quot;)

@RequestMapping(method = RequestMethod. **GET** )

**public** String readFoo() {

      **return**&quot;read foo &quot;;

##### }

##### **How JWT Autherization with LDAP works?**

Set the following properties in **application.properties**

_authentication.AuthenticationBuilder=com.flytxt.security.jwtoauthserver.authBuilder.LdapAuthenticationBuilder_

_authentication.mode.ldap.activeDirectoryDomain= flytxt.com_     ( **Domain Name** )

_authentication.mode.ldap.activeDirectoryUrl=ldap://192.168.125.10:389_ ( **URL** )

##### **How JWT AUTHERIZATION WITH DB works?**

Set the following database authentication builder properties in **application.properties**

_authentication.AuthenticationBuilder=com.flytxt.security.jwtoauthserver.authBuilder.__DBAuthenticationBuilder_

_The user and user roles are configure in the tables, users and user\_role._

###### **Generating the token with grant type password.**

curl -u **&lt;client Id :client**  **secret &gt;**   **&lt;token server URL&gt;** -d &quot;grant\_type=password&amp; username= &lt;user\_name&gt; &amp;password=&lt;pwd&gt;&quot;

_Eg:  curl -u web\_app:client\_pwd http://localhost:9000/oauth/token -d &quot;grant\_type=password &amp;username=john&amp;password=john\_pwd&quot;_

The user name and password are from active directory user name and pwd in the case of ldap authentication and in case of Database the credentials are validated against the user and role table.

###### **Generating the token with grant type client credentials.**

_Eg: curl -u web\_app:client\_pwd http://localhost:9000/oauth/token -d &quot;grant\_type= client \_credentials&quot;._

The client application should invoke the appropriate web-request for generating the token and set the token while requesting for a resource.

##### Invoke the resource with generated Token

curl -H &quot;Authorization: Bearer $TOKEN&quot; &quot;localhost:9001/foo&quot;
