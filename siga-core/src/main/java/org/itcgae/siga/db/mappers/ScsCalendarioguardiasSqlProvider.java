package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsCalendarioguardias;
import org.itcgae.siga.db.entities.ScsCalendarioguardiasExample.Criteria;
import org.itcgae.siga.db.entities.ScsCalendarioguardiasExample.Criterion;
import org.itcgae.siga.db.entities.ScsCalendarioguardiasExample;

public class ScsCalendarioguardiasSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    public String countByExample(ScsCalendarioguardiasExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("SCS_CALENDARIOGUARDIAS");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    public String deleteByExample(ScsCalendarioguardiasExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("SCS_CALENDARIOGUARDIAS");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    public String insertSelective(ScsCalendarioguardias record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("SCS_CALENDARIOGUARDIAS");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdturno() != null) {
            sql.VALUES("IDTURNO", "#{idturno,jdbcType=DECIMAL}");
        }
        
        if (record.getIdguardia() != null) {
            sql.VALUES("IDGUARDIA", "#{idguardia,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcalendarioguardias() != null) {
            sql.VALUES("IDCALENDARIOGUARDIAS", "#{idcalendarioguardias,jdbcType=DECIMAL}");
        }
        
        if (record.getFechainicio() != null) {
            sql.VALUES("FECHAINICIO", "#{fechainicio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechafin() != null) {
            sql.VALUES("FECHAFIN", "#{fechafin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getObservaciones() != null) {
            sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpersonaUltimoanterior() != null) {
            sql.VALUES("IDPERSONA_ULTIMOANTERIOR", "#{idpersonaUltimoanterior,jdbcType=DECIMAL}");
        }
        
        if (record.getFechasuscUltimoanterior() != null) {
            sql.VALUES("FECHASUSC_ULTIMOANTERIOR", "#{fechasuscUltimoanterior,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdturnoprincipal() != null) {
            sql.VALUES("IDTURNOPRINCIPAL", "#{idturnoprincipal,jdbcType=DECIMAL}");
        }
        
        if (record.getIdguardiaprincipal() != null) {
            sql.VALUES("IDGUARDIAPRINCIPAL", "#{idguardiaprincipal,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcalendarioguardiasprincipal() != null) {
            sql.VALUES("IDCALENDARIOGUARDIASPRINCIPAL", "#{idcalendarioguardiasprincipal,jdbcType=DECIMAL}");
        }
        
        if (record.getIdgrupoguardiaUltimoanterior() != null) {
            sql.VALUES("IDGRUPOGUARDIA_ULTIMOANTERIOR", "#{idgrupoguardiaUltimoanterior,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    public String selectByExample(ScsCalendarioguardiasExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("IDTURNO");
        sql.SELECT("IDGUARDIA");
        sql.SELECT("IDCALENDARIOGUARDIAS");
        sql.SELECT("FECHAINICIO");
        sql.SELECT("FECHAFIN");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("OBSERVACIONES");
        sql.SELECT("IDPERSONA_ULTIMOANTERIOR");
        sql.SELECT("FECHASUSC_ULTIMOANTERIOR");
        sql.SELECT("IDTURNOPRINCIPAL");
        sql.SELECT("IDGUARDIAPRINCIPAL");
        sql.SELECT("IDCALENDARIOGUARDIASPRINCIPAL");
        sql.SELECT("IDGRUPOGUARDIA_ULTIMOANTERIOR");
        sql.FROM("SCS_CALENDARIOGUARDIAS");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ScsCalendarioguardias record = (ScsCalendarioguardias) parameter.get("record");
        ScsCalendarioguardiasExample example = (ScsCalendarioguardiasExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("SCS_CALENDARIOGUARDIAS");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdturno() != null) {
            sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
        }
        
        if (record.getIdguardia() != null) {
            sql.SET("IDGUARDIA = #{record.idguardia,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcalendarioguardias() != null) {
            sql.SET("IDCALENDARIOGUARDIAS = #{record.idcalendarioguardias,jdbcType=DECIMAL}");
        }
        
        if (record.getFechainicio() != null) {
            sql.SET("FECHAINICIO = #{record.fechainicio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechafin() != null) {
            sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpersonaUltimoanterior() != null) {
            sql.SET("IDPERSONA_ULTIMOANTERIOR = #{record.idpersonaUltimoanterior,jdbcType=DECIMAL}");
        }
        
        if (record.getFechasuscUltimoanterior() != null) {
            sql.SET("FECHASUSC_ULTIMOANTERIOR = #{record.fechasuscUltimoanterior,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdturnoprincipal() != null) {
            sql.SET("IDTURNOPRINCIPAL = #{record.idturnoprincipal,jdbcType=DECIMAL}");
        }
        
        if (record.getIdguardiaprincipal() != null) {
            sql.SET("IDGUARDIAPRINCIPAL = #{record.idguardiaprincipal,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcalendarioguardiasprincipal() != null) {
            sql.SET("IDCALENDARIOGUARDIASPRINCIPAL = #{record.idcalendarioguardiasprincipal,jdbcType=DECIMAL}");
        }
        
        if (record.getIdgrupoguardiaUltimoanterior() != null) {
            sql.SET("IDGRUPOGUARDIA_ULTIMOANTERIOR = #{record.idgrupoguardiaUltimoanterior,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_CALENDARIOGUARDIAS");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
        sql.SET("IDGUARDIA = #{record.idguardia,jdbcType=DECIMAL}");
        sql.SET("IDCALENDARIOGUARDIAS = #{record.idcalendarioguardias,jdbcType=DECIMAL}");
        sql.SET("FECHAINICIO = #{record.fechainicio,jdbcType=TIMESTAMP}");
        sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        sql.SET("IDPERSONA_ULTIMOANTERIOR = #{record.idpersonaUltimoanterior,jdbcType=DECIMAL}");
        sql.SET("FECHASUSC_ULTIMOANTERIOR = #{record.fechasuscUltimoanterior,jdbcType=TIMESTAMP}");
        sql.SET("IDTURNOPRINCIPAL = #{record.idturnoprincipal,jdbcType=DECIMAL}");
        sql.SET("IDGUARDIAPRINCIPAL = #{record.idguardiaprincipal,jdbcType=DECIMAL}");
        sql.SET("IDCALENDARIOGUARDIASPRINCIPAL = #{record.idcalendarioguardiasprincipal,jdbcType=DECIMAL}");
        sql.SET("IDGRUPOGUARDIA_ULTIMOANTERIOR = #{record.idgrupoguardiaUltimoanterior,jdbcType=DECIMAL}");
        
        ScsCalendarioguardiasExample example = (ScsCalendarioguardiasExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    public String updateByPrimaryKeySelective(ScsCalendarioguardias record) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_CALENDARIOGUARDIAS");
        
        if (record.getFechainicio() != null) {
            sql.SET("FECHAINICIO = #{fechainicio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechafin() != null) {
            sql.SET("FECHAFIN = #{fechafin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpersonaUltimoanterior() != null) {
            sql.SET("IDPERSONA_ULTIMOANTERIOR = #{idpersonaUltimoanterior,jdbcType=DECIMAL}");
        }
        
        if (record.getFechasuscUltimoanterior() != null) {
            sql.SET("FECHASUSC_ULTIMOANTERIOR = #{fechasuscUltimoanterior,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdturnoprincipal() != null) {
            sql.SET("IDTURNOPRINCIPAL = #{idturnoprincipal,jdbcType=DECIMAL}");
        }
        
        if (record.getIdguardiaprincipal() != null) {
            sql.SET("IDGUARDIAPRINCIPAL = #{idguardiaprincipal,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcalendarioguardiasprincipal() != null) {
            sql.SET("IDCALENDARIOGUARDIASPRINCIPAL = #{idcalendarioguardiasprincipal,jdbcType=DECIMAL}");
        }
        
        if (record.getIdgrupoguardiaUltimoanterior() != null) {
            sql.SET("IDGRUPOGUARDIA_ULTIMOANTERIOR = #{idgrupoguardiaUltimoanterior,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("IDTURNO = #{idturno,jdbcType=DECIMAL}");
        sql.WHERE("IDGUARDIA = #{idguardia,jdbcType=DECIMAL}");
        sql.WHERE("IDCALENDARIOGUARDIAS = #{idcalendarioguardias,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    protected void applyWhere(SQL sql, ScsCalendarioguardiasExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
    
	
	
	   public String setLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String logName, String idTurno, String idGuardia) {

		SQL sql = new SQL();
		 
		sql.UPDATE("SCS_CALENDARIOGUARDIAS PC");
			if (logName != null && !logName.isEmpty()) {
				sql.SET("LOG_GENERACION_NAME = '" + logName + "'");
			}
			if (idCG != null && !idCG.isEmpty()) {
				sql.WHERE("PC.IDCALENDARIOGUARDIAS = " + idCG);
			}
			if (idInstitucion != null && !idInstitucion.isEmpty()) {
				sql.WHERE("PC.IDINSTITUCION = " + idInstitucion);
			}
			if (idTurno != null && !idTurno.isEmpty()) {
				sql.WHERE("PC.IDTURNO = " + idTurno);
			}
			if (idGuardia != null && !idGuardia.isEmpty()) {
				sql.WHERE("PC.IDGUARDIA = " + idGuardia);
			}

		return sql.toString();
	}

//	   public String addLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String logName, String idTurno, String idGuardia) {
//
//		SQL sql = new SQL();
//		 
//		sql.INSERT_INTO("SCS_CALENDARIOGUARDIAS");
//			if (logName != null && !logName.isEmpty()) {
//			sql.VALUES("LOG_GENERACION_NAME", logName);
//			}
//			if (fechaIni != null && !fechaIni.isEmpty()) {
//			sql.WHERE("FECHACALINICIO", "TO_DATE('" + fechaIni+ "', 'dd/MM/yyyy')");
//			}
//			if (fechaFin != null && !fechaFin.isEmpty()) {
//			sql.WHERE("FECHACALFIN", "TO_DATE('" + fechaFin + "', 'dd/MM/yyyy')");
//			}
//			if (idCG != null && !idCG.isEmpty()) {
//			sql.VALUES("IDCALENDARIOGUARDIAS" , idCG);
//			}
//			if (idInstitucion != null && !idInstitucion.isEmpty()) {
//			sql.VALUES("IDINSTITUCION" , idInstitucion);
//			}
//			if (idTurno != null && !idTurno.isEmpty()) {
//			sql.VALUES("IDTURNO" , idTurno);
//			}
//			if (idGuardia != null && !idGuardia.isEmpty()) {
//			sql.VALUES("IDGUARDIA" , idGuardia);
//			}
//
//		return sql.toString();
//	}  
	   
	   public String getLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String idTurno, String idGuardia) {

		SQL sql = new SQL();
			sql.SELECT("LOG_GENERACION_NAME");
			sql.FROM("SCS_CALENDARIOGUARDIAS");
			if (idCG != null && !idCG.isEmpty()) {
				sql.WHERE("IDCALENDARIOGUARDIAS = " + idCG);
			}
			if (idInstitucion != null && !idInstitucion.isEmpty()) {
				sql.WHERE("IDINSTITUCION = " + idInstitucion);
			}
			if (idTurno != null && !idTurno.isEmpty()) {
				sql.WHERE("IDTURNO = " + idTurno);
			}
			if (idGuardia != null && !idGuardia.isEmpty()) {
				sql.WHERE("IDGUARDIA = " + idGuardia);
			}

		return sql.toString();
	}
	   
	   public String getGenerado(String idProgCal) {
		   SQL sql = new SQL();
			sql.SELECT("DECODE(PC.ESTADO, 2, 'No', 'Si') AS GENERADO");
			sql.FROM("SCS_PROG_CALENDARIOS PC");
			sql.WHERE("IDPROGCALENDARIO = " + idProgCal);
			return sql.toString();
	   }
	   
	   public String getGeneracionEnProceso() {
		   SQL sql = new SQL();
		   
			sql.SELECT("IDPROGCALENDARIO");
			sql.FROM("SCS_PROG_CALENDARIOS PC");
			sql.WHERE("PROCESANDOGENERACION = " + 1);
			
			return sql.toString();
	   }
	   
	   public String setGeneracionEnProceso(String idProgCal, String procesando) {
		   SQL sql = new SQL();
		   	sql.UPDATE("SCS_PROG_CALENDARIOS PC");
			sql.SET("PROCESANDOGENERACION = " +  procesando);
			sql.WHERE("IDPROGCALENDARIO = " + idProgCal);
			return sql.toString();
	   }

}