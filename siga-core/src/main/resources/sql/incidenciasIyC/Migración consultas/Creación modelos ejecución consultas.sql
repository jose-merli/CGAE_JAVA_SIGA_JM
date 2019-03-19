------------------MIGRAR INFORMES A MODELOS DE COMUNICACION----------------------------
SET AUTOPRINT ON;
SET SERVEROUTPUT ON;

DECLARE

idModelo NUMBER(4):=0;
idPlantillaDocumento NUMBER(4):=0;
idPlantillaConsulta NUMBER(10):=0;

CURSOR consultas IS 
    SELECT * FROM CON_CONSULTA CONSULTA;

BEGIN
    --DBMS_OUTPUT.ENABLE;
    DBMS_OUTPUT.ENABLE(1000000);
    DBMS_OUTPUT.PUT_LINE('Script informe --> modelo empieza: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    
     FOR consulta IN consultas
     LOOP
        idModelo:= SEQ_MOD_MODELOCOMUNICACION.nextval;
        INSERT INTO mod_modelocomunicacion (idmodelocomunicacion,nombre,orden,idinstitucion,descripcion,preseleccionar,idclasecomunicacion,fechabaja,fechamodificacion,usumodificacion,visible,pordefecto,idplantillaenvios,idtipoenvios) 
        VALUES (idModelo,consulta.descripcion,1,consulta.idinstitucion,consulta.descripcion,'SI',5,consulta.fechabaja,sysdate,0,1,'NO',null,null);
        
        idPlantillaDocumento := SEQ_MOD_PLANTILLADOCUMENTO.nextval;
        INSERT INTO mod_plantilladocumento (plantilla,idioma,idplantilladocumento,fechamodificacion,usumodificacion) 
        VALUES ('ResultadoConsulta',1,idPlantillaDocumento,sysdate,0);
        
        INSERT INTO mod_modelo_plantilladocumento (usumodificacion,fechamodificacion,fechaasociacion,nombreficherosalida,idmodelocomunicacion,formatosalida,idplantilladocumento,idinforme,fechabaja,generacionexcel) 
        VALUES (0,sysdate,sysdate,'ResultadoConsulta',idModelo,1,idPlantillaDocumento,1,null,1);
        
        INSERT INTO mod_plantilladoc_consulta (fechamodificacion,usumodificacion,idconsulta,idplantilladocumento,idmodelocomunicacion,idinstitucion,fechabaja,idplantillaconsulta,region,idinstitucion_consulta) 
        VALUES (sysdate,0,consulta.idconsulta,idPlantillaDocumento,idModelo,consulta.idinstitucion,null,idplantillaconsulta,null,consulta.idinstitucion);
        
        INSERT INTO mod_modelo_perfiles (fechamodificacion,usumodificacion,idinstitucion,idperfil,idmodelocomunicacion) 
        SELECT sysdate,0,consulta.idinstitucion,perfil.idperfil,idModelo FROM ADM_PERFIL perfil WHERE perfil.idinstitucion = consulta.idinstitucion;      
        
        contador := contador +1;
    
     END LOOP;
     
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Script informe --> modelo acaba: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    DBMS_OUTPUT.PUT_LINE(' Se han actualizado ' || contador || ' filas.');
END;

