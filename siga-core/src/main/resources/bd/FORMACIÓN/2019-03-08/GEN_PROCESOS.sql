insert into gen_procesos VALUES ('229','CEN','1','Y','22/02/2019','0','Notario','CEN_NOTARIO','500','10');
insert into gen_procesos VALUES ('231','CEN','1','Y','22/02/2019','0','Integrantes','CEN_INTEGRANTES','500','10');
insert into gen_procesos VALUES ('233','CEN','1','Y','22/02/2019','0','Retenciones IRPF','CEN_RETENCIONES_IRPF','500','10');
insert into gen_procesos VALUES ('234','CEN','1','Y','22/02/2019','0','Servicios de interés','CEN_SERVICIOS_DE_INTERES','500','10');
insert into gen_procesos VALUES ('235','CEN','1','Y','22/02/2019','0','Otras colegiaciones','CEN_OTRAS_COLEGIACIONES','500','10');
insert into gen_procesos VALUES ('236','CEN','1','Y','22/02/2019','0','Sanciones','CEN_SANCIONES','500','10');
insert into gen_procesos VALUES ('237','CEN','1','Y','22/02/2019','0','Sociedades','CEN_SOCIEDADES','500','10');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudIncorporacion.ficha.numColegiadoDuplicado','El n�mero de colegiado ya existe','0','1',to_date('27/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudIncorporacion.ficha.numColegiadoDuplicado','El nombre de col·legiat ja existeix','0','2',to_date('27/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudIncorporacion.ficha.numColegiadoDuplicado','El n�mero de colegiado ya existe#EU','0','3',to_date('27/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudIncorporacion.ficha.numColegiadoDuplicado','El n�mero de colegiado ya existe#GL','0','4',to_date('27/02/19','DD/MM/RR'),'0','19');
update gen_parametros set valor = 'https://preproduccion.altermutua.com/WSSIGATEST/ServiciosAlter.asmx' where parametro like '%WS_ALTERM_URL%';
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Hoy','0','1',to_date('14/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Avui','0','2',to_date('14/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Hoy#EU','0','3',to_date('14/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.hoy','Hoy#GL','0','4',to_date('14/02/19','DD/MM/RR'),'0','19');



CREATE TABLE for_temacurso_persona (
    idpersona           NUMBER(10) NOT NULL,
    idtemacurso         NUMBER(10) NOT NULL,
    usumodificacion     NUMBER(10),
    fechamodificacion   DATE,
    idinstitucion       NUMBER(4) NOT NULL,
    fechabaja           DATE
);

--  ERROR: Index name length exceeds maximum allowed length(30) 

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