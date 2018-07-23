CREATE TABLE cmn_datos_xml ( 
--  primary key generada por la secuencia: SEQ_
    id_datos_xml   NUMBER(10) NOT NULL,
    tipo_xml       NUMBER(2) NOT NULL,
    modulo         NUMBER(2) NOT NULL,
    fecha          TIMESTAMP NOT NULL,
    xml            CLOB NOT NULL,
    id_datos_xml_rel     NUMBER(10)
)
TABLESPACE ts_cargas_lob LOGGING;

COMMENT ON COLUMN cmn_datos_xml.id_datos_xml IS
    'primary key generada por la secuencia: SEQ_CMN_DATOS_XML';

ALTER TABLE cmn_datos_xml ADD CONSTRAINT pk_cmn_datos_xml PRIMARY KEY ( id_datos_xml );


CREATE SEQUENCE seq_cmn_datos_xml START WITH 1 INCREMENT BY 1 MAXVALUE 999999999999999 MINVALUE 1;

Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'url.ws.sociedades','https://vmcgaeap003.cloud.es.deloitte.com/sspp-publicacion/ws/PublicadorSociedadesService','Url de acceso a los WS de Sociedades',null,'0');
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) values (SEQ_ADM_CONFIG.nextval,'url.ws.censo','https://ecomdemo.redabogacia.org/eCOM/services/InfoColegiadosService','Url de acceso a los WS de Censo',null,'0');



CREATE TABLE cargas_ws (
    id_carga             NUMBER(10) NOT NULL,
	id_estado_carga      NUMBER(2) NOT NULL,
    id_tipo_carga        NUMBER(2) NOT NULL,
    con_errores          NUMBER(1) DEFAULT 0 NOT NULL,
	usu_modificacion     NUMBER(5) NOT NULL,
    fecha_creacion       TIMESTAMP NOT NULL,    
    fecha_modificacion   TIMESTAMP NOT NULL,
	id_institucion       NUMBER(10),
	numero_peticion      VARCHAR2(20),
	total_paginas        NUMBER(4),
	fecha_fin_de_proceso TIMESTAMP(6)
)
TABLESPACE ts_cargas LOGGING;
COMMENT ON TABLE cargas_ws IS 'Tabla que almacena las cargas realizadas ya sean mediante cliente o mediante servicio';


COMMENT ON COLUMN cargas_ws.id_carga IS
    'primary key generada por la secuencia: SEQ_CARGAS_WS';

CREATE UNIQUE INDEX pk_cargas_ws_idx ON
    cargas_ws ( id_carga ASC )
        LOGGING;

ALTER TABLE cargas_ws ADD CONSTRAINT pk_cargas_ws PRIMARY KEY ( id_carga );


CREATE SEQUENCE seq_cargas_ws START WITH 1 INCREMENT BY 1 MAXVALUE 999999999999999 MINVALUE 1;

CREATE TABLE cargas_ws_pagina ( 
--  primary key generada por la secuencia: SEQ_CARGAS_WS_PAGINA
    id_ws_pagina     NUMBER(10) NOT NULL,
    id_carga         NUMBER(10) NOT NULL,
	id_datos_xml     NUMBER(10) NOT NULL,
    fecha_peticion   TIMESTAMP NOT NULL,
	fecha_creacion   TIMESTAMP NOT NULL,    
	num_pagina       NUMBER(4),  
    cod_error        VARCHAR2(20)
    
)
TABLESPACE ts_cargas LOGGING;
COMMENT ON TABLE cargas_ws_pagina IS 'Tabla que almacena las paginas recibidas de una carga de sociedades';


CREATE UNIQUE INDEX pk_cargas_ws_pag_idx ON
    cargas_ws_pagina ( id_ws_pagina ASC ) TABLESPACE ts_cargas_idx LOGGING;

ALTER TABLE cargas_ws_pagina ADD CONSTRAINT pk_cargas_ws_pag PRIMARY KEY ( id_ws_pagina );

CREATE SEQUENCE seq_cargas_ws_pagina START WITH 1 INCREMENT BY 1 MAXVALUE 999999999999999 MINVALUE 1;



CREATE TABLE cfg_param_colegios ( 
--  primary key generada por la secuencia: SEQ_CFG_PARAM_COLEGIOS
    id_param_colegio         NUMBER(10) NOT NULL,	
    id_institucion       NUMBER(10) NOT NULL,
    nombre               VARCHAR2(255) NOT NULL,	
    modulo_app           VARCHAR2(50) NOT NULL,
    descripcion          VARCHAR2(1024),
    valor                VARCHAR2(1024), 
    comentario           VARCHAR2(1024),
	fecha_modificacion   TIMESTAMP,
    usu_modificacion     NUMBER(5)
    
)
TABLESPACE ts_sspp_sttc LOGGING;
COMMENT ON TABLE cfg_param_colegios IS    'Tabla que contiene todos los campos del mapa que tiene google map en su api';

COMMENT ON COLUMN cfg_param_colegios.modulo_app IS    'Módulo de la aplicación desde donde se usa';
CREATE INDEX cfg_param_colegio_idx ON
    cfg_param_colegios ( id_param_colegio ASC )
        TABLESPACE ts_sspp_sttc_idx LOGGING;

ALTER TABLE cfg_param_colegios ADD CONSTRAINT pk_cfg_param_colegios PRIMARY KEY ( id_param_colegio );

CREATE SEQUENCE seq_cfg_param_colegios START WITH 1 INCREMENT BY 1 MAXVALUE 999999999999999 MINVALUE 1;


UPDATE CEN_TIPOSOCIEDAD SET CODIGOEXT = LETRACIF

COMMIT;