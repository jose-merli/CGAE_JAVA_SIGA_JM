spool 2-GRUPOSPORDEFECTO_perfiles.log
prompt 2-GRUPOSPORDEFECTO_perfiles.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

delete adm_perfil_rol where grupopordefecto is null or GRUPOPORDEFECTO = 'N';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
