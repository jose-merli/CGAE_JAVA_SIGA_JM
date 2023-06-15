spool 01_REVERT_Creacion_menu_FacturacionesYPagos.log
prompt 01_REVERT_Creacion_menu_FacturacionesYPagos.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--DEJAMOS EL RECURSO DEL MENU ACTUALIZADO COMO ESTABA ORIGINALMENTE
UPDATE GEN_MENU SET IDRECURSO='menu.facturacionSJCS.mantenimientoFacturacion', PATH='mantenimientoFacturacion' WHERE IDMENU='600';

--CREAMOS EL MENU DE PREVISIONES BORRADO ANTERIORMENTE
Insert into GEN_MENU (IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,IDCLASS,FECHA_BAJA,PATH) values ('616','21550','160','605',to_date('18/04/05','DD/MM/RR'),'0',null,'menu.facturacionSJCS.previsiones',null,'616','1',null,null,'previsiones');

--BORRAMOS LOS RECURSOS CREADOS
DELETE FROM GEN_RECURSOS WHERE IDRECURSO='menu.facturacionSJCS.facturacionesYPagos';

--BORRAMOS LOS DICCIONARIOS CREADOS
--BORRAMOS LOS RECURSOS CREADOS
DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO='menu.facturacionSJCS.facturacionesYPagos';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
