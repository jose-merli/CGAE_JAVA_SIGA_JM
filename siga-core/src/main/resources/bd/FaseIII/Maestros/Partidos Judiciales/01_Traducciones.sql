spool 01_Traducciones.log
prompt 01_Traducciones.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionPartidoJudicial.existePartidoJudicial','Este partido judicial ya está asociado a la institución','0','1',to_date('01/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionPartidoJudicial.existePartidoJudicial','Aquest partit judicial ja està associat a la institució','0','2',to_date('01/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionPartidoJudicial.existePartidoJudicial','Este partido judicial ya está asociado a la institución#EU','0','3',to_date('01/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionPartidoJudicial.existePartidoJudicial','Este partido judicial ya está asociado a la institución#GL','0','4',to_date('01/10/19','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off