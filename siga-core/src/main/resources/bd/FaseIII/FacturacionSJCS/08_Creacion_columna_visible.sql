spool 08_Creacion_columna_visible.log
prompt 08_Creacion_columna_visible.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--CREAMOS LA COLUMNA
alter table FCS_FACTURACIONJG add VISIBLE varchar2(1) default '0';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
