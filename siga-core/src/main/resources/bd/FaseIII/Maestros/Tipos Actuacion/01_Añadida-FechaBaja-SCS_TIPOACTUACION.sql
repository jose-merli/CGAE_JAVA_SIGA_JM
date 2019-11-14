spool 01_Añadida-FechaBaja-SCS_TIPOACTUACION.log
prompt 01_Añadida-FechaBaja-SCS_TIPOACTUACION.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

ALTER TABLE SCS_PARTIDAPRESUPUESTARIA ADD FECHABAJA DATE NULL;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off