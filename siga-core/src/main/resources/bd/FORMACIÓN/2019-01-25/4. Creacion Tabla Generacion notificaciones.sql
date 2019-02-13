CREATE TABLE age_generacionnotificaciones (
    idgeneracionnotificacion      NUMBER(10) NOT NULL,
    usumodificacion               NUMBER(10),
    fechamodificacion             DATE,
    fechabaja                     DATE,
    idinstitucion                 NUMBER(4),
    idtiponotificacionevento      NUMBER(10) NOT NULL,
    fechageneracionnotificacion   DATE,
    idevento                      NUMBER(10) NOT NULL,
    idnotificacionevento          NUMBER(10) NOT NULL,
    flagenviado                   VARCHAR2(2)
);

ALTER TABLE age_generacionnotificaciones ADD CONSTRAINT age_gennotif_pk PRIMARY KEY ( idgeneracionnotificacion );

ALTER TABLE age_generacionnotificaciones
    ADD CONSTRAINT age_gennot_age_evento_fk FOREIGN KEY ( idevento )
        REFERENCES age_evento ( idevento );

ALTER TABLE age_generacionnotificaciones
    ADD CONSTRAINT age_gennot_age_noteve_fk FOREIGN KEY ( idnotificacionevento )
        REFERENCES age_notificacionesevento ( idnotificacionevento );

ALTER TABLE age_generacionnotificaciones
    ADD CONSTRAINT age_gennot_age_tiponotev_fk FOREIGN KEY ( idtiponotificacionevento )
        REFERENCES age_tiponotificacionevento ( idtiponotificacionevento );
CREATE SEQUENCE  "SEQ_AGEGENERANOTIFICACIONES"  MINVALUE 0 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  CYCLE;  
        
Insert into ADM_CONFIG (ID,CLAVE,VALOR,DESCRIPCION,VALOR_POR_DEFECTO,NECESITA_REINICIO) 
values (SEQ_ADM_CONFIG.NEXTVAL,'cron.pattern.scheduled.envioNotificacionesEventos','0 * * ? * *','Periodo de ejecución del cron para enviar notificaciones de eventos','0 * * ? * *','1'); 		    