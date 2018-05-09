--------------------------------------------------------
--  SQL for Table ADM_CONFIG
--------------------------------------------------------

  CREATE TABLE "DEMO_SOCIEDADES"."ADM_CONFIG" 
   (	
   	"ID" NUMBER, 
	"CLAVE" VARCHAR2(200 BYTE), 
	"VALOR" VARCHAR2(200 BYTE), 
	"DESCRIPCION" VARCHAR2(500 BYTE), 
	"VALOR_POR_DEFECTO" VARCHAR2(200 BYTE), 
	"NECESITA_REINICIO" NUMBER
   ) ;

  CREATE UNIQUE INDEX "DEMO_SOCIEDADES"."ADM_CONFIG_PK" ON "DEMO_SOCIEDADES"."ADM_CONFIG" ("ID");
  ALTER TABLE "DEMO_SOCIEDADES"."ADM_CONFIG" ADD CONSTRAINT "ADM_CONFIG_PK" PRIMARY KEY ("ID");
  ALTER TABLE "DEMO_SOCIEDADES"."ADM_CONFIG" MODIFY ("ID" NOT NULL ENABLE);
  
  COMMIT;
  
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('1','security.basic.enabled','false','Autenticacion de los usuarios mediante certificado y token de sesión JWT','false','1');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('2','security.loging.url','/login','Path de login','/login','1');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('3','security.login.method','GET','Método HTTP donde está definido el login','POST','1');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('4','management.security.enabled','false','Para activar los endpoints provistos por módulo actuator','true','1');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('5','spring.datasource.jndi-name','DataSource_SSPP','Nombre de datasource definido en el weblogic',null,'1');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('6','cert-conf-path','C:/Oracle/wls/Oracle_Home/user_projects/domains/cgae/certificate-filter/access-control.xml','Ubicación del fichero de configuración de los OCSPs contra los que validar los certificados',null,'1');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('7','security.token.expiration-time','86400000','Tiempo de validez del token de sesion en ms','86400000','1');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('8','security.token.sign-key','1234','Clave para firmar los tokens de sesión','1234','1');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('9','security.token.header-auth-key','Authorization','Nombre de la cabecera de autenticación que contiene el token de sesion','Authorization','1');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('10','security.token.prefix','Bearer ','Prefijo del token','Bearer ','1');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('11','logging.level.org.springframework.web','INFO','Nivel de debug de framework spring','INFO','0');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('12','logging.level.root','INFO','Nivel de logging raiz','INFO','0');
Insert into DEMO_SOCIEDADES.ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values ('13','logging.pattern.console','%d{LLL-dd HH:mm:ss.SSS} %-5level [%.56thread] --- %.40logger : %msg%n','Patrón de logs',null,'0');

