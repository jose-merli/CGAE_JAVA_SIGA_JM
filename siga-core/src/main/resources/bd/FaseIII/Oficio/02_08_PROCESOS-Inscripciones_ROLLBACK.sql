spool 08_PROCESOS-Inscripciones_ROLLBACK.log
prompt 08_PROCESOS-Inscripciones_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE FROM ADM_TIPOSACCESO WHERE IDPROCESO = '96P' and idinstitucion ='2005';
DELETE FROM ADM_TIPOSACCESO WHERE IDPROCESO = '97P' and idinstitucion ='2005';
DELETE FROM ADM_TIPOSACCESO WHERE IDPROCESO = '98P' and idinstitucion ='2005';

DELETE FROM gen_procesos where idproceso = '96P' and idparent ='96M';
DELETE FROM gen_procesos where idproceso = '97P' and idparent ='96M';
DELETE FROM gen_procesos where idproceso = '98P' and idparent ='96M';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off