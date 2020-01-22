spool 03_OFICIO_PROCESOS_TURNOS.log
prompt 03_OFICIO_PROCESOS_TURNOS.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('93M','JGR','1','Y',SYSDATE,'0','Tarjeta Listado Inscripciones','JGR_ListadoInscripciones','913','10');

INSERT INTO ADM_TIPOSACCESO (IDPROCESO,IDPERFIL,FECHAMODIFICACION,USUMODIFICACION,DERECHOACCESO,IDINSTITUCION) 
values ('93M','ADG',SYSDATE,'0','3','2005');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off