spool 4-grant_funcion_perfiles_rol.log
prompt 4-grant_funcion_perfiles_rol.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

GRANT EXECUTE ON USCGAE.F_SIGA_PERFILES_USUARIO_ROL TO ROLE_SIGA_R; 
GRANT EXECUTE ON USCGAE.F_SIGA_PERFILES_USUARIO_ROL TO ROLE_SIGA;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
