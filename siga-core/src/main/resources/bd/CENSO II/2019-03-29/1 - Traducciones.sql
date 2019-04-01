UPDATE gen_diccionario SET descripcion = 'Colegiaciones en otros colegios:'    WHERE idrecurso = 'censo.datosDireccion.literal.numero.colegiaciones' and idlenguaje = 1;
UPDATE gen_diccionario SET descripcion = 'Col·legiacions en altres col·legis:' WHERE idrecurso = 'censo.datosDireccion.literal.numero.colegiaciones' and idlenguaje = 2;
UPDATE gen_diccionario SET descripcion = 'Colegiaciones en otros colegios:#EU' WHERE idrecurso = 'censo.datosDireccion.literal.numero.colegiaciones' and idlenguaje = 3;
UPDATE gen_diccionario SET descripcion = 'Colegiaciones en otros colegios:#GL' WHERE idrecurso = 'censo.datosDireccion.literal.numero.colegiaciones' and idlenguaje = 4;



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

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.ws.situacionejerciente.INSCRITO','Inscrito','0','1',to_date('15/12/10','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.ws.situacionejerciente.INSCRITO','Inscrit','0','2',to_date('15/12/10','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.ws.situacionejerciente.INSCRITO','Inscrito#EU','0','3',to_date('15/12/10','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.ws.situacionejerciente.INSCRITO','Inscrito#GL','0','4',to_date('15/12/10','DD/MM/RR'),'0','19');

