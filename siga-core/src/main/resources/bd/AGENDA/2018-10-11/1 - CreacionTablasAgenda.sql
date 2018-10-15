

CREATE TABLE age_calendario (
    idcalendario        NUMBER(100) NOT NULL,
    idinstitucion       NUMBER(4),
    descripcion         VARCHAR2(200),
    usumodificacion     NUMBER(100),
    fechamodificacion   DATE,
    fechabaja           DATE,
    idtipocalendario    NUMBER(100) NOT NULL,
    color               VARCHAR2(100)
);

ALTER TABLE age_calendario ADD CONSTRAINT age_calendario_pk PRIMARY KEY ( idcalendario );

CREATE TABLE age_notificacionesevento (
    idnotificacionevento       NUMBER(100) NOT NULL,
    idinstitucion              NUMBER(4),
    usumodificacion            NUMBER(100),
    fechamodificacion          DATE,
    idtiponotificacionevento   NUMBER(100) NOT NULL,
    cuando                     NUMBER(100),
    idtipocuando               NUMBER(10) NOT NULL,
    idunidadmedida             NUMBER(10) NOT NULL,
    idcalendario               NUMBER(100) NOT NULL,
    idevento                   NUMBER(100),
    fechabaja                  DATE
);

ALTER TABLE age_notificacionesevento ADD CONSTRAINT age_notificaciones_pk PRIMARY KEY ( idnotificacionevento );

CREATE TABLE age_permisoscalendario (
    idpermisoscalendario   NUMBER(100) NOT NULL,
    idinstitucion          NUMBER(4),
    idperfil               VARCHAR2(100),
    usumodificacion        NUMBER(100),
    fechamodificacion      DATE,
    fechabaja              DATE,
    tipoacceso             NUMBER(10),
    idcalendario           NUMBER(100) NOT NULL
);

ALTER TABLE age_permisoscalendario ADD CONSTRAINT age_permisoscalendario_pk PRIMARY KEY ( idpermisoscalendario );

CREATE TABLE age_tipocalendario (
    idtipocalendario    NUMBER(100) NOT NULL,
    descripcion         VARCHAR2(200),
    usumodificacion     NUMBER(100),
    fechamodificacion   DATE,
    fechabaja           DATE
);

ALTER TABLE age_tipocalendario ADD CONSTRAINT age_tipocalendario_pk PRIMARY KEY ( idtipocalendario );

CREATE TABLE age_tiponotificacionevento (
    idtiponotificacionevento   NUMBER(100) NOT NULL,
    descripcion                VARCHAR2(200),
    usumodificacion            NUMBER(100),
    fechamodificacion          DATE,
    fechabaja                  DATE
);

ALTER TABLE age_tiponotificacionevento ADD CONSTRAINT age_tiponotificacionevento_pk PRIMARY KEY ( idtiponotificacionevento );

CREATE TABLE age_unidadmedida (
    idunidadmedida      NUMBER(10) NOT NULL,
    descripcion         VARCHAR2(100),
    usumodificacion     NUMBER(100),
    fechamodificacion   DATE,
    fechabaja           DATE
);

ALTER TABLE age_unidadmedida ADD CONSTRAINT age_unidadmedida_pk PRIMARY KEY ( idunidadmedida );

CREATE TABLE id_tipocuando (
    idtipocuando        NUMBER(10) NOT NULL,
    descripcion         VARCHAR2(100),
    usumodififacion     NUMBER(100),
    fechamodificacion   DATE,
    fechabaja           DATE
);

ALTER TABLE id_tipocuando ADD CONSTRAINT id_tipocuando_pk PRIMARY KEY ( idtipocuando );

ALTER TABLE age_calendario
    ADD CONSTRAINT age_cal_age_tpcal_fk FOREIGN KEY ( idtipocalendario )
        REFERENCES age_tipocalendario ( idtipocalendario );

ALTER TABLE age_notificacionesevento
    ADD CONSTRAINT age_notev_age_cal_fk FOREIGN KEY ( idcalendario )
        REFERENCES age_calendario ( idcalendario );

ALTER TABLE age_notificacionesevento
    ADD CONSTRAINT age_notev_age_tpnotev_fk FOREIGN KEY ( idtiponotificacionevento )
        REFERENCES age_tiponotificacionevento ( idtiponotificacionevento );

ALTER TABLE age_notificacionesevento
    ADD CONSTRAINT age_notevento_age_unidmed_fk FOREIGN KEY ( idunidadmedida )
        REFERENCES age_unidadmedida ( idunidadmedida );

ALTER TABLE age_notificacionesevento
    ADD CONSTRAINT age_notevento_id_tpcuando_fk FOREIGN KEY ( idtipocuando )
        REFERENCES id_tipocuando ( idtipocuando );

ALTER TABLE age_permisoscalendario
    ADD CONSTRAINT age_permcale_age_calendario_fk FOREIGN KEY ( idcalendario )
        REFERENCES age_calendario ( idcalendario );

