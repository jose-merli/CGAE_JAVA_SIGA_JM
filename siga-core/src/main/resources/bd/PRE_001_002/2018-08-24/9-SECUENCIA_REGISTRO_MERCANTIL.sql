spool 9-SECUENCIA_REGISTRO_MERCANTIL.log
prompt 9-SECUENCIA_REGISTRO_MERCANTIL.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

CREATE SEQUENCE seq_id_datos_reg START WITH 1 INCREMENT BY 1 MAXVALUE 999999999999999 MINVALUE 1;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
