Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargaMasivaDatosCurriculares.literal.nombreFichero','Nombre Fichero','0','1',to_date('26/09/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargaMasivaDatosCurriculares.literal.nombreFichero','Nombre Fichero#GL','0','4',to_date('26/09/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargaMasivaDatosCurriculares.literal.nombreFichero','Nombre Fichero#CA','0','2',to_date('26/09/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargaMasivaDatosCurriculares.literal.nombreFichero','Nombre Fichero#EU','0','3',to_date('26/09/18','DD/MM/RR'),'0','19');


update GEN_MENU set PATH = 'datosCv' where IDRECURSO = 'menu.cen.cargaMasivaDatosCurriculares';
commit; 
 

 ALTER TABLE cen_cargaMasiva
ADD NUMREGISTROSERRONEOS NUMBER(6,0);
commit;

UPDATE cen_cargaMasiva
SET NUMREGISTROSERRONEOS = 0;
commit;

ALTER TABLE cen_cargaMasiva MODIFY NUMREGISTROSERRONEOS NUMBER(6,0) NOT NULL;
commit;


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.descargar.informe','Descargar Informe','0','1',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.descargar.informe','Descargar Informe#GL','0','4',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.descargar.informe','Descargar Informe#CA','0','2',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.descargar.informe','Descargar Informe#EU','0','3',to_date('04/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('cargaMasivaDatosCurriculares.numRegistrosCorrectos.literal','Registros Correctos','0','1',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('cargaMasivaDatosCurriculares.numRegistrosCorrectos.literal','Registros Correctos#GL','0','4',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('cargaMasivaDatosCurriculares.numRegistrosCorrectos.literal','Registros Correctos#CA','0','2',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('cargaMasivaDatosCurriculares.numRegistrosCorrectos.literal','Registros Correctos#EU','0','3',to_date('04/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('cargaMasivaDatosCurriculares.numRegistrosErroneos.literal','Registros Erroneos','0','1',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('cargaMasivaDatosCurriculares.numRegistrosErroneos.literal','Registros Erroneos#GL','0','4',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('cargaMasivaDatosCurriculares.numRegistrosErroneos.literal','Registros Erroneos#CA','0','2',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('cargaMasivaDatosCurriculares.numRegistrosErroneos.literal','Registros Erroneos#EU','0','3',to_date('04/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargasMasivas.literal','Censo Cargas Masivas','0','1',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargasMasivas.literal','Censo Cargas Masiva#GL','0','4',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargasMasivas.literal','Censo Cargas Masiva#CA','0','2',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargasMasivas.literal','Censo Cargas Masiva#EU','0','3',to_date('04/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargasMasivas.tipoCarga.literal','Tipo de carga','0','1',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargasMasivas.tipoCarga.literal','Tipo de carga#GL','0','4',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargasMasivas.tipoCarga.literal','Tipo de carga#CA','0','2',to_date('04/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.cargasMasivas.tipoCarga.literal','Tipo de carga#EU','0','3',to_date('04/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('modificacionDatos.boton.procesarSolicitud','Procesar Solicitud','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('modificacionDatos.boton.procesarSolicitud','Procesar Solicitud#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('modificacionDatos.boton.procesarSolicitud','Procesar Solicitud#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('modificacionDatos.boton.procesarSolicitud','Procesar Solicitud#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('nuevaSolicitud.nuevaSolicitudesModificacion.literal','Nueva Solicitud de Modificación','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('nuevaSolicitud.nuevaSolicitudesModificacion.literal','Nueva Solicitud de Modificación#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('nuevaSolicitud.nuevaSolicitudesModificacion.literal','Nueva Solicitud de Modificación#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('nuevaSolicitud.nuevaSolicitudesModificacion.literal','Nueva Solicitud de Modificación#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudModificacion.literal.titulo','Solicitud de Modificación de Datos','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudModificacion.literal.titulo','Solicitud de Modificación de Datos#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudModificacion.literal.titulo','Solicitud de Modificación de Datos#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudModificacion.literal.titulo','Solicitud de Modificación de Datos#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudTextoLibre.literal.tipoModificacion','Tipo de Modificación','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudTextoLibre.literal.tipoModificacion','Tipo de Modificación#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudTextoLibre.literal.tipoModificacion','Tipo de Modificación#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.solicitudTextoLibre.literal.tipoModificacion','Tipo de Modificación#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesModificacion.literal.estado','Estado','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesModificacion.literal.estado','Estado#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesModificacion.literal.estado','Estado#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesModificacion.literal.estado','Estado#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesTextoLibre.literal.fechaDesde','Fecha Desde','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesTextoLibre.literal.fechaDesde','Fecha Desde#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesTextoLibre.literal.fechaDesde','Fecha Desde#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesTextoLibre.literal.fechaDesde','Fecha Desde#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');



Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesTextoLibre.literal.fechaHasta','Fecha Hasta','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesTextoLibre.literal.fechaHasta','Fecha Hasta#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesTextoLibre.literal.fechaHasta','Fecha Hasta#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.busquedaSolicitudesTextoLibre.literal.fechaHasta','Fecha Hasta#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.idSolicitud','ID Solicitud','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.idSolicitud','ID Solicitud#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.idSolicitud','ID Solicitud#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.idSolicitud','ID Solicitud#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.nColegiado','Nº Colegiado','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.nColegiado','Nº Colegiado#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.nColegiado','Nº Colegiado#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.nColegiado','Nº Colegiado#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.Nombre','Nombre','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.Nombre','Nombre#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.Nombre','Nombre#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.Nombre','Nombre#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.fecha','Fecha','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.fecha','Fecha#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.fecha','Fecha#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.fecha','Fecha#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.descripcion','Motivo','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.descripcion','Motivo#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.descripcion','Motivo#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesModificacion.literal.descripcion','Motivo#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.tipoModificacion','Tipo Modificación','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.tipoModificacion','Tipo Modificación#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.tipoModificacion','Tipo Modificación#CA','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.tipoModificacion','Tipo Modificación#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.documentacionSolicitud.literal.tipoSolicitud','Tipo de Solicitud','0','1',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.documentacionSolicitud.literal.tipoSolicitud','Tipo de Solicitud#GL','0','4',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.documentacionSolicitud.literal.tipoSolicitud','Tipo de Solicitud#CA','0','2',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.documentacionSolicitud.literal.tipoSolicitud','Tipo de Solicitud#EU','0','3',to_date('16/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('consulta.nuevaSolicitudesModificacion.literal','Estado Actual','0','1',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('consulta.nuevaSolicitudesModificacion.literal','Estado Actual#GL','0','4',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('consulta.nuevaSolicitudesModificacion.literal','Estado Actual#CA','0','2',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('consulta.nuevaSolicitudesModificacion.literal','Estado Actual#EU','0','3',to_date('16/10/18','DD/MM/RR'),'0','19');



Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.descripcion','Descripción','0','1',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.descripcion','Descripción#GL','0','4',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.descripcion','Descripción#CA','0','2',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.descripcion','Descripción#EU','0','3',to_date('16/10/18','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.descripcionSolicitud','Descripción de solicitud','0','1',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.descripcionSolicitud','Descripción de solicitud#GL','0','4',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.descripcionSolicitud','Descripción de solicitud#CA','0','2',to_date('16/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.resultadosSolicitudesTextoLibre.literal.descripcionSolicitud','Descripción de solicitud#EU','0','3',to_date('16/10/18','DD/MM/RR'),'0','19');


ALTER TABLE CEN_TIPOSCVSUBTIPO1 ADD FECHA_BAJA DATE;
ALTER TABLE CEN_TIPOSCVSUBTIPO2 ADD FECHA_BAJA DATE;


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.tipoCurricular.descripcion.literal','Tipo Curricular','0','1',to_date('23/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.tipoCurricular.descripcion.literal','Tipo Curricular#GL','0','4',to_date('23/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.tipoCurricular.descripcion.literal','Tipo Curricular#CA','0','2',to_date('23/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.tipoCurricular.descripcion.literal','Tipo Curricular#EU','0','3',to_date('23/10/18','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.subtipoCurricular.descripcion.literal','Subtipo Curricular','0','1',to_date('23/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.subtipoCurricular.descripcion.literal','Subtipo Curricular#GL','0','4',to_date('23/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.subtipoCurricular.descripcion.literal','Subtipo Curricular#CA','0','2',to_date('23/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.subtipoCurricular.descripcion.literal','Subtipo Curricular#EU','0','3',to_date('23/10/18','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacion.busquedaAbonos.literal.fecha2','Fecha de emisión','0','1',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacion.busquedaAbonos.literal.fecha2','Fecha de emisión#GL','0','4',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacion.busquedaAbonos.literal.fecha2','Data d''emisió','0','2',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacion.busquedaAbonos.literal.fecha2','Fecha de emisión#EU','0','3',to_date('10/12/05','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.publicidad','Publicidad#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.publicidad','Publicitat','0','2',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.publicidad','Publicidad','0','1',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.publicidad','Publicidad#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.guiaJudicial','Guía Judicial#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.guiaJudicial','Guía Judicial','0','2',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.guiaJudicial','Guía Judicial','0','1',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.guiaJudicial','Guía Judicial#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','19');
