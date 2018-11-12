--------------------------------------------------------
--  DDL for Table CEN_REG_MERCANTIL
--------------------------------------------------------

  CREATE TABLE "USCGAE"."CEN_REG_MERCANTIL" 
   (	"ID_DATOS_REG" NUMBER(10,0), 
	"NUM_REGISTRO" VARCHAR2(20 BYTE), 
	"IDENTIFICACION_REG" VARCHAR2(10 BYTE), 
	"FECHA_INSCRIPCION" DATE, 
	"FECHA_CANCELACION" DATE, 
	"FECHAMODIFICACION" DATE, 
	"USUMODIFICACION" NUMBER(5,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "TS_SIGA" ;
 

   COMMENT ON TABLE "USCGAE"."CEN_REG_MERCANTIL"  IS 'Esta tabla sirve para almacenar los datos del registro mercantil de la sociedad';
--------------------------------------------------------
--  DDL for Index PK_REG_MERCANTIL
--------------------------------------------------------

  CREATE UNIQUE INDEX "USCGAE"."PK_REG_MERCANTIL" ON "USCGAE"."CEN_REG_MERCANTIL" ("ID_DATOS_REG") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "TS_SIGA_IDX" ;
--------------------------------------------------------
--  Constraints for Table CEN_REG_MERCANTIL
--------------------------------------------------------

  ALTER TABLE "USCGAE"."CEN_REG_MERCANTIL" ADD CONSTRAINT "PK_REGISTRO_MERCANTIL" PRIMARY KEY ("ID_DATOS_REG");
 
  ALTER TABLE "USCGAE"."CEN_REG_MERCANTIL" MODIFY ("ID_DATOS_REG" NOT NULL ENABLE);
 
  ALTER TABLE "USCGAE"."CEN_REG_MERCANTIL" MODIFY ("NUM_REGISTRO" NOT NULL ENABLE);
 
  ALTER TABLE "USCGAE"."CEN_REG_MERCANTIL" MODIFY ("IDENTIFICACION_REG" NOT NULL ENABLE);
 
  ALTER TABLE "USCGAE"."CEN_REG_MERCANTIL" MODIFY ("FECHA_INSCRIPCION" NOT NULL ENABLE);

  ALTER TABLE "USCGAE"."CEN_NOCOLEGIADO"   ADD ID_DATOS_REG NUMBER(10,0) NULL;
  
  ALTER TABLE "USCGAE"."CEN_NOCOLEGIADO" ADD CONSTRAINT FK_DATOS_SOCIEDAD_MERCANTIL FOREIGN KEY (ID_DATOS_REG) REFERENCES "USCGAE"."CEN_REG_MERCANTIL"(ID_DATOS_REG);
  
  
  GRANT SELECT ON USCGAE.CEN_REG_MERCANTIL TO ROLE_SIGA_R; 
  GRANT DELETE, INSERT, SELECT, UPDATE ON USCGAE.CEN_REG_MERCANTIL TO ROLE_SIGA;
