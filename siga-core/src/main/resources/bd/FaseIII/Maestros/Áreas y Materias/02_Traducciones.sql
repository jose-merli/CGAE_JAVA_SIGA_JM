spool 02_Traducciones.log
prompt 02_Traducciones.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('maestros.areasmaterias.literal.contenido','Contenido#GL','0','4',to_date('20/04/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('maestros.areasmaterias.literal.contenido','Contenido','0','1',to_date('20/04/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('maestros.areasmaterias.literal.contenido','Contingut','0','2',to_date('20/04/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('maestros.areasmaterias.literal.contenido','Contenido#EU','0','3',to_date('20/04/19','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.Materias','Materias','0','1',to_date('20/03/18','DD/MM/RR'),'0','22');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.Materias','Matèries','0','2',to_date('20/03/18','DD/MM/RR'),'0','22');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.Materias','Materias#EU','0','3',to_date('20/03/18','DD/MM/RR'),'0','22');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.Materias','Materias#GL','0','4',to_date('20/03/18','DD/MM/RR'),'0','22');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.censo.nombreExiste','Ya existe un registro con el mismo nombre','0','1',to_date('16/01/07','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.censo.nombreExiste','Ja existeix un registre amb el mateix nom','0','2',to_date('16/01/07','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.censo.nombreExiste','Ya existe un registro con el mismo nombre#EU','0','3',to_date('16/01/07','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.censo.nombreExiste','Ya existe un registro con el mismo nombre#GL','0','4',to_date('16/01/07','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.erroreliminadomaterias','No se han eliminado las materias','0','1',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.erroreliminadomaterias','No s''han eliminat les matèries','0','2',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.erroreliminadomaterias','No se han eliminado las materias#EU','0','3',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.erroreliminadomaterias','No se han eliminado las materias#GL','0','4',to_date('04/02/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.actualizarerror','No se ha podido actualizar el registro','0','1',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.actualizarerror','No s''ha pogut actualitzar el registre','0','2',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.actualizarerror','No se ha podido actualizar el registro#EU','0','3',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.actualizarerror','No se ha podido actualizar el registro#GL','0','4',to_date('04/02/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.eliminarError','Error al eliminar el registro','0','1',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.eliminarError','Error en eliminar el registre','0','2',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.eliminarError','Error al eliminar el registro#EU','0','3',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.eliminarError','Error al eliminar el registro#GL','0','4',to_date('04/02/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.materiaEnUso','Una o varias de las materias que ha intentado eliminar ya se encuentra en uso','0','1',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.materiaEnUso','Una o diverses de les matèries que ha intentat eliminar ja es troba en ús','0','2',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.materiaEnUso','Una o varias de las materias que ha intentado eliminar ya se encuentra en uso#EU','0','3',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('areasmaterias.materias.ficha.materiaEnUso','Una o varias de las materias que ha intentado eliminar ya se encuentra en uso#GL','0','4',to_date('04/02/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.GestionaAreasYMaterias','Gestión Áreas y Materias','0','1',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.GestionAreasYMaterias','Gestión Àrees i Matèries','0','2',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.GestionAreasYMaterias','Gestión Áreas y Materias#EU','0','3',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.GestionAreasYMaterias','Gestión Áreas y Materias#GL','0','4',to_date('04/02/19','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off