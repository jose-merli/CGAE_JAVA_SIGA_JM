spool 14_Creacion_periodo_ejecucion_proceso_facturacion_ROLLBACK.log
prompt 14_Creacion_periodo_ejecucion_proceso_facturacion_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--REVERTIR LOS CAMBIOS
DELETE FROM ADM_CONFIG WHERE CLAVE='cron.pattern.scheduled.procesoFacturacion';

commit;

prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
