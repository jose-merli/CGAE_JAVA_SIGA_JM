spool 06_Nueva_col_SCS_ORDENACIONCOLAS.log
prompt 06_Nueva_col_SCS_ORDENACIONCOLAS.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .


alter table scs_ordenacioncolas add ordenacionmanual NUMBER(3,0);

update scs_ordenacioncolas set ordenacionmanual = 0;


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
