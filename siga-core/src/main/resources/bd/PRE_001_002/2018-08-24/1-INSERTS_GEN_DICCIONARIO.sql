spool 1-INSERTS_GEN_DICCIONARIO.log
prompt 1-INSERTS_GEN_DICCIONARIO.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

  

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('generico.error.permiso.denegado','Actualmente no tiene permisos para visualizar esta información. Consulte en su Colegio#GL','0','4',to_date('12/04/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('generico.error.permiso.denegado','Actualmente no tiene permisos para visualizar esta información. Consulte en su Colegio#EU','0','3',to_date('12/04/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('generico.error.permiso.denegado','Actualment no té permisos per visualitzar aquesta informació. Consulti en el seu Col·legi','0','2',to_date('12/04/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('generico.error.permiso.denegado','Actualmente no tiene permisos para visualizar esta información. Consulte en su Colegio','0','1',to_date('12/04/18','DD/MM/RR'),'0','19');


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
