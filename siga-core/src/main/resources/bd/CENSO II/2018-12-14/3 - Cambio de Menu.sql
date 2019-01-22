update gen_menu set fecha_baja = sysdate where idmenu = '15F';
update gen_menu set fecha_baja = sysdate where idmenu = '99E';
update gen_menu set fecha_baja = sysdate where idmenu = '5';
update gen_menu set fecha_baja = sysdate where idmenu = '6';
update gen_menu set path = 'solicitudesModificacion' where idmenu = '4';



Insert into GEN_PROCESOS (IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) 
values ('15G','CEN','1','Y',to_date('10/10/18','DD/MM/RR'),'0','Mantenimiento masivo','CEN_CargaMasiva','180','10');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.cargaMasiva','Cargas masivas','0','1',to_date('20/03/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.cargaMasiva','Càrregues massives','0','2',to_date('20/03/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.cargaMasiva','Cargas masivas#EU','0','3',to_date('20/03/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.cargaMasiva','Cargas masivas#GL','0','4',to_date('20/03/18','DD/MM/RR'),'0','19');

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.cargaMasiva','Cargas masivas','0','1',to_date('20/03/18','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.cargaMasiva','Càrregues massives','0','2',to_date('20/03/18','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.cargaMasiva','Cargas masivas#EU','0','3',to_date('20/03/18','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.cargaMasiva','Cargas masivas#GL','0','4',to_date('20/03/18','DD/MM/RR'),'0','19');


Insert into GEN_MENU (IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,IDCLASS,FECHA_BAJA,PATH) 
values ('15G','17050','180','162',to_date('22/09/18','DD/MM/RR'),'0',null,'menu.cen.cargaMasiva',null,'15G','1',null,null,'cargasMasivas');