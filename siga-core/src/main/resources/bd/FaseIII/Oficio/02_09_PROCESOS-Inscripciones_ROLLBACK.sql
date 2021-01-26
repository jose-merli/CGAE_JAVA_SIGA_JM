spool 09_PROCESOS-Inscripciones_ROLLBACK.log
prompt 09_PROCESOS-Inscripciones_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE FROM ADM_TIPOSACCESO WHERE IDPROCESO = '99P' and idinstitucion ='2005';
DELETE FROM ADM_TIPOSACCESO WHERE IDPROCESO = '93P' and idinstitucion ='2005';


DELETE FROM gen_procesos where idproceso = '99P' and idparent ='96M';
DELETE FROM gen_procesos where idproceso = '93P' and idparent ='96M';


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off