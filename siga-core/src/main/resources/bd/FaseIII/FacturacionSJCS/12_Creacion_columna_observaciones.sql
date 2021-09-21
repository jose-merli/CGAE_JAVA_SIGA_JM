spool 12_Creacion_columna_observaciones.log
prompt 12_Creacion_columna_observaciones.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--CREAMOS LA COLUMNA
ALTER TABLE FCS_FACT_ESTADOSFACTURACION ADD OBSERVACIONES VARCHAR2(400);

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
