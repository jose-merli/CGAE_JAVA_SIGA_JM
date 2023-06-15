spool 09_Creacion_labels_conceptos_facturacion_ROLLBACK.log
prompt 09_Creacion_labels_conceptos_facturacion_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--REVERTIR LOS CAMBIOS
--BORRAMOS LAS LABELS INSERTADAS
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.fichaConceptosFacturacion';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.conceptos';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.importe';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.importePendiente';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.cantidad';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.porcentaje';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.datosGenerales';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.cartaPago';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.NumeroColegiado';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.numColegiado';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.colegio';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.apellidos';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.nif';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.importeTurnos';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.importeGuardias';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.importeEJG';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.importeSO';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.importeTotal';

commit;

prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
