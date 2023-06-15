spool TRADUCCIONES_LITERALES_CARTAS_FACTURACIONPAGO_ROLLBACK.log
prompt TRADUCCIONES_LITERALES_CARTAS_FACTURACIONPAGO_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO ='facturacionSJCS.facturacionesYPagos.literal.bruto';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO ='facturacionSJCS.facturacionesYPagos.literal.irpf';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO ='facturacionSJCS.facturacionesYPagos.literal.retenciones';
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO ='facturacionSJCS.facturacionesYPagos.cartaFacturacion';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off