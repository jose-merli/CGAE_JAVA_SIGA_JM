spool Fase III Modulo Busqueda Asuntos Desarrollo.log
prompt Fase III Modulo Busqueda Asuntos Desarrollo.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaApertura','Fecha Apertura#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaApertura','Fecha Apertura','0','1',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaApertura','Fecha Apertura#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaApertura','Data Obertura','0','2',to_date('10/03/04','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaAperturaDesde','Fecha Apertura Desde#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaAperturaDesde','Fecha Apertura Desde','0','1',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaAperturaDesde','Fecha Apertura Desde#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaAperturaDesde','Data Obertura Desde','0','2',to_date('10/03/04','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaAperturaHasta','Fecha Apertura Hasta#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaAperturaHasta','Fecha Apertura Hasta','0','1',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaAperturaHasta','Fecha Apertura Hasta#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaEJG.literal.fechaAperturaHasta','Data Obertura Hasta','0','2',to_date('10/03/04','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tiposDesigna','Tipos de Designación','0','1',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tiposDesigna','Tipus de Designació','0','2',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tiposDesigna','Tipos de Designación#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tiposDesigna','Tipos de Designación#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tipoDesigna','Tipo de Designación','0','1',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tipoDesigna','Tipu de Designació','0','2',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tipoDesigna','Tipo de Designación#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tipoDesigna','Tipo de Designación#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.estadoDesigna','Estado de Designación','0','1',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.estadoDesigna','Estat de Designació','0','2',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.estadoDesigna','Estado de Designación#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.estadoDesigna','Estado de Designación#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.numProcedimiento','Nº Procedimiento','0','1',to_date('11/04/05','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.numProcedimiento','Nº Procediment','0','2',to_date('11/04/05','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.numProcedimiento','Nº Procedimiento#EU','0','3',to_date('11/04/05','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.numProcedimiento','Nº Procedimiento#GL','0','4',to_date('11/04/05','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tipoSOJ','Tipo SOJ','0','1',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tipoSOJ','Tipu de SOJ','0','2',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tipoSOJ','Tipo SOJ#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.busquedaDesignas.literal.tipoSOJ','Tipo SOJ#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.tipoAsistencia','Tipo Asistencia#EU','0','3',to_date('20/03/18','DD/MM/RR'),'0',null);
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.tipoAsistencia','Tipu Assistèncie','0','2',to_date('20/03/18','DD/MM/RR'),'0',null);
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.tipoAsistencia','Tipo Asistencia','0','1',to_date('20/03/18','DD/MM/RR'),'0',null);
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.tipoAsistencia','Tipo Asistencia#GL','0','4',to_date('20/03/18','DD/MM/RR'),'0',null);

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantAsistencias.literal.numeroDiligenciasolo','Nº Diligencia','0','1',to_date('16/12/08','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantAsistencias.literal.numeroDiligenciasolo','Nº Diligencia#GL','0','4',to_date('16/12/08','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantAsistencias.literal.numeroDiligenciasolo','N. Diligència','0','2',to_date('16/12/08','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantAsistencias.literal.numeroDiligenciasolo','Nº Diligencia#EU','0','3',to_date('16/12/08','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantActuacion.literal.nasunto','Nº Asunto','0','1',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantActuacion.literal.nasunto','Núm. Assumpte','0','2',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantActuacion.literal.nasunto','Nº Asunto#EU','0','3',to_date('10/03/04','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.mantActuacion.literal.nasunto','Nº Asunto#GL','0','4',to_date('10/03/04','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.volantesExpres.literal.centroDetencion','Centro Detención','0','1',to_date('30/07/08','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.volantesExpres.literal.centroDetencion','Centre Detenció','0','2',to_date('30/07/08','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.volantesExpres.literal.centroDetencion','Centro Detención#EU','0','3',to_date('30/07/08','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('gratuita.volantesExpres.literal.centroDetencion','Centro Detención#GL','0','4',to_date('30/07/08','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
