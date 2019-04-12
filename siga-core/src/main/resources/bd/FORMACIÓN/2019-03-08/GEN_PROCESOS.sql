CREATE TABLE for_temacurso_persona (
    idpersona           NUMBER(10) NOT NULL,
    idtemacurso         NUMBER(10) NOT NULL,
    usumodificacion     NUMBER(10),
    fechamodificacion   DATE,
    idinstitucion       NUMBER(4) NOT NULL,
    fechabaja           DATE
);



CREATE INDEX for_temacurso_persona__idx ON
    for_temacurso_persona (
        idpersona
    ASC,
        idtemacurso
    ASC,
        idinstitucion
    ASC );

ALTER TABLE for_temacurso_persona ADD CONSTRAINT for_temacurso_persona_pk PRIMARY KEY ( idpersona,
idtemacurso );

ALTER TABLE for_temacurso_persona
    ADD CONSTRAINT for_temc_pers_for_curso_fk FOREIGN KEY ( idtemacurso )
        REFERENCES for_temacurso ( idtemacurso );
        
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaColegial.lopd','LOPD','0','1',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaColegial.lopd','LOPD#GL','0','4',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaColegial.lopd','LOPD','0','2',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaColegial.lopd','LOPD#EU','0','3',to_date('24/10/18','DD/MM/RR'),'0','19'); 
        
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Hoy','0','1',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Hoy#GL','0','4',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Avui','0','2',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Hoy#EU','0','3',to_date('24/10/18','DD/MM/RR'),'0','19');