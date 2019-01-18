-- INSERTAR UN NUEVO PUNTO DE MENU EN EL MENU PRINCIPAL

-- 1º Insertamos los puntos de menu en la tabla procesos

Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('1c','CEN','1','Y',to_date('10/10/18','DD/MM/RR'),'0','Desplegar Menú Búsqueda Censo General',null,'0','10');




-- 3º Insertamos traducciones de los puntos de menu y sus items


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.busquedaCensoGeneral','Búsqueda Censo General','0','1',to_date('10/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.busquedaCensoGeneral','Búsqueda Censo General#GL','0','4',to_date('10/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.busquedaCensoGeneral','Cerca Cens General','0','2',to_date('10/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.busquedaCensoGeneral','Búsqueda Censo General#EU','0','3',to_date('10/10/18','DD/MM/RR'),'0','19');

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.busquedaCensoGeneral','Búsqueda Censo General','0','1',to_date('10/10/18','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.busquedaCensoGeneral','Búsqueda Censo General#GL','0','4',to_date('10/10/18','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.busquedaCensoGeneral','Cerca Cens General','0','2',to_date('10/10/18','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.busquedaCensoGeneral','Búsqueda Censo General#EU','0','3',to_date('10/10/18','DD/MM/RR'),'0','19');


-- 4º Insertamos puntos de menu en la tabla menu


-- Campos de los items: 
-- idparent= idproceso del punto de menu al que pertenezca
-- path = nombre que se usará app.routing.ts
-- idmenu = idproceso
-- idrecurso = traducion

Insert into GEN_MENU 
(IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,PATH,FECHA_BAJA,IDCLASS) values
('1c','30502','80','020',to_date('10/10/18','DD/MM/RR'),'0',null,'menu.censo.busquedaCensoGeneral',null,'1c','1','busquedaCensoGeneral',null,null);

