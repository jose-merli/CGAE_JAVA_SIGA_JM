spool 03_Cambio_proceso.log
prompt 03_Cambio_proceso.sql
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .



update gen_menu set idmenu = '916', idproceso = '916' where idmenu = '81G' and idproceso = '81G';

delete from adm_tiposacceso where idproceso = '81G' and idperfil = 'ADG' and idinstitucion = '2005';

delete from gen_procesos where idproceso='81G';


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
