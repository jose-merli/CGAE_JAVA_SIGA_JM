insert into adm_config (id, clave, valor, descripcion, valor_por_defecto, necesita_reinicio)
values (seq_adm_config.nextval, 'cron.pattern.scheduled.ecom.cola', '0 */2 * ? * *', 'Ejecución de la cola de ecom', '0 */2 * ? * *', 1);

falta configuración log4j
commit;