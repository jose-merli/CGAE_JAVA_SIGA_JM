
spool TRADUCCIONES ZONAS Y SUBZONAS MENSAJES BACK.log
prompt TRADUCCIONES ZONAS Y SUBZONAS MENSAJES BACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeGrupoZonaMismaDescripcion','Ya existe un grupo zona con la misma descripción','0','1',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeGrupoZonaMismaDescripcion','Ja existeix un grup zona amb la mateixa descripció','0','2',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeGrupoZonaMismaDescripcion','Ya existe un grupo zona con la misma descripción#GL','0','4',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeGrupoZonaMismaDescripcion','Ya existe un grupo zona con la misma descripción#EU','0','3',to_date('20/09/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeSubZonaMismaDescripcion','Ya existe una subzona con la misma descripción','0','1',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeSubZonaMismaDescripcion','Ja hi ha una subzona amb la mateixa descripció','0','2',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeSubZonaMismaDescripcion','Ya existe una subzona con la misma descripción#GL','0','4',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeSubZonaMismaDescripcion','Ya existe una subzona con la misma descripción#EU','0','3',to_date('20/09/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeZonaMismaDescripcion','Ya existe una zona con la misma descripción','0','1',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeZonaMismaDescripcion','Ja hi ha una zona amb la mateixa descripció','0','2',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeZonaMismaDescripcion','Ya existe una zona con la misma descripción#GL','0','4',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.existeZonaMismaDescripcion','Ya existe una zona con la misma descripción#EU','0','3',to_date('20/09/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.seleccionarPartidoJudicial','Debe seleccionar al menos un partido judicial','0','1',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.seleccionarPartidoJudicial','Heu de seleccionar com a mínim un partit judicial','0','2',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.seleccionarPartidoJudicial','Debe seleccionar al menos un partido judicial#GL','0','4',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionZonasySubzonas.seleccionarPartidoJudicial','Debe seleccionar al menos un partido judicial#EU','0','3',to_date('20/09/19','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
CostesFijosCosteFijoCaracterísticacoste fijo con las mismas características