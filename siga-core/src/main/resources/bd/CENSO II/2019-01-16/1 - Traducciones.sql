UPDATE gen_diccionario SET DESCRIPCION = 'Carga masiva de etiquetas' where idrecurso = 'menu.cen.cargaMasivaGruposFijos' and idlenguaje = '1';
UPDATE gen_diccionario SET DESCRIPCION = 'Càrrega massiva d''etiquetess' where idrecurso = 'menu.cen.cargaMasivaGruposFijos' and idlenguaje = '2';
UPDATE gen_diccionario SET DESCRIPCION = 'Carga masiva de etiquetas#EU' where idrecurso = 'menu.cen.cargaMasivaGruposFijos' and idlenguaje = '3';
UPDATE gen_diccionario SET DESCRIPCION = 'Carga masiva de etiquetas#GL' where idrecurso = 'menu.cen.cargaMasivaGruposFijos' and idlenguaje = '4';

UPDATE gen_recursos SET DESCRIPCION = 'Carga masiva de etiquetas' where idrecurso = 'menu.cen.cargaMasivaGruposFijos' and idlenguaje = '1';
UPDATE gen_recursos SET DESCRIPCION = 'Càrrega massiva d''etiquetess' where idrecurso = 'menu.cen.cargaMasivaGruposFijos' and idlenguaje = '2';
UPDATE gen_recursos SET DESCRIPCION = 'Carga masiva de etiquetas#EU' where idrecurso = 'menu.cen.cargaMasivaGruposFijos' and idlenguaje = '3';
UPDATE gen_recursos SET DESCRIPCION = 'Carga masiva de etiquetas#GL' where idrecurso = 'menu.cen.cargaMasivaGruposFijos' and idlenguaje = '4';


Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.tiposDatosCurriculares','Gestión de tipos CV','0','1',to_date('09/06/15','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.tiposDatosCurriculares','Gestió de tipus CV','0','2',to_date('09/06/15','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.tiposDatosCurriculares','Gestión de tipos CV#EU','0','3',to_date('09/06/15','DD/MM/RR'),'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.tiposDatosCurriculares','Gestión de tipos CV#GL','0','4',to_date('09/06/15','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.tiposDatosCurriculares','Gestión de tipos CV','0','1',to_date('09/06/15','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.tiposDatosCurriculares','Gestió de tipus CV','0','2',to_date('09/06/15','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.tiposDatosCurriculares','Gestión de tipos CV#EU','0','3',to_date('09/06/15','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.cen.tiposDatosCurriculares','Gestión de tipos CV#GL','0','4',to_date('09/06/15','DD/MM/RR'),'0','19');


update GEN_MENU set idrecurso = 'menu.cen.tiposDatosCurriculares' where idmenu = '15E';



Insert into GEN_PROCESOS (IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL)
values ('15H','CEN','1','Y',to_date('09/06/15','DD/MM/RR'),'0','Gestión de tipos de datos curriculares','CEN_GestionTiposCV','180','10');

Insert into GEN_MENU (IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,IDCLASS,FECHA_BAJA,PATH) 
values ('15H','17040','180','162',to_date('22/04/15','DD/MM/RR'),'0',null,'menu.cen.subtiposDatosCurriculares',null,'15H','1',null,null,'informacionGestionSubtipoCV');


Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('310060','Baja Por Deceso','1',sysdate,'0',null,'CEN_ESTADOCOLEGIAL','DESCRIPCION','cen_estadocolegial.descripcion.0.10');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('310060','Baixa Per Decés','2',sysdate,'0',null,'CEN_ESTADOCOLEGIAL','DESCRIPCION','cen_estadocolegial.descripcion.0.10');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('310060','Baja Por Deceso#EU','3',sysdate,'0',null,'CEN_ESTADOCOLEGIAL','DESCRIPCION','cen_estadocolegial.descripcion.0.10');
Insert into GEN_RECURSOS_CATALOGOS (IDRECURSO,DESCRIPCION,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDINSTITUCION,NOMBRETABLA,CAMPOTABLA,IDRECURSOALIAS) values ('310060','Baja Por Deceso#GL','4',sysdate,'0',null,'CEN_ESTADOCOLEGIAL','DESCRIPCION','cen_estadocolegial.descripcion.0.10');

insert into cen_estadocolegial (IDESTADO,DESCRIPCION,FECHAMODIFICACION,USUMODIFICACION) values(60,310060,SYSDATE,0);


