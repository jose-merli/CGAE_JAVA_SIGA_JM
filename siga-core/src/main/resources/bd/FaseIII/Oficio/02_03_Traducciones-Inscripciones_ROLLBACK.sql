spool 03_Traducciones-Inscripciones_ROLLBACK.log
prompt 03_Traducciones-Inscripciones_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO ='justiciaGratuita.oficio.inscripciones.cambiarfecha';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO ='justiciaGratuita.oficio.inscripciones.mensajesolicitarbaja';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off