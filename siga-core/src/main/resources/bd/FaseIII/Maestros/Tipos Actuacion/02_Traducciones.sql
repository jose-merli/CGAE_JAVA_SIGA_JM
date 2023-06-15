spool 02_Traducciones.log
prompt 02_Traducciones.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.tiposActuacion','Tipos de Actuación','0','1',to_date('10/03/04','DD/MM/RR'),'0','22');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.tiposActuacion','Tipus d''Actuació','0','2',to_date('10/03/04','DD/MM/RR'),'0','22');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.tiposActuacion','Tipos de Actuación#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','22');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.tiposActuacion','Tipos de Actuación#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','22');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.tiposActuacion','Tipos de Actuación','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.tiposActuacion','Tipus d''Actuació','0','2',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.tiposActuacion','Tipos de Actuación#GL','0','3',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.tiposActuacion','Tipos de Actuación#EU','0','4',to_date('01/10/19','DD/MM/RR'),'0','19'); 

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.mensaje.error.tipoactuacion','No se puede eliminar el tipo de asistencia para el tipo de actuación: ','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.mensaje.error.tipoactuacion','No es pot eliminar el tipus d''assistència per al tipus d''actuació:','0','2',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.mensaje.error.tipoactuacion','No se puede eliminar el tipo de asistencia#GL','0','3',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.mensaje.error.tipoactuacion','No se puede eliminar el tipo de asistencia#EU','0','4',to_date('01/10/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeTipoActuacionMismaDescripcion','Ya existe un tipo de actuacion con ese nombre','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeTipoActuacionMismaDescripcion','Ja existeix un tipus d''actuació amb aquest nom','0','2',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeTipoActuacionMismaDescripcion','Ya existe un tipo de actuacion con ese nombre#GL','0','3',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeTipoActuacionMismaDescripcion','Ya existe un tipo de actuacion con ese nombre#EU','0','4',to_date('01/10/19','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off