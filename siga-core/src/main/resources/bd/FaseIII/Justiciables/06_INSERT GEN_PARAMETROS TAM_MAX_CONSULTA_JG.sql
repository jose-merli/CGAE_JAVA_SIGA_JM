spool INSERT_GEN_PARAMETROS TAM_MAX_CONSULTA_JG.log
prompt INSERT_GEN_PARAMETROS TAM_MAX_CONSULTA_JG.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('tamaño.maximo.consulta.justicia.gratuita','Tamaño Máximo Consulta Justicia Gratuita','0','1',sysdate,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('tamaño.maximo.consulta.justicia.gratuita','Mida Màxim Consulta Justícia Gratuïta','0','2',sysdate,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('tamaño.maximo.consulta.justicia.gratuita','Tamaño Máximo Consulta Justicia Gratuita#EU','0','3',sysdate,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('tamaño.maximo.consulta.justicia.gratuita','Tamaño Máximo Consulta Justicia Gratuita#GL','0','4',sysdate,'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('tamaño.maximo.consulta.justicia.gratuita','Tamaño Máximo Consulta Justicia Gratuita','0','1',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('tamaño.maximo.consulta.justicia.gratuita','Mida Màxim Consulta Justícia Gratuïta','0','2',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('tamaño.maximo.consulta.justicia.gratuita','Tamaño Máximo Consulta Justicia Gratuita#EU','0','3',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('tamaño.maximo.consulta.justicia.gratuita','Tamaño Máximo Consulta Justicia Gratuita#GL','0','4',sysdate,'0','19');

Insert into GEN_PARAMETROS (MODULO,PARAMETRO,VALOR,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION, IDRECURSO, FECHA_BAJA) values ('SCS','TAM_MAX_CONSULTA_JG','500',sysdate,'0','0','tamaño.maximo.consulta.justicia.gratuita', null);


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off

