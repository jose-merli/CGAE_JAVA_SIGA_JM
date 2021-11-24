spool 03_Cambio_proceso_rollback.log
prompt 03_Cambio_proceso_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .




insert into gen_procesos (IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values ('81G','JGR',1,'Y',SYSDATE,0,'GUARDIASANTIGUO','JGR_GUARDIA','913','10');

insert into adm_tiposacceso (IDPROCESO,IDPERFIL,FECHAMODIFICACION,USUMODIFICACION,DERECHOACCESO,IDINSTITUCION) values ('81G','ADG',SYSDATE,3,3,2005);

update gen_menu set idmenu = '81G', idproceso = '81G' where idmenu = '916' and idproceso = '916';


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
