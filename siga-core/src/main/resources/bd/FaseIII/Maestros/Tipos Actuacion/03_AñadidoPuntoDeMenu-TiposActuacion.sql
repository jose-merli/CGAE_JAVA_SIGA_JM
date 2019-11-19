spool 03_AñadidoPuntoDeMenu-TiposActuacion.log
prompt 03_AñadidoPuntoDeMenu-TiposActuacion.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('99F','JGR','1','Y',SYSDATE,'0','Tipos de Actuación','JGR_TiposdeActuacion','003','10');

Insert into GEN_MENU(IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,PATH,FECHA_BAJA,IDCLASS) values
('940','21825','160','128',SYSDATE,'0',null,'menu.justiciaGratuita.maestros.tiposActuacion',null,'99F','1','tiposActuacion',null,null); 

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off