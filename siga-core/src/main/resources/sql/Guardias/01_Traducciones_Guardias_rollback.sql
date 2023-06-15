spool 01_Traducciones_Guardias_rollback.log
prompt 01_Traducciones_Guardias_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'justiciaGratuita.guardia.gestion.rotarComponentesAuto'

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'justiciaGratuita.guardia.gestion.porGrupos'

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'justiciaGratuita.guardia.gestion.configuracionCola'

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
