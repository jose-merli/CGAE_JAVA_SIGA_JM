spool 03_OFICIO_PROCESOS_TURNOS_ROLLBACK.log
prompt 03_OFICIO_PROCESOS_TURNOS_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE FROM ADM_TIPOSACCESO WHERE IDPROCESO = '93M' and idinstitucion ='2005';

DELETE FROM gen_procesos where idproceso = '93M' and idparent ='913';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off