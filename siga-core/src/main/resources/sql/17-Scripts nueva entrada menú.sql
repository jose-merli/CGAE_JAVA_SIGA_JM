
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) 
values ('menu.censo.buscarSociedades','Buscar Sociedades#GL','0','4',to_date('10/01/04','DD/MM/RR'),'0','22');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) 
values ('menu.censo.buscarSociedades','Buscar Societats','0','2',to_date('10/01/04','DD/MM/RR'),'0','22');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) 
values ('menu.censo.buscarSociedades','Buscar Sociedades','0','1',to_date('10/01/04','DD/MM/RR'),'0','22');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) 
values ('menu.censo.buscarSociedades','Buscar Sociedades#EU','0','3',to_date('10/01/04','DD/MM/RR'),'0','22');

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) 
values ('menu.censo.buscarSociedades','Buscar Sociedades#GL','0','4',to_date('10/01/04','DD/MM/RR'),'0','22');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) 
values ('menu.censo.buscarSociedades','Buscar Societats','0','2',to_date('10/01/04','DD/MM/RR'),'0','22');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) 
values ('menu.censo.buscarSociedades','Buscar Sociedades','0','1',to_date('10/01/04','DD/MM/RR'),'0','22');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) 
values ('menu.censo.buscarSociedades','Buscar Sociedades#EU','0','3',to_date('10/01/04','DD/MM/RR'),'0','22');

update gen_diccionario set descripcion = 'Buscar No Colegiados' where idrecurso = 'menu.censo.buscarNoColegiados' and idLenguaje = '1';

Insert into GEN_PROCESOS (IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) 
values ('22','CEN','1','Y',to_date('20/01/04','DD/MM/RR'),'0','Buscar Sociedades','CEN_BusquedaClientesNoColegiados','001','10');

Insert into GEN_MENU (IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,PATH,FECHA_BAJA,IDCLASS) 
values ('22','10200','180','0',to_date('20/01/04','DD/MM/RR'),'0',null,'menu.censo.buscarSociedades',null,'2','1','searchNoColegiados',null,null);

UPDATE GEN_MENU SET PATH = 'busquedaNoColegiados' WHERE IDMENU ='2';


COMMIT;
