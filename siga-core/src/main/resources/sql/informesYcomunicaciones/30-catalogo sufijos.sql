Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values (1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Nombre y apellidos del destinatario','1',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Nombre y apellidos del destinatario#CA','2',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Nombre y apellidos del destinatario#EU','3',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Nombre y apellidos del destinatario#GL','4',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

insert into mod_plantilladoc_sufijo (idsufijo, nombre, fechamodificacion, usumodificacion)
values (1, (SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos)), sysdate, 0);

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values (1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Número de colegiado del destinatario','1',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Número de colegiado del destinatario#CA','2',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Número de colegiado del destinatario#EU','3',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Número de colegiado del destinatario#GL','4',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

insert into mod_plantilladoc_sufijo (idsufijo, nombre, fechamodificacion, usumodificacion)
values (2, (SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos)), sysdate, 0);


Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values (1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Número de identificación de la entidad','1',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Número de identificación de la entidad#CA','2',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Número de identificación de la entidad#EU','3',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'Número de identificación de la entidad#GL','4',sysdate,'0',null,'MOD_PLANTILLADOC_SUFIJO','NOMBRE',CONCAT('mod_plantilladoc_sufijo.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

insert into mod_plantilladoc_sufijo (idsufijo, nombre, fechamodificacion, usumodificacion)
values (3, (SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos)), sysdate, 0);
