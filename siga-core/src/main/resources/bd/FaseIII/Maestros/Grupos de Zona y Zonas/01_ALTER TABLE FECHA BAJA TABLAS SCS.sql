spool ALTER_TABLE FECHA BAJA TABLAS SCS.log
prompt ALTER_TABLE FECHA BAJA TABLAS SCS.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

ALTER TABLE SCS_ZONA ADD FECHABAJA DATE NULL;
ALTER TABLE SCS_SUBZONA ADD FECHABAJA DATE NULL;
ALTER TABLE SCS_SUBZONAPARTIDO ADD FECHABAJA DATE NULL;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
