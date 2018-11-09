
ALTER TABLE GEN_MENU ADD PATH VARCHAR(50) NULL;

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

COMMIT;