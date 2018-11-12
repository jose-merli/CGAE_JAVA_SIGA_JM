spool 3-UPDATE_MENU_PROCESO_SOCIEDADES.log
prompt 3-UPDATE_MENU_PROCESO_SOCIEDADES
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .


update gen_menu set idproceso = '22' where idmenu = '22';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
