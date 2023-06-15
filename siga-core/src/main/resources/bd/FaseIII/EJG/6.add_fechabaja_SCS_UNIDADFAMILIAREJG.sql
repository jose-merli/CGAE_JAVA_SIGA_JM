spool 6.add_fechabaja_SCS_UNIDADFAMILIAREJG.log
prompt 6.add_fechabaja_SCS_UNIDADFAMILIAREJG.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

ALTER TABLE SCS_UNIDADFAMILIAREJG ADD FECHABAJA DATE NULL;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off