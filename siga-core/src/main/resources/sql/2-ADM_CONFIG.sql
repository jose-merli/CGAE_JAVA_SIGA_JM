--------------------------------------------------------
--  SQL for Table ADM_CONFIG
--------------------------------------------------------

  CREATE TABLE "ADM_CONFIG" 
   (	
   	"ID" NUMBER, 
	"CLAVE" VARCHAR2(200 BYTE), 
	"VALOR" VARCHAR2(200 BYTE), 
	"DESCRIPCION" VARCHAR2(500 BYTE), 
	"VALOR_POR_DEFECTO" VARCHAR2(200 BYTE), 
	"NECESITA_REINICIO" NUMBER
   ) ;

  CREATE UNIQUE INDEX ADM_CONFIG_PK ON ADM_CONFIG ("ID");
  ALTER TABLE ADM_CONFIG ADD CONSTRAINT "ADM_CONFIG_PK" PRIMARY KEY ("ID");
  ALTER TABLE ADM_CONFIG MODIFY ("ID" NOT NULL ENABLE);
  
  COMMIT;

CREATE SEQUENCE seq_adm_config START WITH 1 INCREMENT BY 1 MAXVALUE 999999 MINVALUE 1;

Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'security.basic.enabled','false','Autenticacion de los usuarios mediante certificado y token de sesión JWT','false','1');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'security.loging.url','/login','Path de login','/login','1');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'security.login.method','GET','Método HTTP donde está definido el login','POST','1');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'management.security.enabled','false','Para activar los endpoints provistos por módulo actuator','true','1');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'spring.datasource.jndi-name','DataSourceSIGA_RW','Nombre de datasource definido en el weblogic',null,'1');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'cert-conf-path','C:/Oracle/wls/Oracle_Home/user_projects/domains/cgae/certificate-filter/access-control.xml','Ubicación del fichero de configuración de los OCSPs contra los que validar los certificados',null,'1');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'security.token.expiration-time','86400000','Tiempo de validez del token de sesion en ms','86400000','1');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'security.token.sign-key','1234','Clave para firmar los tokens de sesión','1234','1');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'security.token.header-auth-key','Authorization','Nombre de la cabecera de autenticación que contiene el token de sesion','Authorization','1');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'security.token.prefix','Bearer ','Prefijo del token','Bearer ','1');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'logging.pattern.console','%d{LLL-dd HH:mm:ss.SSS} %-5level [%.56thread] --- %.40logger : %msg%n','Patrón de logs',null,'0');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'log4j.siga.web.file','/home/managedxd/wls12213/user_projects/domains/cgae/servers/cgaeServer/logs/siga-web.log','Fichero general de logs de siga',null,'0');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'log4j.siga.web.path','siga-web','Pattern de rotado del fichero',null,'0');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'log4j.siga.web.bck.number','5','Número de ficheros de logs almacenados',null,'0');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'log4j.siga.web.max.size','50mb','Tamaño máximo del fichero de log',null,'0');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'log4j.siga.web.nivel','DEBUG','Nivel de trazas del fichero de log',null,'0');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'url.ws.sociedades','https://vmcgaeap003.cloud.es.deloitte.com/sspp-publicacion/ws/PublicadorSociedadesService',null,'0');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'url.ws.censo','https://ecomdemo.redabogacia.org/eCOM/services/InfoColegiadosService','Url de acceso a los WS de Censo',null,'0');

commit;