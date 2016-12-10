CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));
CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id));

INSERT INTO users(username,password,enabled)
VALUES ('reader','reader', true);
INSERT INTO users(username,password,enabled)
VALUES ('writer','writer', true);

INSERT INTO user_roles (username, role)
VALUES ('reader', 'reader');
INSERT INTO user_roles (username, role)
VALUES ('writer', 'reader');
INSERT INTO user_roles (username, role)
VALUES ('writer', 'B6_WRITER');

drop table if exists oauth_client_details;
        create table oauth_client_details (
        client_id VARCHAR(255) PRIMARY KEY,
        resource_ids VARCHAR(255),
        client_secret VARCHAR(255),
        scope VARCHAR(255),
        authorized_grant_types VARCHAR(255),
        web_server_redirect_uri VARCHAR(255),
        authorities VARCHAR(255),
        access_token_validity INTEGER,
        refresh_token_validity INTEGER,
        additional_information VARCHAR(4096),
        autoapprove VARCHAR(255)
        );
INSERT INTO oauth_client_details (client_id,resource_ids,client_secret,
  scope,authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity,additional_information, autoapprove)
VALUES('reader','reader','reader',
'reader', 'reader', 'www.flytxt.com','someAuth',
10,10,'some additional_information', 'true');