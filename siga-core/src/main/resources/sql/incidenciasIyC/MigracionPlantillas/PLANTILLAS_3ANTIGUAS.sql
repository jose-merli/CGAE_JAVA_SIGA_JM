SET AUTOPRINT ON;
SET SERVEROUTPUT ON;

DECLARE

idPlantillaEnvio NUMBER(10):=0;
contador NUMBER(4) := 0;
v_errm  VARCHAR2(64);

    CURSOR plantillaEnvios IS
        SELECT plantilla.idtipoenvios, plantilla.idplantillaenvios, plantilla.nombre,plantilla.idinstitucion, plantilla.fechamodificacion, plantilla.usumodificacion, plantilla.acuserecibo,plantilla.fechabaja,campos.valor as valor,plantilla.iddireccion,plantilla.idpersona,plantilla.descripcion,plantilla.antigua,plantilla.descripcion_remitente
        FROM ENV_PLANTILLASENVIOS PLANTILLA
        JOIN ENV_CAMPOSPLANTILLA CAMPOS ON CAMPOS.IDPLANTILLAENVIOS = PLANTILLA.IDPLANTILLAENVIOS AND CAMPOS.IDINSTITUCION = PLANTILLA.IDINSTITUCION AND CAMPOS.IDTIPOENVIOS = PLANTILLA.IDTIPOENVIOS
        WHERE PLANTILLA.ANTIGUA = 'S' AND CAMPOS.IDCAMPO IN (1) AND CAMPOS.TIPOCAMPO = 'E'; 


BEGIN
    --DBMS_OUTPUT.ENABLE;
    DBMS_OUTPUT.ENABLE(1000000);
    DBMS_OUTPUT.PUT_LINE('Script informe --> modelo empieza: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    
     FOR plantilla IN plantillaEnvios
     LOOP        
        
        idPlantillaEnvio := SEQ_ENV_PLANTILLASENVIO.nextval;
        DBMS_OUTPUT.PUT_LINE('idplantilla' || plantilla.idplantillaenvios || ' ' || plantilla.idtipoenvios || ' ' || plantilla.idinstitucion);
        INSERT INTO env_plantillasenvios (idtipoenvios,idplantillaenvios,nombre,idinstitucion,fechamodificacion,usumodificacion,acuserecibo,fechabaja,asunto,cuerpo,iddireccion,idpersona,descripcion,antigua,descripcion_remitente) 
        VALUES (plantilla.idtipoenvios,idPlantillaEnvio,plantilla.nombre,plantilla.idinstitucion,sysdate,0,plantilla.acuserecibo,plantilla.fechabaja,plantilla.valor,(SELECT CAMPOS.VALOR AS VALOR
        FROM ENV_PLANTILLASENVIOS PLANTILLAENVIO
        JOIN ENV_CAMPOSPLANTILLA CAMPOS ON CAMPOS.IDPLANTILLAENVIOS = PLANTILLAENVIO.IDPLANTILLAENVIOS AND CAMPOS.IDINSTITUCION = PLANTILLAENVIO.IDINSTITUCION AND CAMPOS.IDTIPOENVIOS = PLANTILLAENVIO.IDTIPOENVIOS
        WHERE PLANTILLAENVIO.ANTIGUA = 'S' AND CAMPOS.IDCAMPO IN (2) AND PLANTILLAENVIO.idtipoenvios = plantilla.idtipoenvios AND PLANTILLAENVIO.idplantillaenvios = plantilla.idplantillaenvios AND PLANTILLAENVIO.idInstitucion = plantilla.idInstitucion and CAMPOS.valor IS NOT NULL)
        ,plantilla.iddireccion,plantilla.idpersona,plantilla.descripcion,'N',plantilla.descripcion_remitente);
        
        contador := contador +1;
    
     END LOOP;
     
    --COMMIT;
    DBMS_OUTPUT.PUT_LINE('Script informe --> modelo acaba: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    DBMS_OUTPUT.PUT_LINE(' Se han actualizado ' || contador || ' filas.');
    
    EXCEPTION
    WHEN OTHERS THEN  
    v_errm := SUBSTR(SQLERRM, 1, 64);
    DBMS_OUTPUT.PUT_LINE('Error' || '' || v_errm);
END;