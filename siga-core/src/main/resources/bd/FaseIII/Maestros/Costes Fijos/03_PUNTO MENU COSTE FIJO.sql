spool PUNTO MENU COSTE FIJO.log
prompt PUNTO MENU COSTE FIJO.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .
 
Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('915','JGR','1','Y',SYSDATE,'0','Costes Fijos','JGR_CostesFijos','003','10');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.costesFijos','Costes Fijos','0','1',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.costesFijos','Costes Fijos#GL','0','4',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.costesFijos','Costos Fixos','0','2',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.costesFijos','Costes Fijos#EU','0','3',SYSDATE,'0','19');

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.costesFijos','Costes Fijos','0','1',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.costesFijos','Costes Fijos#GL','0','4',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.costesFijos','Costos Fixos','0','2',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.costesFijos','Costes Fijos#EU','0','3',SYSDATE,'0','19');


Insert into GEN_MENU 
(IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,PATH,FECHA_BAJA,IDCLASS) values
('915','21820','160','128',SYSDATE,'0',null,'menu.justiciaGratuita.maestros.costesFijos',null,'915','1','costesFijos',null,null);

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off