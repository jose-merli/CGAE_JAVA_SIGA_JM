ALTER TABLE CEN_TIPOSOCIEDAD ADD FECHA_BAJA DATE NULL; -- BUSQUEDA DE PERSONA JURIDICA
ALTER TABLE CEN_NOCOLEGIADO ADD FECHA_BAJA DATE NULL; -- BUSQUEDA DE PERSONA JURIDICA
ALTER TABLE CEN_GRUPOSCLIENTE_CLIENTE ADD FECHA_BAJA DATE NULL; -- BUSQUEDA EN PERSONA JURIDICA 
ALTER TABLE CEN_GRUPOSCLIENTE_CLIENTE ADD FECHA_INICIO DATE; -- BUSQUEDA EN PERSONA JURIDICA
UPDATE CEN_GRUPOSCLIENTE_CLIENTE SET FECHA_INICIO = to_date('01/01/2018','DD/MM/RR'); -- BUSQUEDA EN PERSONA JURIDICA
UPDATE GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedades Colectivas' where IDLENGUAJE = '1' and IDRECURSO= '390067';  -- COMBO ETIQUERAS EN BUSQUEDA EN PERSONA JURIDICA
UPDATE GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Societats Col·lectives ' where IDLENGUAJE = '2' and IDRECURSO= '390067'; -- COMBO ETIQUERAS EN BUSQUEDA EN PERSONA JURIDICA
UPDATE GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedades Colectivas#EU ' where IDLENGUAJE = '3' and IDRECURSO= '390067'; -- COMBO ETIQUERAS EN BUSQUEDA EN PERSONA JURIDICA
UPDATE GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedades Colectivas#GL ' where IDLENGUAJE = '4' and IDRECURSO= '390067'; -- COMBO ETIQUERAS EN BUSQUEDA EN PERSONA JURIDICA
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Comunidades de bienes, herencias yacentes y demás entidades carentes de personalidad jurídica no incluidas expresamente en otras claves' where IDLENGUAJE = '1' and IDRECURSO= '390069'; -- COMBO ETIQUERAS EN BUSQUEDA EN PERSONA JURIDICA
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Comunitats de béns, herències jacents i altres entitats mancades de personalitat jurídica no incloses expressament en altres claus' where IDLENGUAJE = '2' and IDRECURSO= '390069'; -- COMBO ETIQUERAS EN BUSQUEDA EN PERSONA JURIDICA
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Comunidades de bienes, herencias yacentes y demás entidades carentes de personalidad jurídica no incluidas expresamente en otras claves#EU ' where IDLENGUAJE = '3' and IDRECURSO= '390069'; -- COMBO ETIQUERAS EN BUSQUEDA EN PERSONA JURIDICA
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Comunidades de bienes, herencias yacentes y demás entidades carentes de personalidad jurídica no incluidas expresamente en otras claves#GL ' where IDLENGUAJE = '4' and IDRECURSO= '390069'; -- COMBO ETIQUERAS EN BUSQUEDA EN PERSONA JURIDICA
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedad Cooperativa' where IDLENGUAJE = '1' and IDRECURSO= '390070';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Societat Cooperativa' where IDLENGUAJE = '2' and IDRECURSO= '390070';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedad Cooperativa#EU ' where IDLENGUAJE = '3' and IDRECURSO= '390070';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedad Cooperativa#GL ' where IDLENGUAJE = '4' and IDRECURSO= '390070';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Asociaciones' where IDLENGUAJE = '1' and IDRECURSO= '390074';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Associacions' where IDLENGUAJE = '2' and IDRECURSO= '390074';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Asociaciones#EU ' where IDLENGUAJE = '3' and IDRECURSO= '390074';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Asociaciones#GL ' where IDLENGUAJE = '4' and IDRECURSO= '390074';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedad Civil' where IDLENGUAJE = '1' and IDRECURSO= '390089';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Societat Civil' where IDLENGUAJE = '2' and IDRECURSO= '390089';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedad Civil#EU ' where IDLENGUAJE = '3' and IDRECURSO= '390089';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedad Civil#GL ' where IDLENGUAJE = '4' and IDRECURSO= '390089';
update cen_tiposociedad set LETRACIF = 'Y'  where DESCRIPCION = '390089';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedades Comanditarias' where IDLENGUAJE = '1' and IDRECURSO= '390089';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Societats Comanditàries' where IDLENGUAJE = '2' and IDRECURSO= '390089';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedades Comanditarias#EU ' where IDLENGUAJE = '3' and IDRECURSO= '390089';
update GEN_RECURSOS_CATALOGOS set DESCRIPCION = 'Sociedades Comanditarias#GL ' where IDLENGUAJE = '4' and IDRECURSO= '390089';
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390090','Comunidades de propietarios en régimen de propiedad horizontal','1',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.90');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390090','Comunitats de propietaris en règim de propietat horitzontal','2',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.90');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390090','Comunidades de propietarios en régimen de propiedad horizontal#EU ','3',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.90');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390090','Comunidades de propietarios en régimen de propiedad horizontal#GL ','4',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.90');
Insert into cen_tiposociedad (LETRACIF,DESCRIPCION,USUMODIFICACION,FECHAMODIFICACION,CODIGOEXT,FECHA_BAJA) values ('H','390090','0',to_date('23/03/11','DD/MM/RR'),null,to_date('21/05/18','DD/MM/RR'));
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390091','Corporaciones locales','1',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.91');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390091','Corporacions locals','2',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.91');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390091','Corporaciones locales#EU ','3',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.91');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390091','Corporaciones locales#GL ','4',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.91');
Insert into cen_tiposociedad (LETRACIF,DESCRIPCION,USUMODIFICACION,FECHAMODIFICACION,CODIGOEXT,FECHA_BAJA) values ('P','390091','0',to_date('23/03/11','DD/MM/RR'),null,to_date('21/05/18','DD/MM/RR'));
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390092','Organismos públicos','1',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.92');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390092','Organismes públics','2',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.92');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390092','Organismos públicos#EU ','3',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.92');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390092','Organismos públicos#GL ','4',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.92');
Insert into cen_tiposociedad (LETRACIF,DESCRIPCION,USUMODIFICACION,FECHAMODIFICACION,CODIGOEXT,FECHA_BAJA) values ('Q','390092','0',to_date('23/03/11','DD/MM/RR'),null,to_date('21/05/18','DD/MM/RR'));
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390093','Congregaciones e instituciones religiosas','1',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.93');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390093','Congregacions i institucions religioses','2',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.93');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390093','Congregaciones e instituciones religiosas#EU ','3',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.93');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390093','Congregaciones e instituciones religiosas#GL ','4',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.93');
Insert into cen_tiposociedad (LETRACIF,DESCRIPCION,USUMODIFICACION,FECHAMODIFICACION,CODIGOEXT,FECHA_BAJA) values ('R','390093','0',to_date('23/03/11','DD/MM/RR'),null,to_date('21/05/18','DD/MM/RR'));
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390094','Órganos de administración del Estado y de las Comunidades Autónomas','1',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.94');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390094','Òrgans d''administració de l''Estat i de les Comunitats Autònomes','2',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.94');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390094','Órganos de administración del Estado y de las Comunidades Autónomas#EU ','3',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.94');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390094','Órganos de administración del Estado y de las Comunidades Autónomas#GL ','4',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.94');
Insert into cen_tiposociedad (LETRACIF,DESCRIPCION,USUMODIFICACION,FECHAMODIFICACION,CODIGOEXT,FECHA_BAJA) values ('S','390094','0',to_date('23/03/11','DD/MM/RR'),null,to_date('21/05/18','DD/MM/RR'));
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390095','Uniones Temporales de Empresas','1',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.95');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390095','Unions Temporals d''Empreses','2',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.95');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390095','Uniones Temporales de Empresas#EU ','3',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.95');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390095','Uniones Temporales de Empresas#GL ','4',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.95');
Insert into cen_tiposociedad (LETRACIF,DESCRIPCION,USUMODIFICACION,FECHAMODIFICACION,CODIGOEXT,FECHA_BAJA) values ('U','390095','0',to_date('23/03/11','DD/MM/RR'),null,to_date('21/05/18','DD/MM/RR'));
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390096','Otros tipos no definidos en el resto de claves','1',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.96');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390096','Altres tipus no definits en la resta de claus','2',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.96');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390096','Otros tipos no definidos en el resto de claves#EU ','3',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.96');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('390096','Otros tipos no definidos en el resto de claves#GL ','4',to_date('23/03/11','DD/MM/RR'),'0',null,'CEN_TIPOSOCIEDAD','DESCRIPCION','cen_tiposociedad.descripcion.0.96');
Insert into cen_tiposociedad (LETRACIF,DESCRIPCION,USUMODIFICACION,FECHAMODIFICACION,CODIGOEXT,FECHA_BAJA) values ('V','390096','0',to_date('23/03/11','DD/MM/RR'),null,to_date('21/05/18','DD/MM/RR'));
--delete from cen_tiposociedad where descripcion = '390048';
--delete from GEN_RECURSOS_CATALOGOS where IDRECURSO = '390048';
update gen_menu set idparent = '90' where idmenu='96';
update gen_menu set idparent = '90' where idmenu='95';
update gen_menu set fecha_baja = '24/05/2018' where idmenu = '127';
update gen_menu set fecha_baja = null where idrecurso='menu.administracion.informes';
update gen_menu set idparent = '83' where idrecurso='menu.administracion.informes';

update  GEN_PROCESOS set idPArent = '00' ,idModulo = 'CEN' where idProceso = '112';
Insert into GEN_PROCESOS (IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) 
values ('320','ADM','1','Y',to_date('20/11/07','DD/MM/RR'),'0','Contadores','ADM_Contadores','013','10');
update gen_procesos  set idparent = '012' where idproceso = 'I00';

Insert into GEN_MENU (IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,PATH,FECHA_BAJA,IDCLASS) 
values ('320','19962','175','90',to_date('20/11/07','DD/MM/RR'),'0',null,'menu.administracion.contador',null,'320',null,'contadores/0',null,null);
update GEN_PROCESOS set descripcion = 'HIDDEN_Desplegar Menú Recursos Multiidioma' where IDPROCESO = '019'; -- Resolucion indicencia SIGARNV-79 (33483)

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosColegiacion.literal.fechaNac','Fecha Nacimiento','0','1',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosColegiacion.literal.fechaNac','Fecha Nacimiento#GL','0','4',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosColegiacion.literal.fechaNac','Data Naixement','0','2',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosColegiacion.literal.fechaNac','Fecha Nacimiento#EU','0','3',to_date('10/12/05','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.usarFoto','Permitir utilizar mi foto en Guía Colegial','0','1',to_date('08/03/12','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.usarFoto','Permitir utilizar mi foto en Guía Colegial#GL','0','4',to_date('08/03/12','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.usarFoto','Permetre utilitzar la meva foto en Guia Col·legial','0','2',to_date('08/03/12','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.usarFoto','Permitir utilizar mi foto en Guía Colegial#EU','0','3',to_date('08/03/12','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.literal.identificacion','Identificación','0','1',to_date('08/03/12','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.literal.identificacion','Identificación#GL','0','4',to_date('08/03/12','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.literal.identificacion','Identificació','0','2',to_date('08/03/12','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.literal.identificacion','Identificación#EU','0','3',to_date('08/03/12','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantenimientoTablasMaestra.literal.apellidos','Apellidos','0','1',to_date('24/01/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantenimientoTablasMaestra.literal.apellidos','Cognoms','0','2',to_date('24/01/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantenimientoTablasMaestra.literal.apellidos','Apellidos#EU','0','3',to_date('24/01/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantenimientoTablasMaestra.literal.apellidos','Apellidos#GL','0','4',to_date('24/01/06','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.edad','Edad','0','1',to_date('30/07/08','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.edad','Edad#GL','0','4',to_date('30/07/08','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.edad','Edat','0','2',to_date('30/07/08','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.edad','Edad#EU','0','3',to_date('30/07/08','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.nacido','Lugar de Nacimiento#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.nacido','Lloc de Naixement','0','2',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.nacido','Lugar de Nacimiento#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.nacido','Lugar de Nacimiento','0','1',to_date('10/03/04','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.tratamiento','Tratamiento#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.tratamiento','Tratamiento#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.tratamiento','Tractament','0','2',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.tratamiento','Tratamiento','0','1',to_date('10/03/04','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.situacion.cabecera','Situación','0','1',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.situacion.cabecera','Situación#GL','0','4',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.situacion.cabecera','Situació','0','2',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.situacion.cabecera','Situación#EU','0','3',to_date('10/12/05','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.situacion.idiomaPref','Idioma de preferencia','0','1',to_date('29/05/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.situacion.idiomaPref','Idioma de preferencia#GL','0','4',to_date('29/05/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.situacion.idiomaPref','Idioma de preferència','0','2',to_date('29/05/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.fichaCliente.situacion.idiomaPref','Idioma de preferencia#EU','0','3',to_date('29/05/18','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.cuentaContable','Cuenta contable#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.cuentaContable','Compte comptable','0','2',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.cuentaContable','Cuenta contable#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.cuentaContable','Cuenta contable','0','1',to_date('10/03/04','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.noExportarDatos','No exportar datos#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.noExportarDatos','No exportar dades','0','2',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.noExportarDatos','No exportar datos#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.noExportarDatos','No exportar datos','0','1',to_date('10/03/04','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatos.literal.fechaBaja','Fecha Baja','0','1',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatos.literal.fechaBaja','Fecha Baja#GL','0','4',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatos.literal.fechaBaja','Fecha Baja#EU','0','3',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatos.literal.fechaBaja','Data Baixa','0','2',to_date('10/12/05','DD/MM/RR'),'0','19');
	
update gen_diccionario set descripcion = 'Se han restaurado ' where idrecurso = 'general.message.registros.restaurados' and idlenguaje = 1;

Update gen_menu Set orden = orden + 13000 Where idparent In ('90','91','I00','019','125','126','127','92','93','97','99A','99B','310','311','300','132','135','95','96');


COMMIT;