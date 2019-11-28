
spool TRADUCCIONES FUNDAMENTOS RESOLUCION MENSAJES BACK.log
prompt TRADUCCIONES FUNDAMENTOS RESOLUCION MENSAJES BACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeFundamentosResolucionMismaDescripcion','Ya existe un fundamento de resolución con la misma descripción','0','1',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeFundamentosResolucionMismaDescripcion','Ja hi ha un fonament de resolució amb la mateixa descripció','0','2',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeFundamentosResolucionMismaDescripcion','Ya existe un fundamento de resolución con la misma descripción#GL','0','4',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionFundamentosResolucion.existeFundamentosResolucionMismaDescripcion','Ya existe un fundamento de resolución con la misma descripción#EU','0','3',to_date('20/09/19','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
