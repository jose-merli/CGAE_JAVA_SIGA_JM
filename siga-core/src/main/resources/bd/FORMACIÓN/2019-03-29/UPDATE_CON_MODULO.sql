UPDATE CON_MODULO SET NOMBRE = 'Formación' WHERE IDMODULO = '11';


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



ALTER TABLE FOR_PERSONA_CURSO
DROP CONSTRAINT FOR_PER_CURSO_FOR_ROLES_FK;

ALTER TABLE for_roles
DROP CONSTRAINT FOR_ROLES_PK;


ALTER TABLE for_roles
ADD CONSTRAINT FOR_ROLES_PK PRIMARY KEY (IDROL,IDINSTITUCION);

ALTER TABLE for_persona_curso
    ADD CONSTRAINT for_per_curso_for_roles_fk FOREIGN KEY ( idrol,IDINSTITUCION )
        REFERENCES for_roles ( idrol,IDINSTITUCION );

		
		ALTER TABLE FOR_TEMACURSO_CURSO
DROP CONSTRAINT FOR_TEMACU_CURSO_FOR_TEM_FK;

ALTER TABLE FOR_TEMACURSO_PERSONA
DROP CONSTRAINT FOR_TEMC_PERS_FOR_CURSO_FK;

ALTER TABLE FOR_TEMACURSO
DROP CONSTRAINT FOR_TEMACURSO_PK;


ALTER TABLE FOR_TEMACURSO
ADD CONSTRAINT FOR_TEMACURSO_PK PRIMARY KEY (IDTEMACURSO,IDINSTITUCION);

ALTER TABLE FOR_TEMACURSO_CURSO
    ADD CONSTRAINT FOR_TEMACU_CURSO_FOR_TEM_FK FOREIGN KEY ( IDTEMACURSO,IDINSTITUCION )
        REFERENCES FOR_TEMACURSO ( IDTEMACURSO,IDINSTITUCION );

ALTER TABLE FOR_TEMACURSO_PERSONA
    ADD CONSTRAINT FOR_TEMC_PERS_FOR_CURSO_FK FOREIGN KEY ( IDTEMACURSO,IDINSTITUCION )
        REFERENCES FOR_TEMACURSO ( IDTEMACURSO,IDINSTITUCION );
		
		
		
		
		
Insert into GEN_CATALOGOS_MULTIIDIOMA (CODIGO,NOMBRETABLA,CAMPOTABLA,FECHAMODIFICACION,USUMODIFICACION,LOCAL,CODIGOTABLA,MIGRADO) 
values ((select max(codigo) +1 from GEN_CATALOGOS_MULTIIDIOMA),'FOR_TEMACURSO','DESCRIPCION',sysdate,'0','N','IDTEMACURSO','S');
Insert into GEN_CATALOGOS_MULTIIDIOMA (CODIGO,NOMBRETABLA,CAMPOTABLA,FECHAMODIFICACION,USUMODIFICACION,LOCAL,CODIGOTABLA,MIGRADO) 
values ((select max(codigo) +1 from GEN_CATALOGOS_MULTIIDIOMA),'FOR_ROLES','DESCRIPCION',sysdate,'0','N','IDROL','S');
		








Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.aprobadas','inscripciones aprobadas','0','1',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.aprobadas','inscripcions aprovades','0','2',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.aprobadas','inscripciones aprobadas#EU','0','3',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.aprobadas','inscripciones aprobadas#GL','0','4',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.canceladas','inscripciones canceladas','0','1',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.canceladas','inscripció cancel·lades','0','2',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.canceladas','inscripciones canceladas#EU','0','3',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.canceladas','inscripciones canceladas#GL','0','4',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.rechazadas','inscripciones rechazadas','0','1',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.rechazadas','inscripció rebutjades','0','2',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.rechazadas','inscripciones rechazadas#EU','0','3',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripciones.rechazadas','inscripciones rechazadas#GL','0','4',to_date('13/12/18','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.aprobada','inscripción aprobada','0','1',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.aprobada','inscripció aprovada','0','2',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.aprobada','inscripción aprobada#EU','0','3',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.aprobada','inscripción aprobada#GL','0','4',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.cancelada','inscripción cancelada','0','1',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.cancelada','inscripció cancel·lada','0','2',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.cancelada','inscripción cancelada#EU','0','3',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.cancelada','inscripción cancelada#GL','0','4',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.rechazada','inscripción rechazada','0','1',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.rechazada','inscripció rebutjada','0','2',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.rechazada','inscripción rechazada#EU','0','3',to_date('13/12/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('form.busquedaInscripciones.mensaje.inscripcion.rechazada','inscripción rechazada#GL','0','4',to_date('13/12/18','DD/MM/RR'),'0','19');






        
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaColegial.lopd','LOPD','0','1',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaColegial.lopd','LOPD#GL','0','4',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaColegial.lopd','LOPD','0','2',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaColegial.lopd','LOPD#EU','0','3',to_date('24/10/18','DD/MM/RR'),'0','19'); 
        
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Hoy','0','1',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Hoy#GL','0','4',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Avui','0','2',to_date('24/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Hoy#EU','0','3',to_date('24/10/18','DD/MM/RR'),'0','19');


