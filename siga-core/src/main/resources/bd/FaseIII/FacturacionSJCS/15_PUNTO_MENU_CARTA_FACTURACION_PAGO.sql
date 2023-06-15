spool 14_PUNTO_MENU_CARTA_FACTURACION_PAGO.log
prompt 14_PUNTO_MENU_CARTA_FACTURACION_PAGO.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .
 
Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('620','JGR','1','Y',SYSDATE,'0','Cartas Facturación y Pago','JGR_Cartas Facturación Pago','004','10');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.cartaFacturacionPago','Carta Facturación y Pago','0','1',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.cartaFacturacionPago','Carta Facturació i Pagament','0','2',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.cartaFacturacionPago','Carta Facturación y Pago#EU','0','3',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.cartaFacturacionPago','Carta Facturación y Pago#GL','0','4',SYSDATE,'0','19');

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.cartaFacturacionPago','Carta Facturación y Pago','0','1',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.cartaFacturacionPago','Carta Facturació i Pagament','0','2',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.cartaFacturacionPago','Carta Facturación y Pago#EU','0','3',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.cartaFacturacionPago','Carta Facturación y Pago#GL','0','4',SYSDATE,'0','19');

Insert into GEN_MENU 
(IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,PATH,FECHA_BAJA,IDCLASS) values
('622','35002','160','605',SYSDATE,'0',null,'menu.facturacionSJCS.cartaFacturacionPago',null,'620','1','cartaFacturacionPago',null,null);

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
