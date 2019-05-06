Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.cabecera.confirmacion','Confirmación','0','1',to_date('30/04/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.cabecera.confirmacion','Confirmació','0','2',to_date('30/04/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.cabecera.confirmacion','Confirmación#GL','0','4',to_date('30/04/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.cabecera.confirmacion','Confirmación#EU','0','3',to_date('30/04/19','DD/MM/RR'),'0','19');

Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values (1+(SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos  )),'Formación','1',sysdate,'0',null,'CEN_TIPODIRECCION','DESCRIPCION',CONCAT('cen_tipodireccion.descripcion.',1+(SELECT MAX(IDRECURSO) IDRECURSO FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos  ))));
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos  )),'Formació','2',sysdate,'0',null,'CEN_TIPODIRECCION','DESCRIPCION',CONCAT('cen_tipodireccion.descripcion.',(SELECT MAX(IDRECURSO) IDRECURSO FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos  ))));
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos  )),'Formación#EU','3',sysdate,'0',null,'CEN_TIPODIRECCION','DESCRIPCION',CONCAT('cen_tipodireccion.descripcion.',(SELECT MAX(IDRECURSO) IDRECURSO FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos  ))));
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ((SELECT MAX(IDRECURSO)  FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos  )),'Formación#GL','4',sysdate,'0',null,'CEN_TIPODIRECCION','DESCRIPCION',CONCAT('cen_tipodireccion.descripcion.',(SELECT MAX(IDRECURSO) IDRECURSO FROM (select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos  ))));

Insert into CEN_TIPODIRECCION (IDTIPODIRECCION,DESCRIPCION,USUMODIFICACION,FECHAMODIFICACION) values (14,(SELECT MAX(IDRECURSO) IDRECURSO FROM ((select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999') IDRECURSO from gen_recursos_catalogos  ))),0,sysdate);

commit;