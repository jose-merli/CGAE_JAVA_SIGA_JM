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

		    