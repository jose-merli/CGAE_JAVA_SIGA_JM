spool  01_Insert_propiedad_showMockups_Rollback.log
prompt 01_Insert_propiedad_showMockups_Rollback
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt

/* rollback */
DELETE FROM gen_properties WHERE parametro = 'showMockups' AND fichero = 'SIGA';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off