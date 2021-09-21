spool 03_Traducciones-Inscripciones.log
prompt 03_Traducciones-Inscripciones.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.cambiarfecha','Cambiar Fecha','0','1',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.cambiarfecha','Canviar Data','0','2',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.cambiarfecha','Cambiar Fecha#GL','0','4',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.cambiarfecha','Cambiar Fecha#EU','0','3',to_date('09/10/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.mensajesolicitarbaja','No se puede solicitar la baja del letrado porque la fecha no es la de hoy','0','1',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.mensajesolicitarbaja','No es pot sol·licitar la baixa de l''lletrat perquè la data no és la d''avui','0','2',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.mensajesolicitarbaja','No se puede solicitar la baja del letrado porque la fecha no es la de hoy#GL','0','4',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.mensajesolicitarbaja','No se puede solicitar la baja del letrado porque la fecha no es la de hoy#EU','0','3',to_date('09/10/19','DD/MM/RR'),'0','19');


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off