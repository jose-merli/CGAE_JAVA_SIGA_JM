spool 05_Creacion_labels_mensaje_filtro_vacio_ROLLBAK.log
prompt 05_Creacion_labels_mensaje_filtro_vacio_ROLLBAK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--REVERTIR LOS CAMBIOS
--BORRAMOS LAS LABELS INSERTADAS
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.buscarFacturacion.mensajeFiltroVacio';

commit;

prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
