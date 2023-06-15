spool 08_Creacion_columna_visible_ROLLBACK.log
prompt 08_Creacion_columna_visible_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--REVERTIR LOS CAMBIOS
ALTER TABLE FCS_FACTURACIONJG DROP COLUMN VISIBLE;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
