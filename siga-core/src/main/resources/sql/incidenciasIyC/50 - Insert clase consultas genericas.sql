INSERT INTO MOD_CLASECOMUNICACIONES (IDCLASECOMUNICACION, NOMBRE, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, IDMODULO, SUFIJO, RUTAPLANTILLA)
VALUES (5, 'Consultas genéricas', 'Consultas genéricas', sysdate, 1, null, null, 'consultas/%%IDINSTITUCION%%');

INSERT INTO MOD_CLASECOMUNICACION_RUTA (IDCLASECOMUNICACION_RUTA, IDCLASECOMUNICACION, RUTA, FECHAMODIFICACION, USUARIOMODIFICACION) values (7,5,'/fichaConsulta', sysdate, 0);
