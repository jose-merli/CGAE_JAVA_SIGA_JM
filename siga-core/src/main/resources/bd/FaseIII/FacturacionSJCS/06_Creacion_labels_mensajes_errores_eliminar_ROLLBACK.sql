spool 06_Creacion_labels_mensajes_errores_eliminar_ROLLBACK.log
prompt 06_Creacion_labels_mensajes_errores_eliminar_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--REVERTIR LOS CAMBIOS
--BORRAMOS LAS LABELS INSERTADAS
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.buscarFacturacion.mensajeErrorEliminar';

commit;

prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
