spool  01_Añadida-FechaBaja-SCS_PARTIDAPRESUPUESTARIA.log
prompt 01_Añadida-FechaBaja-SCS_PARTIDAPRESUPUESTARIA.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .



ALTER TABLE SCS_TIPOACTUACION ADD FECHABAJA DATE NULL;


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
