spool 07_Creacion_labels_ficha_facturacion_ROLLBACK.log
prompt 07_Creacion_labels_ficha_facturacion_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--REVERTIR LOS CAMBIOS
--BORRAMOS LAS LABELS INSERTADAS
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.fichaFacturacion';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.datosFacturacion';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.simular';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.reabrir';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.regularizarFacturado';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.visible';

commit;

prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
