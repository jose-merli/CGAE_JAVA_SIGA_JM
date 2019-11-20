spool 01_InsertDiccionarioProcedimiento.log
prompt 01_InsertDiccionarioProcedimiento.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.pretension.existeProcedimientoMismoNombre','Ya existe un procedimiento con esa descripción','0','1',to_date('24/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.pretension.existeProcedimientoMismoNombre','Ja hi ha un procediment amb aquesta descripció','0','2',to_date('24/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.pretension.existeProcedimientoMismoNombre','Ya existe un procedimiento con esa descripción#GL','0','4',to_date('24/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.pretension.existeProcedimientoMismoNombre','Ya existe un procedimiento con esa descripción#EU','0','3',to_date('24/10/19','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off