spool 4-FUNCION_PARAMETROS_GENERALES.log
prompt 4-FUNCION_PARAMETROS_GENERALES.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

create or replace function F_SIGA_GETPARAMETROGENERAL 
(
  p_modulo in gen_parametros.modulo%type 
, p_parametro in gen_parametros.parametro%type 
, p_idinstitucion in gen_parametros.idinstitucion%type 
) return varchar2 is

  v_valor gen_parametros.valor%type;

begin
  begin
    select gp.valor
      into v_valor
      from GEN_PARAMETROS GP
     where gp.idinstitucion = p_idinstitucion
       and gp.modulo = p_modulo
       and gp.parametro = p_parametro 
       AND gp.fecha_baja is null;
  
  exception
    when no_data_found then
      begin
        select gp.valor
          into v_valor
          from GEN_PARAMETROS GP
         where gp.idinstitucion = 0
           and gp.modulo = p_modulo
           and gp.parametro = p_parametro
           AND gp.fecha_baja is null;
      
      exception
      when no_data_found then
        begin
        select gp.valor
          into v_valor
          from GEN_PARAMETROS GP
         where gp.idinstitucion = 2000
           and gp.modulo = p_modulo
           and gp.parametro = p_parametro
           and gp.fecha_baja is null;
      
          exception
            when others then
              v_valor := '-1';
        end;
        when others then
          v_valor := '-1';
      end;
    
    when others then
      v_valor := '-1';
  end;

  return v_valor;    

end F_SIGA_GETPARAMETROGENERAL;
/


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
