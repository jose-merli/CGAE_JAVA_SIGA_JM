spool 10.insert_permisosEJG_bd.log
prompt 10.insert_permisosEJG_bd.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt . 

Insert into gen_procesos (IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values('940','JGR','1','Y',SYSDATE,'0','EJGTarjetaResumen','JGR_TarjetaResumenEJG','946','10');

Insert into gen_procesos (IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values ('943','JGR','1','Y',SYSDATE,'0','EJGDatosGenerales','JGR_DatosGeneralesEJG','946','10');

Insert into gen_procesos (IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values ('944','JGR','1','Y',SYSDATE,'0','EJGServiciosTramitacion','JGR_ServiciosTramitacionEJG','946','10');

Insert into gen_procesos (IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values ('945','JGR','1','Y',SYSDATE,'0','EJGExpedientesEconomicos','JGR_ExpedientesEconomicosEJG','946','10');

Insert into gen_procesos (IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values ('91R','JGR','1','Y',SYSDATE,'0','EJGResolucion','JGR_ResolucionEJG','946','10');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off 