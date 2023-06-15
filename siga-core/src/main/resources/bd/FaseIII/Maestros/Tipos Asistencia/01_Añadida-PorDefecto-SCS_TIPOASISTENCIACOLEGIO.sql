spool  01_Añadida-PorDefecto-SCS_TIPOASISTENCIACOLEGIO.log
prompt 01_Añadida-PorDefecto-SCS_TIPOASISTENCIACOLEGIO.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

ALTER TABLE SCS_TIPOASISTENCIACOLEGIO ADD PORDEFECTO NUMBER(1) DEFAULT '0';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
