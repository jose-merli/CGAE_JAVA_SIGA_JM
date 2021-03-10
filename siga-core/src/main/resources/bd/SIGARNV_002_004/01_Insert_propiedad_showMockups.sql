spool  01_Insert_propiedad_showMockups.log
prompt 01_Insert_propiedad_showMockups
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt

INSERT INTO gen_properties VALUES ('SIGA', 'showMockups', 'true');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off