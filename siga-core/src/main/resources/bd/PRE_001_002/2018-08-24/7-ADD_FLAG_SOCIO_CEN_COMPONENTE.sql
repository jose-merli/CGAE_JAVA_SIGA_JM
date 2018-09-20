spool 7-ADD_FLAG_SOCIO_CEN_COMPONENTE.log
prompt 7-ADD_FLAG_SOCIO_CEN_COMPONENTE.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

ALTER TABLE CEN_COMPONENTES ADD  FLAG_SOCIO NUMBER(1,0) NULL;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
