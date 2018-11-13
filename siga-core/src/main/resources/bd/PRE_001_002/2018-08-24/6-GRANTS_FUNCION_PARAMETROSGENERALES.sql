spool 6-GRANTS_FUNCION_PARAMETROSGENERALES.log
prompt 6-GRANTS_FUNCION_PARAMETROSGENERALES.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

GRANT EXECUTE ON USCGAE.F_SIGA_GETPARAMETROGENERAL TO ROLE_SIGA_R; 
GRANT EXECUTE ON USCGAE.F_SIGA_GETPARAMETROGENERAL TO ROLE_SIGA;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
