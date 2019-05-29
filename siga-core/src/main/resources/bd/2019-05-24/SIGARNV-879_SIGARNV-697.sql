
ALTER TABLE env_enviosgrupocliente ADD IDINSTITUCION NUMBER(4,0);

ALTER TABLE env_enviosgrupocliente ADD IDINSTITUCION_GRUPO NUMBER(4,0);

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('message.informativo.busquedaInscripciones.calificacion','Sólo se pueden calificar las inscripciones aprobadas de cursos ya impartidos. Introduzca la calificación en el cuadro de texto de la columna Calificación','0','1',to_date('22/05/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('message.informativo.busquedaInscripciones.calificacion','Només es poden qualificar les inscripcions aprovades de cursos ja impartits. Introduïu la qualificació en el quadre de text de la columna Qualificació','0','2',to_date('22/05/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('message.informativo.busquedaInscripciones.calificacion','Sólo se pueden calificar las inscripciones aprobadas de cursos ya impartidos. Introduzca la calificación en el cuadro de texto de la columna Calificación#EU','0','3',to_date('22/05/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('message.informativo.busquedaInscripciones.calificacion','Sólo se pueden calificar las inscripciones aprobadas de cursos ya impartidos. Introduzca la calificación en el cuadro de texto de la columna Calificación#GL','0','4',to_date('22/05/19','DD/MM/RR'),'0','19');

commit;