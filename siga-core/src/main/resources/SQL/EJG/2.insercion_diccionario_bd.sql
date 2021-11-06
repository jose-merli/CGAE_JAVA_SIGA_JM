spool 2.insercion_diccionario_bd.log
prompt 2.insercion_diccionario_bd.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt . 

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.NIG','NIG','0','1',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.NIG','NIG','0','2',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.NIG','NIG#GL','0','4',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.NIG','NIG#EU','0','3',to_date('10/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.DescargarEEJ','Descargar EEJ','0','1',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.DescargarEEJ','Descargar EEJ','0','2',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.DescargarEEJ','Descargar EEJ#GL','0','4',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.DescargarEEJ','Descargar EEJ#EU','0','3',to_date('10/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaEstadoDesd','Fecha Estado desde','0','1',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaEstadoDesd','Data estat des','0','2',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaEstadoDesd','Fecha Estado desde#GL','0','4',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaEstadoDesd','Fecha Estado desde#EU','0','3',to_date('11/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaEstadoHast','Fecha Estado hasta','0','1',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaEstadoHast','Data estat fins','0','2',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaEstadoHast','Fecha Estado hasta#GL','0','4',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaEstadoHast','Fecha Estado hasta#EU','0','3',to_date('11/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaLimiteDesd','Fecha Límite desde','0','1',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaLimiteDesd','Data límit des','0','2',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaLimiteDesd','Fecha Límite desde#GL','0','4',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaLimiteDesd','Fecha Límite desde#EU','0','3',to_date('11/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaLimiteHast','Fecha Límite hasta','0','1',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaLimiteHast','Data límit fins','0','2',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaLimiteHast','Fecha Límite hasta#GL','0','4',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaLimiteHast','Fecha Límite hasta#EU','0','3',to_date('11/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaDictamenDesd','Fecha Dictamen desde','0','1',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaDictamenDesd','Data Dictamen des','0','2',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaDictamenDesd','Fecha Dictamen desde#GL','0','4',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaDictamenDesd','Fecha Dictamen desde#EU','0','3',to_date('11/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaDictamenHast','Fecha Dictamen hasta','0','1',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaDictamenHast','Data Dictamen fins','0','2',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaDictamenHast','Fecha Dictamen hasta#GL','0','4',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaDictamenHast','Fecha Dictamen hasta#EU','0','3',to_date('11/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaImpugnacionDesd','Fecha Impugnación desde','0','1',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaImpugnacionDesd','Data Impugnació des','0','2',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaImpugnacionDesd','Fecha Impugnación desde#GL','0','4',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaImpugnacionDesd','Fecha Impugnación desde#EU','0','3',to_date('11/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaImpugnacionHast','Fecha Impugnación hasta','0','1',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaImpugnacionHast','Data Impugnació fins','0','2',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaImpugnacionHast','Fecha Impugnación hasta#GL','0','4',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaImpugnacionHast','Fecha Impugnación hasta#EU','0','3',to_date('11/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaPonenteDesd','Fecha Ponente desde','0','1',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaPonenteDesd','Data Ponent des','0','2',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaPonenteDesd','Fecha Ponente desde#GL','0','4',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaPonenteDesd','Fecha Ponente desde#EU','0','3',to_date('11/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaPonenteHast','Fecha Ponente hasta','0','1',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaPonenteHast','Data Ponent fins','0','2',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaPonenteHast','Fecha Ponente hasta#GL','0','4',to_date('11/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaPonenteHast','Fecha Ponente hasta#EU','0','3',to_date('11/12/19','DD/MM/RR'),'0','19');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off 