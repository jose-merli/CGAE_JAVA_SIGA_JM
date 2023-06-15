spool 06_Creacion_labels_mensajes_errores_eliminar.log
prompt 06_Creacion_labels_mensajes_errores_eliminar.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--HACEMOS EL INSERT DE LAS LABELS UTILIZADAS
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.buscarFacturacion.mensajeErrorEliminar','La línea de facturación no pudo ser eliminada','0','1',to_date('28/11/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.buscarFacturacion.mensajeErrorEliminar','La línia de facturació no es pot esborrar','0','2',to_date('28/11/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.buscarFacturacion.mensajeErrorEliminar','La línea de facturación no pudo ser eliminada #EU','0','3',to_date('28/11/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.buscarFacturacion.mensajeErrorEliminar','La línea de facturación no pudo ser eliminada #GL','0','4',to_date('28/11/19','DD/MM/RR'),'0','15');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
