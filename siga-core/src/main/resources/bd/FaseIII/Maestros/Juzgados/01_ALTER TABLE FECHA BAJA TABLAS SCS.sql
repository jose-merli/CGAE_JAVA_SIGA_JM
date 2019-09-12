spool ALTER_TABLE FECHA BAJA TABLA SCS.log
prompt ALTER_TABLE FECHA BAJA TABLA SCS.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

ALTER TABLE SCS_JUZGADOPROCEDIMIENTO ADD FECHABAJA DATE NULL;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off

