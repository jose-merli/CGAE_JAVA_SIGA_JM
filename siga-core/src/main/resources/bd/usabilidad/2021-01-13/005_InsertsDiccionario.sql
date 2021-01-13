spool  005_InsertsDiccionario.log
prompt 005_InsertsDiccionario
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt 

  
  insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('1','13/01/21','justiciaGratuita.oficio.turnos.inforesumen',0,0,'Información Resumen','19');
  insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('2','13/01/21','justiciaGratuita.oficio.turnos.inforesumen',0,0,'Informació Resum','19');
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('3','13/01/21','justiciaGratuita.oficio.turnos.inforesumen',0,0,'Información Resumen#GL','19');
   insert into GEN_DICCIONARIO(IDLENGUAJE,FECHAMODIFICACION,IDRECURSO,USUMODIFICACION,ERROR,DESCRIPCION,IDPROPIEDAD)
 values ('4','13/01/21','justiciaGratuita.oficio.turnos.inforesumen',0,0,'Información Resumen#EU','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off