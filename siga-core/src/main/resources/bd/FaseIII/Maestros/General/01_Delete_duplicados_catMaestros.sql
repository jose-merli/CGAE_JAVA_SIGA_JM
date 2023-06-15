spool  DeleteduplicadosCatMaestros.log
prompt   DeleteduplicadosCatMaestros.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .


update GEN_TABLAS_MAESTRAS set FLAGBORRADOLOGICO = '1' where IDTABLAMAESTRA = 'SCS_COSTEFIJO'; 
update GEN_TABLAS_MAESTRAS set FLAGBORRADOLOGICO = '1' where IDTABLAMAESTRA = 'SCS_TIPOASISTENCIA'; 
update GEN_TABLAS_MAESTRAS set FLAGBORRADOLOGICO = '1' where IDTABLAMAESTRA = 'SCS_TIPOFUNDAMENTOCALIF'; 
update GEN_TABLAS_MAESTRAS set FLAGBORRADOLOGICO = '1' where IDTABLAMAESTRA = 'SCS_TIPORESOLUCION';  


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
