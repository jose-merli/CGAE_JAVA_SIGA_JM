Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaHistorico.cabecera','Datos Auditoría','0','1',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaHistorico.cabecera','Dades Històric','0','2',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaHistorico.cabecera','Datos Auditoría#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaHistorico.cabecera','Datos Auditoría#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.datosHistorico.literal.motivo','Motivo','0','1',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.datosHistorico.literal.motivo','Motiu','0','2',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.datosHistorico.literal.motivo','Motivo#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.datosHistorico.literal.motivo','Motivo#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.censo.nifcifExiste2','Ya existe un registro con el mismo CIF','0','1',to_date('16/01/07','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.censo.nifcifExiste2','Ja existeix un registre amb el mateix CIF','0','2',to_date('16/01/07','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.censo.nifcifExiste2','Ya existe un registro con el mismo CIF#EU','0','3',to_date('16/01/07','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.censo.nifcifExiste2','Ya existe un registro con el mismo CIF#GL','0','4',to_date('16/01/07','DD/MM/RR'),'0','19');
update gen_menu set path = 'opcionMenu' where path = ' ' and idparent = ' ';
update gen_menu set path = 'opcionMenu' where idmenu in (select idparent from gen_menu) and path = ' ';

COMMIT;