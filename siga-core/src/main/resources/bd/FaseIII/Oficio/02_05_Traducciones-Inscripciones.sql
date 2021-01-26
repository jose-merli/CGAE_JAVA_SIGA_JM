spool 05_Traducciones-Inscripciones.log
prompt 05_Traducciones-Inscripciones.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.seguimientoInscripcion','Seguimiento Inscripci�n','0','1',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.seguimientoInscripcion','Seguimiento Inscripci�n','0','2',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.seguimientoInscripcion','Seguimiento Inscripci�n#GL','0','4',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.seguimientoInscripcion','Seguimiento Inscripci�n#EU','0','3',to_date('09/10/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.accion','Acci�n','0','1',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.accion','Acci�n','0','2',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.accion','Acci�n#GL','0','4',to_date('09/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.oficio.inscripciones.accion','Acci�n#EU','0','3',to_date('09/10/19','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off