spool INSERT_GEN_PROCESOS TARJETAS.log
prompt INSERT_GEN_PROCESOS TARJETAS.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .
 
 Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('89A','JGR','1','Y',SYSDATE,'0','Gestión Justiciables','JGR_Justiciables','89','10');

 Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('89B','JGR','1','Y',SYSDATE,'0','Tarjeta Datos Generales','JGR_JusticiablesTarjeta','89A','10');

 Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('89C','JGR','1','Y',SYSDATE,'0','Tarjeta Datos Solicitud','JGR_JusticiablesTarjeta','89A','10');

 Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('89D','JGR','1','Y',SYSDATE,'0','Tarjeta Datos Representante','JGR_JusticiablesTarjeta','89A','10');

 Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('89E','JGR','1','Y',SYSDATE,'0','Tarjeta Asuntos','JGR_JusticiablesTarjeta','89A','10');


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off