spool 04_REVERT_Creacion_labels_tabla_busquedaFacturacion.log
prompt 04_REVERT_Creacion_labels_tabla_busquedaFacturacion.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--REVERTIR LOS CAMBIOS
--BORRAMOS LAS LABELS INSERTADAS
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.buscarFacturacion.pendiente';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.buscarFacturacion.regularizacion';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.buscarFacturacion.total';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.buscarFacturacion.pagado';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.buscarFacturacion.fechaEstado';

commit;

prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
