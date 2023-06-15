spool  6.add_fechabaja_SCS_UNIDADFAMILIAREJG.rollback.log
prompt 6.add_fechabaja_SCS_UNIDADFAMILIAREJG.rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

ALTER TABLE SCS_UNIDADFAMILIAREJG DROP COLUMN FECHABAJA;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off