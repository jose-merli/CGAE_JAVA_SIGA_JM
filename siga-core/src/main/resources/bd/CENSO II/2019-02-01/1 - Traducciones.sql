Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('modificacionDatos.boton.procesarSolicitudes','Procesar Solicitud/es','0','1',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('modificacionDatos.boton.procesarSolicitudes','Processar Sol·licitud/s','0','2',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('modificacionDatos.boton.procesarSolicitudes','Procesar Solicitud/es#EU','0','3',to_date('15/10/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('modificacionDatos.boton.procesarSolicitudes','Procesar Solicitud/es#GL','0','4',to_date('15/10/18','DD/MM/RR'),'0','19');

UPDATE gen_diccionario SET descripcion = 'Denegar Solicitud' WHERE idrecurso = 'general.boton.denegarSolicitud' and idlenguaje = '1';
UPDATE gen_diccionario SET descripcion = 'Denegar Sol·licitud' WHERE idrecurso = 'general.boton.denegarSolicitud' and idlenguaje = '2';
UPDATE gen_diccionario SET descripcion = 'Denegar Solicitud#EU' WHERE idrecurso = 'general.boton.denegarSolicitud' and idlenguaje = '3';
UPDATE gen_diccionario SET descripcion = 'Denegar Solicitud#GL' WHERE idrecurso = 'general.boton.denegarSolicitud' and idlenguaje = '4';

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.denegarSolicitudes','Denegar Solicitud/es','0','1',to_date('13/04/18','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.denegarSolicitudes','Denegar Sol·licitud/s','0','2',to_date('13/04/18','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.denegarSolicitudes','Denegar Solicitud/es#EU','0','3',to_date('13/04/18','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.boton.denegarSolicitudes','Denegar Solicitud/es#GL','0','4',to_date('13/04/18','DD/MM/RR'),'0','15');
