spool 03_REVERT_Creacion_columna_y_foreign_key.log
prompt 03_REVERT_Creacion_columna_y_foreign_key.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--REVERTIR LOS CAMBIOS
--ELIMINAMOS PRIMERO LA FK Y LUEGO LA COLUMNA
ALTER TABLE FCS_FACTURACIONJG DROP CONSTRAINT fk_partidaPresupuestaria_id;
ALTER TABLE FCS_FACTURACIONJG DROP COLUMN IDPARTIDAPRESUPUESTARIA;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
