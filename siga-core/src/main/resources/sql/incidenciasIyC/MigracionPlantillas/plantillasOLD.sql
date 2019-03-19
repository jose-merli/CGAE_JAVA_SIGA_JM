--DISTINGUIR PLANTILLAS ANTIGUAS DE NUEVAS
UPDATE ENV_PLANTILLASENVIOS SET ANTIGUA = 'S' where ANTIGUA IS NULL;
commit;
---------------------------------------------------------------------------------
------------------METER EL IDPERSONA LA PLANTILLAENVIO----------------------------
SET AUTOPRINT ON;
SET SERVEROUTPUT ON;

DECLARE

idinstitucion NUMBER(4,0);
idpersona NUMBER(10,0);
idtipoenvios NUMBER(2,0);
idplantillaenvios NUMBER(5,0);
contador NUMBER(10,0);

CURSOR cursorEnvios IS
    SELECT envio.idplantillaenvios, envio.idtipoenvios, envio.idinstitucion, remitente.idpersona
    from env_envios envio
    join env_plantillaremitentes remitente on remitente.idplantillaenvios = envio.idplantillaenvios and remitente.idtipoenvios = envio.idtipoenvios and remitente.idinstitucion = envio.idinstitucion;
        
BEGIN
    --DBMS_OUTPUT.ENABLE;
    DBMS_OUTPUT.ENABLE(1000000);
    DBMS_OUTPUT.PUT_LINE('Script idpersona empieza: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    
     FOR envio IN cursorEnvios
     LOOP
        UPDATE ENV_PLANTILLASENVIOS SET IDPERSONA = envio.idpersona WHERE IDTIPOENVIOS = envio.idtipoenvios AND IDPLANTILLAENVIOS = envio.idplantillaenvios AND IDINSTITUCION = envio.idinstitucion;
        contador := contador +1;
     END LOOP;
     
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Script idpersona acaba: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    DBMS_OUTPUT.PUT_LINE(' Se han actualizado ' || contador || ' filas.');
END;
---------------------------------------------------------------------------------

-----------------METER EL IDDIRECCION EN LA PLANTILLAENVIO-----------------------
SET AUTOPRINT ON;
SET SERVEROUTPUT ON;

DECLARE

idinstitucion NUMBER(4,0);
idpersona NUMBER(10,0);
idtipoenvios NUMBER(2,0);
idplantillaenvios NUMBER(5,0);
contador NUMBER(10):= 0;

CURSOR cursorDireccion is 
    SELECT REMITENTE.IDINSTITUCION, REMITENTE.IDTIPOENVIOS, REMITENTE.IDPLANTILLAENVIOS ,DIR.IDDIRECCION, REMITENTE.IDPERSONA
    FROM ENV_PLANTILLASENVIOS PLANTILLA
    JOIN ENV_PLANTILLAREMITENTES REMITENTE ON remitente.idtipoenvios = plantilla.idtipoenvios AND remitente.idplantillaenvios = PLANTILLA.idplantillaenvios 
    AND REMITENTE.IDINSTITUCION = plantilla.idinstitucion and remitente.idpersona = plantilla.idpersona 
    JOIN CEN_DIRECCIONES DIR ON DIR.IDPERSONA = REMITENTE.IDPERSONA AND DIR.DOMICILIO = REMITENTE.DOMICILIO AND REMITENTE.IDINSTITUCION = DIR.IDINSTITUCION;

BEGIN
    DBMS_OUTPUT.ENABLE(1000000);
    DBMS_OUTPUT.PUT_LINE('Script iddireccion empieza: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    
    FOR REMITENTE IN cursorDireccion
    LOOP
    UPDATE ENV_PLANTILLASENVIOS SET IDDIRECCION = REMITENTE.IDDIRECCION WHERE IDPLANTILLAENVIOS = REMITENTE.IDPLANTILLAENVIOS AND IDTIPOENVIOS = REMITENTE.IDTIPOENVIOS AND 
    IDINSTITUCION = REMITENTE.IDINSTITUCION AND IDPERSONA = REMITENTE.IDPERSONA;
    contador := contador +1;
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('Script iddireccion acaba: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    DBMS_OUTPUT.PUT_LINE(' Se han actualizado ' || contador || ' filas.');
END;

COMMIT;

      