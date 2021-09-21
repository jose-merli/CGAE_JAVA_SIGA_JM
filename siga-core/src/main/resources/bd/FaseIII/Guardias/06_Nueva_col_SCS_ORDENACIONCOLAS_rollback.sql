spool 06_Nueva_col_SCS_ORDENACIONCOLAS_rollback.log
prompt 06_Nueva_col_SCS_ORDENACIONCOLAS_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .


alter table scs_ordenacioncolas drop column ordenacionmanual;



commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
