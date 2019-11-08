spool AÑADIDA FECHABAJA TURNOS.log
prompt AÑADIDA FECHABAJA TURNOS.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .
ALTER TABLE SCS_TURNO ADD FECHABAJA DATE NULL;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off