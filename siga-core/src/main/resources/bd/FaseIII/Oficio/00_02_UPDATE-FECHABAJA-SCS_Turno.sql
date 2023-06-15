spool FECHABAJA TURNOS.log
prompt FECHABAJA TURNOS.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

UPDATE scs_turno set fechabaja = fechamodificacion  where visibilidad = '0' ;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off