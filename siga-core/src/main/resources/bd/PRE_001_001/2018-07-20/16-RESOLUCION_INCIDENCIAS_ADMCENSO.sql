
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.parametrosGenerales.literal.nombre.apellidos','Cognoms y Nom','0','2',to_date('14/08/07','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.parametrosGenerales.literal.nombre.apellidos','Apellidos y Nombre','0','1',to_date('14/08/07','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.parametrosGenerales.literal.nombre.apellidos','Apellidos y Nombre#EU','0','3',to_date('14/08/07','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.parametrosGenerales.literal.nombre.apellidos','Apellidos y Nombre#GL','0','4',to_date('14/08/07','DD/MM/RR'),'0','15');

update gen_diccionario set descripcion = 'Idioma búsqueda' where idrecurso = 'administracion.multidioma.etiquetas.literal.idioma' and idlenguaje = 1;
update gen_diccionario set descripcion = 'Idioma recerca' where idrecurso = 'administracion.multidioma.etiquetas.literal.idioma' and idlenguaje = 2;
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.multidioma.etiquetas.literal.textobuscar','Texto a buscar','0','1',to_date('10/05/18','DD/MM/RR'),'1','8');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.multidioma.etiquetas.literal.textobuscar','Text a cercar','0','2',to_date('10/11/04','DD/MM/RR'),'0','8');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.multidioma.etiquetas.literal.textobuscar','Texto a buscar#EU','0','3',to_date('10/11/04','DD/MM/RR'),'0','8');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.multidioma.etiquetas.literal.textobuscar','Texto a buscar#GL','0','4',to_date('10/11/04','DD/MM/RR'),'0','8');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.administracion.gestionMultiidioma.catalogo','Catálogo','0','1',to_date('25/04/18','DD/MM/RR'),'1','8');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.administracion.gestionMultiidioma.catalogo','Catàleg','0','2',to_date('25/04/18','DD/MM/RR'),'0','8');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.administracion.gestionMultiidioma.catalogo','Catálogo#EU','0','3',to_date('25/04/18','DD/MM/RR'),'0','8');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.administracion.gestionMultiidioma.catalogo','Catálogo#GL','0','4',to_date('25/04/18','DD/MM/RR'),'0','8');
update gen_procesos set descripcion = 'HIDDEN_Etiquetas' where descripcion = 'Etiquetas';
DELETE FROM ADM_TIPOSACCESO WHERE IDPROCESO = '99A' AND IDINSTITUCION <> '2000';

update gen_menu set path = 'contadores/0/admin' where path = 'contadores/0';
update gen_menu set path = 'contadores/3/censo' where path = 'contadores/3';

commit;