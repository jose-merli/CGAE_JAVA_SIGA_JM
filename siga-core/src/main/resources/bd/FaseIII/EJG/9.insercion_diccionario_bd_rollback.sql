spool 9.insercion_diccionario_bd_rollback.log
prompt 9.insercion_diccionario_bd_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt . 

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.AutoResolutorio';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.SentidoAuto';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.fechaAuto';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.NumImpugnacion';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.FechaPublicacion';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.Bis';


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off 