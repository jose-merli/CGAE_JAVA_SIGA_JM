spool 11_Creacion_labels_baremos_cartas_ROLLBACK.log
prompt 11_Creacion_labels_baremos_cartas_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--REVERTIR LOS CAMBIOS
--BORRAMOS LAS LABELS INSERTADAS
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.numero';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.baremos';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.cartasFacturacion';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='facturacionSJCS.facturacionesYPagos.apuntes';

commit;

prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
