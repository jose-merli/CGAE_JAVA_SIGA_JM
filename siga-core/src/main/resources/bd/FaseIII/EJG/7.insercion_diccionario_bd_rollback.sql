spool 5.insercion_diccionario_bd_rollback.log
prompt 5.insercion_diccionario_bd_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt . 

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.AddExpediente';

Delete from GEN_DICCIONARIO  where idrecurso = 'general.boton.AsociarDesignacion';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.InformeCalificacion';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.Fundamento';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.datos.FundamentoCalificacion';

Delete from GEN_DICCIONARIO  where idrecurso = 'messages.ReestablecerDictamen';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off 