spool  003_InsertsDiccionario.log
prompt 003_InsertsDiccionario
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt 

update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.colegiaciones' AND idlenguaje = '1';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.colegiaciones' AND idlenguaje = '2';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#EU' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.colegiaciones' AND idlenguaje = '3';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#GL' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.colegiaciones' AND idlenguaje = '4';


insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('1','11/01/21','general.message.camposObligatorios',0,0,'Debe rellenar los campos obligatorios','15');
 
 insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('2','11/01/21','general.message.camposObligatorios',0,0,'Ha d''omplir els camps obligatoris','15');
 
 insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('3','11/01/21','general.message.camposObligatorios',0,0,'Debe rellenar los campos obligatorios #EU','15');
 
 insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('4','11/01/21','general.message.camposObligatorios',0,0,'Debe rellenar los campos obligatorios #GL','15');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off