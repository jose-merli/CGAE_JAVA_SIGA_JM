spool PUNTO MENU FUNDAMENTOS RESOLUCION.log
prompt PUNTO MENU FUNDAMENTOS RESOLUCION.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .
 
 Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('917','JGR','1','Y',SYSDATE,'0','Fundamentos de Resolución','JGR_FundamentosResolucion','003','10');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosResolucion','Fundamentos de Resolución','0','1',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosResolucion','Fundamentos de Resolución#GL','0','4',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosResolucion','Fonaments de Resolució','0','2',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosResolucion','Fundamentos de Resolución#EU','0','3',SYSDATE,'0','19');

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosResolucion','Fundamentos de Resolución','0','1',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosResolucion','Fundamentos de Resolución#GL','0','4',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosResolucion','Fonaments de Resolució','0','2',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.fundamentosResolucion','Fundamentos de Resolución#EU','0','3',SYSDATE,'0','19');


Insert into GEN_MENU 
(IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,PATH,FECHA_BAJA,IDCLASS) values
('917','21821','160','128',SYSDATE,'0',null,'menu.justiciaGratuita.maestros.fundamentosResolucion',null,'917','1','fundamentosResolucion',null,null);

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off