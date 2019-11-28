spool  01_Añadida-FechaBaja-Y-Observaciones-SCS_Procedimientos.log
prompt   01_Añadida-FechaBaja-Y-Observaciones-SCS_Procedimientos.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

ALTER TABLE SCS_PROCEDIMIENTOS ADD FECHABAJA DATE NULL;
ALTER TABLE SCS_PROCEDIMIENTOS ADD OBSERVACIONES VARCHAR (4000);

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
