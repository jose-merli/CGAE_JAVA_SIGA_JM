spool PUNTO MENU CARTA FACTURACION PAGO_ROLLBACK.log
prompt PUNTO MENU CARTA FACTURACION PAGO_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .
 
DELETE FROM GEN_PROCESOS WHERE IDPROCESO = '620';

DELETE FROM GEN_PROCESOS WHERE IDRECURSO like 'menu.facturacionSJCS.cartaFacturacionPago';

DELETE FROM GEN_RECURSOS WHERE IDRECURSO like 'menu.facturacionSJCS.cartaFacturacionPago';

DELETE FROM GEN_MENU WHERE IDMENU = '620';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
