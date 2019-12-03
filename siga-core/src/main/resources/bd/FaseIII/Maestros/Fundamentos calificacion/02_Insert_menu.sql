
spool PUNTO MENU FUNDAMENTOS CALIFICACION.log
prompt PUNTO MENU FUNDAMENTOS CALIFICACION.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

 -- gen_procesos IDPARENT -- En pantallas permisos procesos la seccion donde se quiere mostrar el proceso

 
 Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('921','JGR','1','Y',SYSDATE,'0','Fundamentos de Calificación','JGR_FundamentosCalificacion','003','10');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosCalificacion','Fundamentos de Calificación','0','1',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosCalificacion','Fundamentos de Calificación#GL','0','4',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosCalificacion','Fonaments de Qualificació','0','2',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosCalificacion','Fundamentos de Calificación#EU','0','3',SYSDATE,'0','19');

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosCalificacion','Fundamentos de Calificación','0','1',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosCalificacion','Fundamentos de Calificación#GL','0','4',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosCalificacion','Fonaments de Qualificació','0','2',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosCalificacion','Fundamentos de Calificación#EU','0','3',SYSDATE,'0','19');

-- GEN_MENU IDPARENT -- Es el punto de menu padre donde se visualiza en el menu


Insert into GEN_MENU 
(IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,PATH,FECHA_BAJA,IDCLASS) values
('921','21822','160','128',SYSDATE,'0',null,'menu.justiciaGratuita.maestros.fundamentosCalificacion',null,'921','1','fundamentosCalificacion',null,null);

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
