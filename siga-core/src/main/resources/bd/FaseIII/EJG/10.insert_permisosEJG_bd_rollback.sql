spool 10.insert_permisosEJG_bd_rollback.log
prompt 10.insert_permisosEJG_bd_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt . 

Delete from GEN_PROCESOS  where idproceso = '940';

Delete from GEN_PROCESOS  where idproceso = '943';

Delete from GEN_PROCESOS  where idproceso = '944';

Delete from GEN_PROCESOS  where idproceso = '945';

Delete from GEN_PROCESOS  where idproceso = '91R';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off 