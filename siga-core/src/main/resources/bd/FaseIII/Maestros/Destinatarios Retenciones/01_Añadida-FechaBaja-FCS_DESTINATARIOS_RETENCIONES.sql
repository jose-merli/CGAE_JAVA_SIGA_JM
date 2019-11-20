spool 01_Añadida-FechaBaja-FCS_DESTINATARIOS_RETENCIONES.log
prompt 01_Añadida-FechaBaja-FCS_DESTINATARIOS_RETENCIONES.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

ALTER TABLE fcs_destinatarios_Retenciones ADD FECHABAJA DATE NULL;


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
