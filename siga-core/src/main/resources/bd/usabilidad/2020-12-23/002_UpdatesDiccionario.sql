spool  002_UpdatesDiccionario.log
prompt 002_UpdatesDiccionario
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.regtel.literal.numero.directorios' AND idlenguaje = '1';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.regtel.literal.numero.directorios' AND idlenguaje = '2';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#EU' 
WHERE idrecurso = 'censo.regtel.literal.numero.directorios' AND idlenguaje = '3';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#GL' 
WHERE idrecurso = 'censo.regtel.literal.numero.directorios' AND idlenguaje = '4';

update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.consultaDatosColegiacion.literal.numSanciones' AND idlenguaje = '1';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.consultaDatosColegiacion.literal.numSanciones' AND idlenguaje = '2';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#EU' 
WHERE idrecurso = 'censo.consultaDatosColegiacion.literal.numSanciones' AND idlenguaje = '3';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#GL' 
WHERE idrecurso = 'censo.consultaDatosColegiacion.literal.numSanciones' AND idlenguaje = '4';

update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.certificados' AND idlenguaje = '1';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.certificados' AND idlenguaje = '2';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#EU' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.certificados' AND idlenguaje = '3';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#GL' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.certificados' AND idlenguaje = '4';

update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.curriculares' AND idlenguaje = '1';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.curriculares' AND idlenguaje = '2';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#EU' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.curriculares' AND idlenguaje = '3';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#GL' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.curriculares' AND idlenguaje = '4';

update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.sociedades' AND idlenguaje = '1';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.sociedades' AND idlenguaje = '2';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#EU' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.sociedades' AND idlenguaje = '3';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#GL' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.sociedades' AND idlenguaje = '4';

update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.integrantes' AND idlenguaje = '1';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.integrantes' AND idlenguaje = '2';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#EU' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.integrantes' AND idlenguaje = '3';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#GL' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.integrantes' AND idlenguaje = '4';

update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.cuentas' AND idlenguaje = '1';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.cuentas' AND idlenguaje = '2';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#EU' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.cuentas' AND idlenguaje = '3';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#GL' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.cuentas' AND idlenguaje = '4';

update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.cuentas' AND idlenguaje = '1';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.cuentas' AND idlenguaje = '2';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#EU' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.cuentas' AND idlenguaje = '3';
update GEN_DICCIONARIO set DESCRIPCION = 'Total:#GL' 
WHERE idrecurso = 'censo.datosDireccion.literal.numero.cuentas' AND idlenguaje = '4';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off