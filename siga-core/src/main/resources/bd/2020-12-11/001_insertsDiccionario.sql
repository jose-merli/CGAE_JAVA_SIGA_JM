insert into GEN_DICCIONARIO (IDRECURSO, DESCRIPCION, ERROR, IDLENGUAJE, FECHAMODIFICACION, USUMODIFICACION, IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.fechaSituacion', 'F. Incorporación', 0, '1', sysdate, 0, '19');
insert into GEN_DICCIONARIO (IDRECURSO, DESCRIPCION, ERROR, IDLENGUAJE, FECHAMODIFICACION, USUMODIFICACION, IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.fechaSituacion', 'F. Incorporación#CA', 0, '2', sysdate, 0, '19');
insert into GEN_DICCIONARIO (IDRECURSO, DESCRIPCION, ERROR, IDLENGUAJE, FECHAMODIFICACION, USUMODIFICACION, IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.fechaSituacion', 'F. Incorporación#EU', 0, '3', sysdate, 0, '19');
insert into GEN_DICCIONARIO (IDRECURSO, DESCRIPCION, ERROR, IDLENGUAJE, FECHAMODIFICACION, USUMODIFICACION, IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.fechaSituacion', 'F. Incorporación#GL', 0, '4', sysdate, 0, '19');

update GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='F. Situación' where idrecurso='censo.consultaDatosGenerales.literal.fechaSituacion' and idlenguaje='1';
update GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='F. Situación#CA' where idrecurso='censo.consultaDatosGenerales.literal.fechaSituacion' and idlenguaje='2';
update GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='F. Situación#EU' where idrecurso='censo.consultaDatosGenerales.literal.fechaSituacion' and idlenguaje='3';
update GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='F. Situación#GL' where idrecurso='censo.consultaDatosGenerales.literal.fechaSituacion' and idlenguaje='4';


update GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='Este colegiado está inscrito en turnos y/o guardias por lo que se dará de baja en todas las incripciones de forma automática. ¿Desea continuar? ' where idrecurso='message.fichaColegial.informarBajaInscripciones.cambioEstadoColegial' and idlenguaje='1';
update GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='Este colegiado está inscrito en turnos y/o guardias por lo que se dará de baja en todas las incripciones de forma automática. ¿Desea continuar? #CA' where idrecurso='message.fichaColegial.informarBajaInscripciones.cambioEstadoColegial' and idlenguaje='2';
update GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='Este colegiado está inscrito en turnos y/o guardias por lo que se dará de baja en todas las incripciones de forma automática. ¿Desea continuar? #EU' where idrecurso='message.fichaColegial.informarBajaInscripciones.cambioEstadoColegial' and idlenguaje='3';
update GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='Este colegiado está inscrito en turnos y/o guardias por lo que se dará de baja en todas las incripciones de forma automática. ¿Desea continuar? #GL' where idrecurso='message.fichaColegial.informarBajaInscripciones.cambioEstadoColegial' and idlenguaje='4';


commit;