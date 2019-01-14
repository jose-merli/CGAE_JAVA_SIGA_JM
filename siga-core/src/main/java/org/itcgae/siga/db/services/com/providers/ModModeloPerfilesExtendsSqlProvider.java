package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModModeloPerfilesExtendsSqlProvider {
	
	public String selectPerfilesModelo(Short idInstitucion, Long idModeloComunicacion){
		
		SQL sql = new SQL();		
				
		sql.SELECT("modeloPerfil.IDPERFIL");
		sql.SELECT("admPerfil.DESCRIPCION");
		
		sql.FROM("MOD_MODELOSPERFILES modeloPerfil");
		
		sql.INNER_JOIN("adm_perfil admPerfil ON admPerfil.IDPERFIL = modeloPerfil.IDPERFIL");	
		
		sql.WHERE("modeloPerfil.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("modeloPerfil.IDMODELOCOMUNICACION = " + idModeloComunicacion);
		
		return sql.toString();
	}

}
