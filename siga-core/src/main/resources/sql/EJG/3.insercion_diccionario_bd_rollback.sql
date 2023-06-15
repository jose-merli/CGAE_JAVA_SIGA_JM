spool 3.insercion_diccionario_bd_rollback.log
prompt 3.insercion_diccionario_bd_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt . 

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.Prestaciones';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.FechaLimPresentacion';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.DatosIdenEJG';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.OtrosDatosEJG';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.DatosExpedienteIns';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.NumExpediente';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.ServiciosTramit';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off 