spool 5.insercion_diccionario_bd_rollback.log
prompt 5.insercion_diccionario_bd_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt . 

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.Asociar';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.ICA';

Delete from GEN_DICCIONARIO  where idrecurso = 'administracion.auditoriaUsuarios.literal.Automatico';

Delete from GEN_DICCIONARIO  where idrecurso = 'administracion.auditoriaUsuarios.literal.Propietario';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.numero.Documentos';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.documentacion.Presentador';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.documentacion.Documento';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.documentacion.RegistroEntrada';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.documentacion.RegistroSalida';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.documentacion.Propietario';

Delete from GEN_DICCIONARIO  where idrecurso = 'general.boton.volver.solicitudEejg';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off 