Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values (1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'XLS/XLSX','1',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'XLS/XLSX#CA','2',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'XLS/XLSX#EU','3',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'XLS/XLSX#GL','4',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

insert into mod_plantilladoc_formato (idformatosalida, nombre, fechamodificacion, usumodificacion)
values (1, (SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos)), sysdate, 0);

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values (1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'DOC/DOCX','1',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'DOC/DOCX#CA','2',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'DOC/DOCX#EU','3',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'DOC/DOCX#GL','4',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

insert into mod_plantilladoc_formato (idformatosalida, nombre, fechamodificacion, usumodificacion)
values (2, (SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos)), sysdate, 0);


Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values (1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'PDF','1',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'PDF#CA','2',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'PDF#EU','3',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'PDF#GL','4',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

insert into mod_plantilladoc_formato (idformatosalida, nombre, fechamodificacion, usumodificacion)
values (3, (SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos)), sysdate, 0);

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values (1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'PDF firmado','1',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'PDF firmado#CA','2',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'PDF firmado#EU','3',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) 
values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ),
'PDF firmado#GL','4',sysdate,'0',null,'mod_plantilladoc_formato','NOMBRE',CONCAT('mod_plantilladoc_formato.nombre.',(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos) ))); 

insert into mod_plantilladoc_formato (idformatosalida, nombre, fechamodificacion, usumodificacion)
values (4, (SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos)), sysdate, 0);
