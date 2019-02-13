  CREATE TABLE "CEN_SOLICMODIFCAMBIARFOTO" 
   (	
    "IDSOLICITUD" NUMBER NOT NULL ENABLE, 
	"MOTIVO" VARCHAR2(255 BYTE) NOT NULL ENABLE, 
	"IDINSTITUCION" NUMBER(4,0) NOT NULL ENABLE, 
	"IDPERSONA" NUMBER(10,0) NOT NULL ENABLE, 
	"FECHAMODIFICACION" DATE NOT NULL ENABLE, 
	"USUMODIFICACION" NUMBER(5,0) NOT NULL ENABLE, 
	"IDESTADOSOLIC" NUMBER(2,0) NOT NULL ENABLE, 
	"FOTOGRAFIA" VARCHAR2(255 BYTE) NOT NULL ENABLE,  
	"FECHAALTA" DATE NOT NULL ENABLE, 
	 CONSTRAINT "PK_CEN_SOLICMODIFCAMBIARFOTO" PRIMARY KEY ("IDSOLICITUD")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "TS_SIGA_IDX"  ENABLE, 
	 CONSTRAINT "FK_SOLICMODIFCAMBFOTO_ESTADO" FOREIGN KEY ("IDESTADOSOLIC")
	  REFERENCES "CEN_ESTADOSOLICITUDMODIF" ("IDESTADOSOLIC") DEFERRABLE ENABLE, 
	 CONSTRAINT "FK_SOLICMODIFCAMBFOTO_CLIENTE" FOREIGN KEY ("IDINSTITUCION", "IDPERSONA")
	  REFERENCES "CEN_CLIENTE" ("IDINSTITUCION", "IDPERSONA") DEFERRABLE ENABLE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "TS_SIGA" ;
 

  CREATE INDEX "IND_FK_SOLICMODIFCAMFOTO_CLI" ON "CEN_SOLICMODIFCAMBIARFOTO" ("IDINSTITUCION", "IDPERSONA") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "TS_SIGA_IDX" ;
 

  CREATE INDEX "IND_FK_SOLICMODIFCAMBFOTO_EST" ON "CEN_SOLICMODIFCAMBIARFOTO" ("IDESTADOSOLIC") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "TS_SIGA_IDX" ;
  
  CREATE SEQUENCE  "USCGAE"."SEQ_CENSOLICMODIFCAMBIARFOTO"  MINVALUE 0 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  CYCLE; 