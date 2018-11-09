spool 5-UPDATE_RECURSO_PARAMETROS_GENERALES.log
prompt 5-UPDATE_RECURSO_PARAMETROS_GENERALES.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

update gen_parametros set idrecurso = 'administracion.parametro.path_docushare_expedientes' where parametro = 'PATH_DOCUSHARE_EXPEDIENTES' ;
update gen_parametros set idrecurso = 'administracion.parametro.etiquetas_alto' where parametro = 'ETIQUETAS_ALTO' ;
update gen_parametros set idrecurso = 'administracion.parametro.etiquetas_margen_coordenadax' where parametro = 'ETIQUETAS_MARGEN_COORDENADAX' ;
update gen_parametros set idrecurso = 'administracion.parametro.docushare_port' where parametro = 'DOCUSHARE_PORT' ;
update gen_parametros set idrecurso = 'administracion.parametro.id_docushare_expedientes' where parametro = 'ID_DOCUSHARE_EJG' ;
update gen_parametros set idrecurso = 'administracion.parametro.path_docushare_censo' where parametro = 'PATH_DOCUSHARE_CENSO' ;
update gen_parametros set idrecurso = 'administracion.parametro.path_docushare_ejg' where parametro = 'PATH_DOCUSHARE_EJG' ;
update gen_parametros set idrecurso = 'administracion.parametro.contab_mov_varios_negativos' where parametro = 'CONTAB_MOV_VARIOS_NEGATIVOS' ;
update gen_parametros set idrecurso = 'administracion.parametro.id_docushare_ejg' where parametro = 'ID_DOCUSHARE_EJG' ;
update gen_parametros set idrecurso = 'administracion.parametro.id_docushare_expedientes' where parametro = 'ID_DOCUSHARE_EXPEDIENTES' ;
update gen_parametros set idrecurso = 'administracion.parametro.contabilidad_caja' where parametro = 'CONTABILIDAD_CAJA' ;
update gen_parametros set idrecurso = 'administracion.parametro.url_ftp_descarga_envios_ordinarios' where parametro = 'URL_FTP_DESCARGA_ENVIOS_ORDINARIOS' ;
update gen_parametros set idrecurso = 'scs.parametro.pcajg.usuarioFtp' where parametro = 'PCAJG_FTP_USER' ;
update gen_parametros set idrecurso = 'administracion.parametro.modificar_descripcion' where parametro = 'MODIFICAR_DESCRIPCION' ;
update gen_parametros set idrecurso = 'administracion.parametro.id_docushare_ejg' where parametro = 'ID_DOCUSHARE_CENSO' ;
update gen_parametros set idrecurso = 'administracion.parametro.tipo_designacion_desde_asistencia' where parametro = 'TIPO_DESIGNACION_DESDE_ASISTENCIA' ;
update gen_parametros set idrecurso = 'administracion.parametro.bloqueoEnvios' where parametro = 'BLOQUEO_ENVIOS' ;
update gen_parametros set idrecurso = 'scs.parametro.pcajg.directorioOutFtp' where parametro = 'PCAJG_FTP_DIRECTORIO_OUT' ;
update gen_parametros set idrecurso = 'administracion.parametro.contab_servicios_profesionales' where parametro = 'CONTAB_SERVICIOS_PROFESIONALES' ;
update gen_parametros set idrecurso = 'administracion.parametro.contabilidad_tarjetas' where parametro = 'CONTABILIDAD_TARJETAS' ;
update gen_parametros set idrecurso = 'administracion.parametro.contab_gastos_bancarios' where parametro = 'CONTAB_GASTOS_BANCARIOS' ;
update gen_parametros set idrecurso = 'scs.parametro.pcajg.ipFtp' where parametro = 'PCAJG_FTP_IP' ;
update gen_parametros set idrecurso = 'scs.parametro.pcajg.puertoFtp' where parametro = 'PCAJG_FTP_PUERTO' ;
update gen_parametros set idrecurso = 'gratuita.parametro.validar_volante' where parametro = 'VALIDAR_VOLANTE' ;
update gen_parametros set idrecurso = 'scs.parametro.pcajg.passFtp' where parametro = 'PCAJG_FTP_PASS' ;
update gen_parametros set idrecurso = 'scs.parametro.pcajg.directorioInFtp' where parametro = 'PCAJG_FTP_DIRECTORIO_IN' ;
update gen_parametros set idrecurso = 'gratuita.parametro.activar_restricciones_acreditacion' where parametro = 'ACTIVAR_RESTRICCIONES_ACREDITACION' ;
update gen_parametros set idrecurso = 'administracion.parametro.docushare_password' where parametro = 'DOCUSHARE_PASSWORD' ;
update gen_parametros set idrecurso = 'administracion.parametro.contab_retencionesjudiciales' where parametro = 'CONTAB_RETENCIONESJUDICIALES' ;
update gen_parametros set idrecurso = 'administracion.parametro.etiquetas_ancho' where parametro = 'ETIQUETAS_ANCHO' ;
update gen_parametros set idrecurso = 'administracion.parametro.etiquetas_distancia_fila' where parametro = 'ETIQUETAS_DISTANCIA_FILA' ;
update gen_parametros set idrecurso = 'administracion.parametro.contab_movimientos_varios' where parametro = 'CONTAB_MOVIMIENTOS_VARIOS' ;
update gen_parametros set idrecurso = 'administracion.parametro.contabilidad_iva' where parametro = 'CONTABILIDAD_IVA' ;
update gen_parametros set idrecurso = 'administracion.parametro.etiquetas_margen_coordenaday' where parametro = 'ETIQUETAS_MARGEN_COORDENADAY' ;
update gen_parametros set idrecurso = 'administracion.parametro.modificar_importe_unitario' where parametro = 'MODIFICAR_IMPORTE_UNITARIO' ;
update gen_parametros set idrecurso = 'administracion.parametro.path_inicio_sesion' where parametro = 'PATH_INICIO_SESION' ;
update gen_parametros set idrecurso = 'administracion.parametro.docushare_user' where parametro = 'DOCUSHARE_USER' ;
update gen_parametros set idrecurso = 'administracion.parametro.contabilidad_ventas' where parametro = 'CONTABILIDAD_VENTAS' ;
update gen_parametros set idrecurso = 'administracion.parametro.etiquetas_distancia_columna' where parametro = 'ETIQUETAS_DISTANCIA_COLUMNA' ;
update gen_parametros set idrecurso = 'administracion.parametro.deducir_cobros_automatico' where parametro = 'DEDUCIR_COBROS_AUTOMATICO' ;
update gen_parametros set idrecurso = 'administracion.parametro.num_asistencias' where parametro = 'NUM_ASISTENCIAS' ;
update gen_parametros set idrecurso = 'administracion.parametro.id_docushare_censo' where parametro = 'ID_DOCUSHARE_EXPEDIENTES' ;
update gen_parametros set idrecurso = 'administracion.parametro.id_docushare_censo' where parametro = 'ID_DOCUSHARE_CENSO' ;
update gen_parametros set idrecurso = 'administracion.parametro.contab_irpf' where parametro = 'CONTAB_IRPF' ;
update gen_parametros set idrecurso = 'scs.parametro.pcajg.xunta.codigoColegio' where parametro = 'PCAJG_JE_CODIGO_COLEGIO' ;
update gen_parametros set idrecurso = 'administracion.parametro.Exportarcolegiadosacogidos_LOPD' where parametro = 'EXPORTAR_COLEGIADOS_ACOGIDOS_A_LOPD';
update gen_parametros set idrecurso = 'administracion.parametro.prefijo_numreg' where parametro = 'PREFIJO_NUMREG';
update gen_parametros set idrecurso = 'scs.parametro.censows.activarCliente' where parametro =  'CENSO_WS_ACTIVAR_CLIENTE';
update gen_parametros set idrecurso = 'APELLIDO2_NULO' where parametro = 'RULE_APELLIDOS_DNI';
update gen_parametros set idrecurso = 'administracion.parametro.contador_unico_ncolegiado_ncomunit'  where parametro = 'CONTADOR_UNICO_NCOLEGIADO_NCOMUNIT';
update gen_parametros set idrecurso = 'administracion.parametro.mens_solicitud' where parametro = 'MENS_SOLICITUD';
update gen_parametros set idrecurso = 'administracion.parametro.ocultar_motivo_modificacion' where parametro = 'OCULTAR_MOTIVO_MODIFICACION';
update gen_parametros set idrecurso = 'cen.parametro.censows.consulta.fechadesde' where parametro =  'CENSO_WS_FECHA_DESDE';
update gen_parametros set idrecurso = 'administracion.parametro.sufijo_numreg' where parametro = 'SUFIJO_NUMREG' ;
update gen_parametros set idrecurso = 'administracion.parametro.contador_numreg' where parametro = 'CONTADOR_NUMREG' ;

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
