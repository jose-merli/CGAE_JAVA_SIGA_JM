Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.fichaSociedad','Ficha Sociedad','0','1',to_date('25/03/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.fichaSociedad','Fitxa Societat','0','2',to_date('25/03/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.fichaSociedad','Ficha Sociedad#GL','0','4',to_date('25/03/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.censo.fichaSociedad','Ficha Sociedad#EU','0','3',to_date('25/03/19','DD/MM/RR'),'0','19');

ALTER TABLE FOR_TEMACURSO ADD  FECHA_BAJA DATE; 

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('mensaje.informacion.noAcceso.fichaColegial','El usuario registrado no es un colegiado.','0','1',to_date('25/03/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('mensaje.informacion.noAcceso.fichaColegial','L''usuari registrat no és un col·legiat.','0','2',to_date('25/03/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('mensaje.informacion.noAcceso.fichaColegial','El usuario registrado no es un colegiado.#GL','0','4',to_date('25/03/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('mensaje.informacion.noAcceso.fichaColegial','El usuario registrado no es un colegiado.#EU','0','3',to_date('25/03/19','DD/MM/RR'),'0','19');


commit;