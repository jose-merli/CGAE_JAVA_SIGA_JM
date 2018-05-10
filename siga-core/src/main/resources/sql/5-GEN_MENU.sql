
ALTER TABLE GEN_MENU ADD PATH VARCHAR(50) NULL;
ALTER TABLE GEN_MENU ADD IDCLASS VARCHAR(50) NULL;
ALTER TABLE GEN_MENU ADD FECHA_BAJA DATE NULL;


UPDATE GEN_MENU SET PATH = 'catalogosMaestros' WHERE IDRECURSO = 'menu.administracion.gestionCatalogosMaestros';
UPDATE GEN_MENU SET PATH = 'gruposUsuarios' WHERE IDRECURSO = 'menu.administracion.gruposDeUsuarios';
UPDATE GEN_MENU SET PATH = 'etiquetas' WHERE IDRECURSO = 'menu.administracion.gestionMultiidioma.etiquetas';
UPDATE GEN_MENU SET PATH = 'usuarios' WHERE IDRECURSO = 'menu.administracion.usuarios';
UPDATE GEN_MENU SET PATH = 'seleccionarIdioma' WHERE IDRECURSO = 'menu.administracion.seleccionarIdioma';

UPDATE GEN_MENU SET PATH = 'busquedaColegiados' WHERE IDRECURSO = 'menu.censo.buscarColegiados';
UPDATE GEN_MENU SET PATH = 'fichaColegial/:id' WHERE IDRECURSO = 'menu.censo.fichaColegial';
UPDATE GEN_MENU SET PATH = 'searchNoColegiados' WHERE IDRECURSO = 'menu.censo.buscarNoColegiados';
--UPDATE GEN_MENU SET PATH = 'certificadosAca' WHERE IDRECURSO = '';
--UPDATE GEN_MENU SET PATH = 'comisionesCargos' WHERE IDRECURSO = '';
UPDATE GEN_MENU SET PATH = 'solicitudesGenericas' WHERE IDRECURSO = 'menu.censo.verSolicitudesModificacion.solicitudesGenericas';
UPDATE GEN_MENU SET PATH = 'solicitudesEspecificas' WHERE IDRECURSO = 'menu.censo.verSolicitudesModificacion.solicitudesEspecificas';
UPDATE GEN_MENU SET PATH = 'solicitudesIncorporacion' WHERE IDRECURSO = 'menu.censo.verSolicitudesIncorporacion';
UPDATE GEN_MENU SET PATH = 'nuevaIncorporacion' WHERE IDRECURSO = 'menu.solicitudIncorporacion';
--UPDATE GEN_MENU SET PATH = 'documentacionSolicitudes' WHERE IDRECURSO = '';
UPDATE GEN_MENU SET PATH = 'mantenimientoGruposFijos' WHERE IDRECURSO = 'menu.censo.mantenimientoGruposFijos';
UPDATE GEN_MENU SET PATH = 'mantenimientoMandatos' WHERE IDRECURSO = 'menu.censo.mantenimientoMandatos';
UPDATE GEN_MENU SET PATH = 'busquedaSanciones' WHERE IDRECURSO = 'menu.expedientes.sanciones';
UPDATE GEN_MENU SET PATH = 'parametrosGenerales' WHERE IDRECURSO = 'menu.administracion.parametrosGenerales';
UPDATE GEN_MENU SET PATH = 'auditoriaUsuarios' WHERE IDRECURSO = 'menu.administracion.auditoria.usuarios';
UPDATE GEN_MENU SET PATH = 'catalogos' WHERE IDRECURSO = 'menu.administracion.gestionMultiidioma.catalogosMaestros';

update gen_menu set IDPARENT = ' ' WHERE IDRECURSO IN ('menu.administracion.gestionCatalogosMaestros');
update gen_menu set CLASS = 'catalogos' WHERE IDRECURSO IN ('menu.administracion.gestionCatalogosMaestros');
update gen_menu set CLASS = 'adm' WHERE IDRECURSO IN ('menu.administracion');
update gen_menu set CLASS = 'plantillas' WHERE IDRECURSO IN ('menu.administracion.gestionCatalogosMaestros');

UPDATE GEN_MENU SET fecha_baja = to_date('26/04/2018','DD/MM/RRRR') WHERE IDRECURSO = 'menu.administracion.perfilrol';

update gen_menu set idrecurso = 'administracion.usuarios.literal.usuariosyperfiles' where idrecurso = 'menu.administracion.usuariosygrupos';

insert into GEN_MENU (IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,PATH,FECHA_BAJA,IDCLASS) values ('999','31000','100',' ',to_date('04/02/2018','DD/MM/RRRR'),'0',null,'menu.configuracion',null,'999','1',' ',null,'config');

update gen_menu set fecha_baja = sysdate where idMenu = '92';
update gen_menu set fecha_baja = sysdate where idMenu = '310';
update gen_menu set fecha_baja = sysdate where idMenu = '311';
update gen_menu set path = 'auditoriaUsuarios' where idMenu = '125';

update gen_menu set fecha_baja = sysdate where idMenu = '300';
update gen_menu set idrecurso = 'menu.administracion.parametrosGenerales' where idMenu = '126';
update gen_menu set path = 'parametrosGenerales' where idMenu = '126';
																				