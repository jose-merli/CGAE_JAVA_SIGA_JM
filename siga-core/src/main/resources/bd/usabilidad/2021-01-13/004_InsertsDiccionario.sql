spool  004_InsertsDiccionario.log
prompt 004_InsertsDiccionario
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt 

update GEN_DICCIONARIO set DESCRIPCION = 'Total:' where IDRECURSO = 'censo.datosDireccion.literal.numero.direccion' and IDLENGUAJE = '1';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#EU' where IDRECURSO = 'censo.datosDireccion.literal.numero.direccion' and IDLENGUAJE = '3';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#GL' where IDRECURSO = 'censo.datosDireccion.literal.numero.direccion' and IDLENGUAJE = '4';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:' where IDRECURSO = 'censo.datosDireccion.literal.numero.direccion' and IDLENGUAJE = '2';
 
  insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('1','13/01/21','censo.consultaComponentesJuridicos.literal.descripcioncertificados',0,0,'Descripción del certificado','19');
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('2','13/01/21','censo.consultaComponentesJuridicos.literal.descripcioncertificados',0,0,'Descripció de l''certificat','19');
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('3','13/01/21','censo.consultaComponentesJuridicos.literal.descripcioncertificados',0,0,'Descripción del certificado#EU','19');
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('4','13/01/21','censo.consultaComponentesJuridicos.literal.descripcioncertificados',0,0,'Descripción del certificado#GL','19');
 
  insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('1','13/01/21','censo.consultaComponentesJuridicos.literal.fechaemision',0,0,'Fecha de emisión','19');
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('2','13/01/21','censo.consultaComponentesJuridicos.literal.fechaemision',0,0,'Data d''emissió:','19');
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('3','13/01/21','censo.consultaComponentesJuridicos.literal.fechaemision',0,0,'Fecha de emisión#EU','19');
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('4','13/01/21','censo.consultaComponentesJuridicos.literal.fechaemision',0,0,'Fecha de emisión#GL','19');
 
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('1','13/01/21','censo.consultaComponentesJuridicos.literal.identificacionsociedad',0,0,'Identificación de la sociedad','19');
  insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('2','13/01/21','censo.consultaComponentesJuridicos.literal.identificacionsociedad',0,0,'Identificació de la societat','19');
  insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('3','13/01/21','censo.consultaComponentesJuridicos.literal.identificacionsociedad',0,0,'Identificación de la sociedad#EU','19');
  insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('4','13/01/21','censo.consultaComponentesJuridicos.literal.identificacionsociedad',0,0,'Identificación de la sociedad#GL','19');
 
  insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('1','13/01/21','censo.consultaComponentesJuridicos.literal.tiposociedad',0,0,'Tipo de sociedad','19');
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('2','13/01/21','censo.consultaComponentesJuridicos.literal.tiposociedad',0,0,'Tipus de societat','19');
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('3','13/01/21','censo.consultaComponentesJuridicos.literal.tiposociedad',0,0,'Tipo de sociedad#EU','19');
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('4','13/01/21','censo.consultaComponentesJuridicos.literal.tiposociedad',0,0,'Tipo de sociedad#GL','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off