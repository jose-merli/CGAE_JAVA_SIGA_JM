spool 14_Creacion_periodo_ejecucion_proceso_facturacion.log
prompt 14_Creacion_periodo_ejecucion_proceso_facturacion.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--HACEMOS EL INSERT
INSERT INTO ADM_CONFIG (ID, CLAVE, VALOR, DESCRIPCION, VALOR_POR_DEFECTO, NECESITA_REINICIO)
VALUES (SEQ_ADM_CONFIG.NEXTVAL, 'cron.pattern.scheduled.procesoFacturacion', '*/2 * * * * *', 'Periodo de ejecucion del cron para el proceso de facturacion', '*/2 * * * * *', 0); 

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
