spool 1.insercion_diccionario_bd_rollback.log
prompt 1.insercion_diccionario_bd_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt . 

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.CAJG';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.annioCAJG';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.numCAJG';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.EJG';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.datosGeneralesEJG';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.tramtiadorAsistenciaDesignacion';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.datosDefensa';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.numAnnioProcedimiento';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.calidad';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.fundamentoJuridico';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.fundamentoImpugnacion';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.Impugnacion';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.Juzgado';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.Perceptivo;

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.Renuncia';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.AnnioActa';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.numActa';

Delete from GEN_DICCIONARIO  where idrecurso = 'justiciaGratuita.ejg.datosGenerales.Ponente';

Delete from GEN_DICCIONARIO where idrecurso = 'justiciaGratuita.ejg.datosGenerales.TipoEJG';

Delete from GEN_DICCIONARIO where idrecurso = 'justiciaGratuita.ejg.datosGenerales.TipoEJGColegio';

Delete from GEN_DICCIONARIO where idrecurso = 'justiciaGratuita.ejg.datosGenerales.CreadoDesde';

Delete from GEN_DICCIONARIO where idrecurso = 'justiciaGratuita.ejg.datosGenerales.EstadoEJG';

Delete from GEN_DICCIONARIO where idrecurso = 'justiciaGratuita.ejg.datosGenerales.TipoLetrado';

Delete from GEN_DICCIONARIO where idrecurso = 'justiciaGratuita.ejg.datosGenerales.Dictamen';


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off 