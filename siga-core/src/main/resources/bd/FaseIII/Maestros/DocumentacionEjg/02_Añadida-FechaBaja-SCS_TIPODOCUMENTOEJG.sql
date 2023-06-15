spool  02_Añadida-FechaBaja-SCS_TIPODOCUMENTOEJG.log
prompt 02_Añadida-FechaBaja-SCS_TIPODOCUMENTOEJG.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

ALTER TABLE SCS_TIPODOCUMENTOEJG ADD FECHABAJA DATE NULL;


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
