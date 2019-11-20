spool TRADUCCIONES CALENDARIO LABORAL - FICHA EVENTO.log
prompt TRADUCCIONES CALENDARIO LABORAL - FICHA EVENTO.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('agenda.fichaEventos.datosGenerales.tipoFestivo','Tipo Festivo','0','1',to_date('22/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('agenda.fichaEventos.datosGenerales.tipoFestivo','Tipus Festiu','0','2',to_date('22/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('agenda.fichaEventos.datosGenerales.tipoFestivo','Tipo Festivo#GL','0','4',to_date('22/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('agenda.fichaEventos.datosGenerales.tipoFestivo','Tipo Festivo#EU','0','3',to_date('22/10/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.calendarioLaboralAgenda.anio','Año','0','1',to_date('22/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.calendarioLaboralAgenda.anio','Any','0','2',to_date('22/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.calendarioLaboralAgenda.anio','Año#GL','0','4',to_date('22/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.calendarioLaboralAgenda.anio','Año#EU','0','3',to_date('22/10/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.calendarioLaboralAgenda.fiestaLocalPartidoJudicial','Fiesta Local Partido Judicial','0','1',to_date('22/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.calendarioLaboralAgenda.fiestaLocalPartidoJudicial','Festa Local Partit Judicial','0','2',to_date('22/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.calendarioLaboralAgenda.fiestaLocalPartidoJudicial','Fiesta Local Partido Judicial#GL','0','4',to_date('22/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.calendarioLaboralAgenda.fiestaLocalPartidoJudicial','Fiesta Local Partido Judicial#EU','0','3',to_date('22/10/19','DD/MM/RR'),'0','19');


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
