Insert into GEN_PROPERTIES (FICHERO,PARAMETRO,VALOR) values ('SIGA','scs.ficheros.inscripciones','inscripciones');

ALTER TABLE FOR_INSCRIPCIONESMASIVAS ADD IDFICHEROLOG NUMBER(10,0);
ALTER TABLE FOR_INSCRIPCIONESMASIVAS ADD IDFICHERO NUMBER(10,0);

CREATE TABLE age_persona_evento (
    idformadorevento    NUMBER(10) NOT NULL,
    idpersona           NUMBER(10) NOT NULL,
    idevento            NUMBER(10) NOT NULL,
    usumodificacion     NUMBER(10),
    fechamodificacion   DATE,
    idinstitucion       NUMBER(4) NOT NULL,
    fechabaja           DATE
);

ALTER TABLE age_persona_evento ADD CONSTRAINT age_persona_evento_pk PRIMARY KEY ( idformadorevento );



ALTER TABLE age_persona_evento
    ADD CONSTRAINT age_per_even_age_even_fk FOREIGN KEY ( idevento )
        REFERENCES age_evento ( idevento );
        
CREATE SEQUENCE  SEQ_AGEPERSONAEVENTO  MINVALUE 0 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  CYCLE;         
commit;