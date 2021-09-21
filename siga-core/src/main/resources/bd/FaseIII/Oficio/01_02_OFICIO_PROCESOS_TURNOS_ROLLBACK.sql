spool PROCESOS TURNOS.log
prompt PROCESOS TURNOS.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE FROM gen_procesos where idproceso = '92M' and idparent ='913';

DELETE FROM ADM_TIPOSACCESO WHERE IDPROCESO = '92M' and idinstitucion ='2005';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off