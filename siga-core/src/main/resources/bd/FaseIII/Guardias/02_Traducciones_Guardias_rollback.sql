spool 02_Traducciones_Guardias_rollback.log
prompt 02_Traducciones_Guardias_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'justiciaGratuita.guardia.gestion.guardiaCreadaDatosPred';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.letradosGuardia';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.grupo';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.fechaValidez';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.fechaBaja';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.baremos';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.ultimaFechaDesde';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.ultimaFechaHasta';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.generado';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.fichaProgramacion';
commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
