package org.itcgae.siga.db.services.scs.providers;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.PermutaDTO;
import org.itcgae.siga.DTOs.scs.PermutaItem;
import org.itcgae.siga.db.mappers.ScsPermutaguardiasSqlProvider;

public class ScsPermutaguardiasSqlExtendsProvider extends ScsPermutaguardiasSqlProvider{
	
	public String getPermutaColeg(PermutaItem permutaItem, Short idInstitucion) {
		
		String sql;
		SQL sqlSol = new SQL();
		SQL sqlCon = new SQL();
		
		
		
		sqlSol.SELECT(" PC.IDINSTITUCION,"
				+ "pg.fechaconfirmacion,"
				+ "    pg.fechasolicitud,"
				+ "    pc.IDTURNO,"
				+ "    TURNO.NOMBRE turnopermuta,"
				+ "    GUARDIA.NOMBRE guardiadestino,"
				+ "    pg.IDGUARDIA_CONFIRMADOR,"
				+ "    'Motivos Solicitante: '"
				+ "     || pg.motivossolicitante"
				+ "     || ' / '"
				+ "     || 'Motivos Confirmador: '"
				+ "     || pg.motivosconfirmador motivos,"
				+ "     pc.IDPERSONA,"
				+ "     (CASE WHEN per.nombre is  NULL THEN '' ELSE per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre END) as NOMBRE");
		
		sqlSol.FROM("scs_permuta_cabecera pc");
		sqlSol.JOIN("scs_permutaguardias pg ON"
				+ "        pg.idinstitucion = pc.idinstitucion"
				+ "    AND pg.idpersona_solicitante = pc.idpersona"
				+ "     AND pg.idguardia_solicitante = pc.idguardia"
				);
		
		sqlSol.JOIN("cen_persona per ON per.idpersona = pc.idpersona");
		sqlSol.JOIN("scs_turno turno ON"
				+ "        turno.idinstitucion = pc.idinstitucion"
				+ "    AND"
				+ "        turno.idturno = pc.idturno");
		sqlSol.JOIN("scs_guardiasturno guardia ON"
				+ "        guardia.idinstitucion = pg.idinstitucion"
				+ "    AND"
				+ "        guardia.idguardia = pg.IDGUARDIA_CONFIRMADOR");
		sqlSol.WHERE(" pc.idinstitucion = "+ idInstitucion);
		sqlSol.WHERE(" pc.idguardia = " + permutaItem.getIdguardia());
		sqlSol.WHERE(" pc.idpersona = " + permutaItem.getIdpersona());
		
		sqlCon.SELECT(" PC.IDINSTITUCION,"
				+ "pg.fechaconfirmacion,"
				+ "    pg.fechasolicitud,"
				+ "    pc.IDTURNO,"
				+ "    TURNO.NOMBRE turnopermuta,"
				+ "    GUARDIA.NOMBRE guardiadestino,"
				+ "    pg.IDGUARDIA_CONFIRMADOR,"
				+ "    'Motivos Solicitante: '"
				+ "     || pg.motivossolicitante"
				+ "     || ' / '"
				+ "     || 'Motivos Confirmador: '"
				+ "     || pg.motivosconfirmador motivos,"
				+ "     pc.IDPERSONA,"
				+ "     (CASE WHEN per.nombre is  NULL THEN '' ELSE per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre END) as NOMBRE");
		
		sqlCon.FROM("scs_permuta_cabecera pc");
		sqlCon.JOIN("scs_permutaguardias pg ON"
				+ "        pg.idinstitucion = pc.idinstitucion"
				+ "    AND  pg.idpersona_confirmador = pc.idpersona"
				+ "     AND  pg.idguardia_confirmador = pc.idguardia");
		
		sqlCon.JOIN("cen_persona per ON per.idpersona = pc.idpersona");
		sqlCon.JOIN("scs_turno turno ON"
				+ "        turno.idinstitucion = pc.idinstitucion"
				+ "    AND"
				+ "        turno.idturno = pc.idturno");
		sqlCon.JOIN("scs_guardiasturno guardia ON"
				+ "        guardia.idinstitucion = pg.idinstitucion"
				+ "    AND"
				+ "        guardia.idguardia = pg.IDGUARDIA_CONFIRMADOR");
		sqlCon.WHERE(" pc.idinstitucion = "+ idInstitucion);
		sqlCon.WHERE(" pc.idguardia = " + permutaItem.getIdguardia());
		sqlCon.WHERE(" pc.idpersona = " + permutaItem.getIdpersona());
		
		
		sql = sqlSol.toString() + " UNION " + sqlCon.toString();
		
		return sql;
	}

}
