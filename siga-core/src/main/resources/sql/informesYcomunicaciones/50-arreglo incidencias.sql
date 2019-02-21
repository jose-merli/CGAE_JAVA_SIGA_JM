Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.location', 'Location del sello de la firma de la PFD', 0, 1, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.location', 'Location del sello de la firma de la PFD#CA', 0, 2, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.location', 'Location del sello de la firma de la PFD#EU', 0, 3, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.location', 'Location del sello de la firma de la PFD#GL', 0, 4, sysdate, 0, 0);

INSERT INTO GEN_PARAMETROS (modulo, parametro, valor, fechamodificacion, usumodificacion, idinstitucion, idrecurso, fecha_baja) 
values('GEN', 'PFD_FIRMA_LOCATION', 'Consejo General de la Abogacía Española', sysdate, 0, '0', 'administracion.parametro.pfd.firma.location', null);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.razon', 'Razón del sello de la firma de la PFD', 0, 1, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.razon', 'Razón del sello de la firma de la PFD#CA', 0, 2, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.razon', 'Razón del sello de la firma de la PFD#EU', 0, 3, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.razon', 'Razón del sello de la firma de la PFD#GL', 0, 4, sysdate, 0, 0);

INSERT INTO GEN_PARAMETROS (modulo, parametro, valor, fechamodificacion, usumodificacion, idinstitucion, idrecurso, fecha_baja) 
values('GEN', 'PFD_FIRMA_RAZON', 'Documento firmado', sysdate, 0, '0', 'administracion.parametro.pfd.firma.razon', null);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.razon', 'Indica si la firma es visible en el documento firmado', 0, 1, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.visible', 'Indica si la firma es visible en el documento firmado#CA', 0, 2, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.visible', 'Indica si la firma es visible en el documento firmado#EU', 0, 3, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.firma.visible', 'Indica si la firma es visible en el documento firmado#GL', 0, 4, sysdate, 0, 0);

INSERT INTO GEN_PARAMETROS (modulo, parametro, valor, fechamodificacion, usumodificacion, idinstitucion, idrecurso, fecha_baja) 
values('GEN', 'PFD_FIRMA_VISIBLE', '1', sysdate, 0, '0', 'administracion.parametro.pfd.firma.visible', null);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.idCliente', 'Identificador cliente de la pfd', 0, 1, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.idCliente', 'Identificador cliente de la pfd#CA', 0, 2, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.idCliente', 'Identificador cliente de la pfd#EU', 0, 3, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.idCliente', 'Identificador cliente de la pfd#GL', 0, 4, sysdate, 0, 0);

INSERT INTO GEN_PARAMETROS (modulo, parametro, valor, fechamodificacion, usumodificacion, idinstitucion, idrecurso, fecha_baja) 
values('GEN', 'PFD_IDCLIENTE', '1001', sysdate, 0, '0', 'administracion.parametro.pfd.idCliente', null);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.url', 'Url del servicio de la pfd', 0, 1, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.url', 'Url del servicio de la pfd#CA', 0, 2, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.url', 'Url del servicio de la pfd#EU', 0, 3, sysdate, 0, 0);

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD)
values ('administracion.parametro.pfd.url', 'Url del servicio de la pfd#GL', 0, 4, sysdate, 0, 0);

INSERT INTO GEN_PARAMETROS (modulo, parametro, valor, fechamodificacion, usumodificacion, idinstitucion, idrecurso, fecha_baja) 
values('GEN', 'PFD_URLWS', 'http://10.60.3.117:7019/plataformafirma2/PFDService', sysdate, 0, '0', 'administracion.parametro.pfd.url', null);





insert into mod_plantilladoc_formato (idformatosalida, nombre, fechamodificacion, usumodificacion)
values (3, null, sysdate, 0);

insert into mod_plantilladoc_formato (idformatosalida, nombre, fechamodificacion, usumodificacion)
values (4, null, sysdate, 0);


ALTER TABLE ENV_CONSULTASENVIO ADD SUFIJO VARCHAR(200);

ALTER TABLE MOD_CLASECOMUNICACION_RUTA DROP COLUMN SUFIJO;

INSERT INTO MOD_CLASECOMUNICACION_RUTA (IDCLASECOMUNICACION_RUTA, IDCLASECOMUNICACION, RUTA, FECHAMODIFICACION, USUARIOMODIFICACION) values (1,1,'/busquedaColegiados', sysdate, 0);

INSERT INTO MOD_CLASECOMUNICACION_RUTA (IDCLASECOMUNICACION_RUTA, IDCLASECOMUNICACION, RUTA, FECHAMODIFICACION, USUARIOMODIFICACION) values (2,1,'/busquedaNoColegiados', sysdate, 0);

INSERT INTO MOD_CLASECOMUNICACION_RUTA (IDCLASECOMUNICACION_RUTA, IDCLASECOMUNICACION, RUTA, FECHAMODIFICACION, USUARIOMODIFICACION) values (3,1,'/busquedaLetrados', sysdate, 0);

INSERT INTO MOD_CLASECOMUNICACION_RUTA (IDCLASECOMUNICACION_RUTA, IDCLASECOMUNICACION, RUTA, FECHAMODIFICACION, USUARIOMODIFICACION) values (4,2,'/busquedaSanciones', sysdate, 0);

INSERT INTO MOD_CLASECOMUNICACION_RUTA (IDCLASECOMUNICACION_RUTA, IDCLASECOMUNICACION, RUTA, FECHAMODIFICACION, USUARIOMODIFICACION) values (5,3,'/consultarDatosBancariosOrden', sysdate, 0);

INSERT INTO MOD_CLASECOMUNICACION_RUTA (IDCLASECOMUNICACION_RUTA, IDCLASECOMUNICACION, RUTA, FECHAMODIFICACION, USUARIOMODIFICACION) values (5,4,'/consultarDatosBancariosAnexo', sysdate, 0);



ALTER TABLE MOD_CLASECOMUNICACIONES ADD SUFIJO VARCHAR(400);

UPDATE MOD_CLASECOMUNICACIONES SET SUFIJO = 'IDINSTITUCION,IDPERSONA' WHERE IDCLASECOMUNICACION = 1;
UPDATE MOD_CLASECOMUNICACIONES SET SUFIJO = 'IDINSTITUCION,IDPERSONA,IDSANCION' WHERE IDCLASECOMUNICACION = 2;
UPDATE MOD_CLASECOMUNICACIONES SET SUFIJO = 'IDINSTITUCION,IDPERSONA,IDCUENTA,IDMANDATO' WHERE IDCLASECOMUNICACION = 3;
UPDATE MOD_CLASECOMUNICACIONES SET SUFIJO = 'IDINSTITUCION,IDPERSONAIDCUENTA,IDMANTADO,IDANEXO' WHERE IDCLASECOMUNICACION = 4;


alter table env_plantillasenvios 
add descripcion_remitente varchar2 (400) null;

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dialogo.boton.descargar','Descargar documentación','0','1',to_date('21/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dialogo.boton.descargar','Descarregar documentació','0','2',to_date('21/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dialogo.boton.descargar','Descargar documentación#EU','0','3',to_date('21/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dialogo.boton.descargar','Descargar documentación#GL','0','4',to_date('21/02/19','DD/MM/RR'),'0','19');

