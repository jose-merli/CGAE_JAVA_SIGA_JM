spool Fase III Modulo Busqueda Asuntos Desarrollo.log
prompt Fase III Modulo Busqueda Asuntos Desarrollo.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE from GEN_DICCIONARIO where IDRECURSO = 'gratuita.busquedaDesignas.literal.tiposDesigna';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'gratuita.busquedaDesignas.literal.tipoDesigna';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'gratuita.busquedaDesignas.literal.estadoDesigna';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'gratuita.busquedaDesignas.literal.numProcedimiento';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'gratuita.busquedaDesignas.literal.tipoSOJ';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'menu.justiciaGratuita.tipoAsistencia';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'gratuita.mantAsistencias.literal.numeroDiligenciasolo';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'gratuita.mantActuacion.literal.nasunto';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'gratuita.volantesExpres.literal.centroDetencion';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'gratuita.busquedaEJG.literal.fechaApertura';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'gratuita.busquedaEJG.literal.fechaAperturaDesde';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'gratuita.busquedaEJG.literal.fechaAperturaHasta';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
