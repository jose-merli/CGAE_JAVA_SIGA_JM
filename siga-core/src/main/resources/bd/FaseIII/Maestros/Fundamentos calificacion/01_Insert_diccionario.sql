spool  01_Insert_diccionario.log
prompt 01_Insert_diccionario.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.tabla.sinResultados','Sin resultados','0','1',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.tabla.sinResultados','Sense resultats','0','2',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.tabla.sinResultados','Sin resultados#GL','0','4',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.tabla.sinResultados','Sin resultados#EU','0','3',to_date('24/09/19','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.fundamentos.fundamentosCalificacion','Fundamentos Calificación','0','1',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.fundamentos.fundamentosCalificacion','Fonaments Qualificació','0','2',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.fundamentos.fundamentosCalificacion','Fundamentos Calificación#GL','0','4',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.fundamentos.fundamentosCalificacion','Fundamentos Calificación#EU','0','3',to_date('24/09/19','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.gestion.gestionFundamentosCalificacion','Gestión Fundamentos Calificación','0','1',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.gestion.gestionFundamentosCalificacion','Gestió Fonaments Qualificació','0','2',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.gestion.gestionFundamentosCalificacion','Gestión Fundamentos Calificación#GL','0','4',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.gestion.gestionFundamentosCalificacion','Gestión Fundamentos Calificación#EU','0','3',to_date('24/09/19','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.datosGenerales.dictamen','Dictamen','0','1',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.datosGenerales.dictamen','Dictamen','0','2',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.datosGenerales.dictamen','Dictamen#GL','0','4',to_date('24/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.fundamentosCalificacion.datosGenerales.dictamen','Dictamen#EU','0','3',to_date('24/09/19','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
