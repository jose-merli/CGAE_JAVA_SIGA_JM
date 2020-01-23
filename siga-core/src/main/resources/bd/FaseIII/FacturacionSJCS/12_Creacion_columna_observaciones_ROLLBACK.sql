spool 12_Creacion_columna_observaciones_ROLLBACK.log
prompt 12_Creacion_columna_observaciones_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--REVERTIR LOS CAMBIOS
ALTER TABLE FCS_FACT_ESTADOSFACTURACION DROP COLUMN OBSERVACIONES;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
