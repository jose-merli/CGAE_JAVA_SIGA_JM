
spool TRADUCCIONES JUZGADO MENSAJES BACK.log
prompt TRADUCCIONES JUZGADO MENSAJES BACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionJuzgado.existeJuzgadoMismoNombre','Ya existe un juzgado con ese nombre','0','1',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionJuzgado.existeJuzgadoMismoNombre','Ja existeix una societat amb aquest número de registre','0','2',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionJuzgado.existeJuzgadoMismoNombre','Ya existe un juzgado con ese nombre#GL','0','4',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionJuzgado.existeJuzgadoMismoNombre','Ya existe un juzgado con ese nombre#EU','0','3',to_date('20/09/19','DD/MM/RR'),'0','19');


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
