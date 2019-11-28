spool  02_Traducciones.log
prompt 02_Traducciones.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('formacion.fichaCurso.tarjetaPrecios.importeMaximo','Importe Máximo','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('formacion.fichaCurso.tarjetaPrecios.importeMaximo','Import Màxim','0','2',to_date('01/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('formacion.fichaCurso.tarjetaPrecios.importeMaximo','Importe Máximo#EU','0','3',to_date('01/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('formacion.fichaCurso.tarjetaPrecios.importeMaximo','Importe Máximo#GL','0','4',to_date('01/10/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('maestros.tiposasistencia.tipoGuardia','Tipos de Guardia','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('maestros.tiposasistencia.tipoGuardia','Tipus de Guàrdia','0','2',to_date('01/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('maestros.tiposasistencia.tipoGuardia','Tipos de Guardia#EU','0','3',to_date('01/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('maestros.tiposasistencia.tipoGuardia','Tipos de Guardia#GL','0','4',to_date('01/10/19','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.informes.literal.visibleMovil','Visible Móvil','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.informes.literal.visibleMovil','Visible Mòbil','0','2',to_date('01/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.informes.literal.visibleMovil','Visible Móvil#EU','0','3',to_date('01/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.informes.literal.visibleMovil','Visible Móvil#GL','0','4',to_date('01/10/19','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeTipoAsistenciaMismaDescripcion','Ya existe un tipo de asistencia con ese nombre','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeTipoAsistenciaMismaDescripcion','Ja existeix un tipus d''assistència amb aquest nom','0','2',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeTipoAsistenciaMismaDescripcion','Ya existe un tipo de asistencia con ese nombre#GL','0','3',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeTipoAsistenciaMismaDescripcion','Ya existe un tipo de asistencia con ese nombre#EU','0','4',to_date('01/10/19','DD/MM/RR'),'0','19'); 

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.datosBancarios.mensaje.seleccionar.almenosUnoPorDefecto','Debes seleccionar un tipo de asistencia por defecto','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.datosBancarios.mensaje.seleccionar.almenosUnoPorDefecto','Has de seleccionar un tipus d''assistència per defecte','0','2',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.datosBancarios.mensaje.seleccionar.almenosUnoPorDefecto','Debes seleccionar un tipo de asistencia por defecto#GL','0','3',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.datosBancarios.mensaje.seleccionar.almenosUnoPorDefecto','Debes seleccionar un tipo de asistencia por defecto#EU','0','4',to_date('01/10/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.confirmacionpordefecto','Otro tipo de asistencia ya estaba marcada por defecto. ¿Desea cambiar a este tipo por defecto?','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.confirmacionpordefecto','Un altre tipus d''assistència ja estava marcada per defecte. Voleu canviar a aquest tipus per defecte?','0','2',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.confirmacionpordefecto','Otro tipo de asistencia ya estaba marcada por defecto. ¿Desea cambiar a este tipo por defecto?#GL','0','3',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.confirmacionpordefecto','Otro tipo de asistencia ya estaba marcada por defecto. ¿Desea cambiar a este tipo por defecto?#EU','0','4',to_date('01/10/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.confirmacionespordefecto','Es necesario indicar un tipo de asistencia por defecto. ¿Desea que este sea el tipo por defecto?','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.confirmacionespordefecto','Cal indicar un tipus d''assistència per defecte. Voleu que aquest sigui el tipus per defecte?','0','2',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.confirmacionespordefecto','Es necesario indicar un tipo de asistencia por defecto. ¿Desea que este sea el tipo por defecto?#GL','0','3',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.confirmacionespordefecto','Es necesario indicar un tipo de asistencia por defecto. ¿Desea que este sea el tipo por defecto?#EU','0','4',to_date('01/10/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.nosepuedeeliminarpordefecto','No se puede eliminar el tipo asistencia por defecto.','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.nosepuedeeliminarpordefecto','No es pot eliminar el tipus assistència per defecte','0','2',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.nosepuedeeliminarpordefecto','No se puede eliminar el tipo asistencia por defecto#GL','0','3',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.nosepuedeeliminarpordefecto','No se puede eliminar el tipo asistencia por defecto#EU','0','4',to_date('01/10/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.nosepuedeeliminardependetipoactuacion','No se puede eliminar el tipo asistencia porque está siendo utilizado en un tipo actuación.','0','1',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.nosepuedeeliminardependetipoactuacion','No es pot eliminar el tipus assistència perquè està sent utilitzat en un tipus actuació','0','2',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.nosepuedeeliminardependetipoactuacion','No se puede eliminar el tipo asistencia porque está siendo utilizado en un tipo actuación#GL','0','3',to_date('01/10/19','DD/MM/RR'),'0','19'); 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.nosepuedeeliminardependetipoactuacion','No se puede eliminar el tipo asistencia porque está siendo utilizado en un tipo actuación#EU','0','4',to_date('01/10/19','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
