spool 10-TRADUCCION_PARAMETROS_GENERALES.log
prompt 10-TRADUCCION_PARAMETROS_GENERALES.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.warn','Advertencia','0','1',to_date('19/04/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.warn','Advertència','0','2',to_date('19/04/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.warn','Advertencia#EU','0','3',to_date('19/04/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('general.message.warn','Advertencia#GL','0','4',to_date('19/04/18','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.parametrosGenerales.literal.faltaValor','Es necesario introducir el valor del parámetro','0','1',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.parametrosGenerales.literal.faltaValor','És necessari introduir el valor del paràmetre','0','2',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.parametrosGenerales.literal.faltaValor','Es necesario introducir el valor del parámetro#GL','0','4',to_date('10/12/05','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('administracion.parametrosGenerales.literal.faltaValor','Es necesario introducir el valor del parámetro#EU','0','3',to_date('10/12/05','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
