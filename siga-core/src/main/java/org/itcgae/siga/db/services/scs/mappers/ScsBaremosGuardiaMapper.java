package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;
import org.itcgae.siga.DTO.scs.BaremosRequestDTO;
import org.itcgae.siga.DTO.scs.BaremosRequestItem;
import org.itcgae.siga.db.entities.ScsBaremosGuardiaKey;
import org.itcgae.siga.db.services.fcs.providers.FcsFacturacionJGSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsBaremosGuardiaSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsBaremosGuardiaMapper {

//	@Select({ "select"," Listagg(Gua.Nombre, ', ') Within Group(Order By Gua.Nombre) GUARDIAS,"
//			, " gua.DIASGUARDIA || ' ' || gua.tipodiasguardia N_DIAS,"
//			+ " f_Siga_Getrecurso(Tip.Descripcion, 1) BAREMO,"
//			+ "   'L: ' || "
//			+ "case when REGEXP_COUNT(gua.seleccionlaborables || Hit.Diasaplicables, 'L')=2 then 'L' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionlaborables || Hit.Diasaplicables, 'M')=2 then 'M' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionlaborables || Hit.Diasaplicables, 'X')=2 then 'X' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionlaborables || Hit.Diasaplicables, 'J')=2 then 'J' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionlaborables || Hit.Diasaplicables, 'V')=2 then 'V' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionlaborables || Hit.Diasaplicables, 'S')=2 then 'S' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionlaborables || Hit.Diasaplicables, 'D')=2 then 'D' end "
//			+ "       || ' ' || chr(10) || 'F: ' ||  "
//			+ "case when REGEXP_COUNT(gua.seleccionfestivos || Hit.Diasaplicables, 'L')=2 then 'L' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionfestivos || Hit.Diasaplicables, 'M')=2 then 'M' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionfestivos || Hit.Diasaplicables, 'X')=2 then 'X' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionfestivos || Hit.Diasaplicables, 'J')=2 then 'J' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionfestivos || Hit.Diasaplicables, 'V')=2 then 'V' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionfestivos || Hit.Diasaplicables, 'S')=2 then 'S' end || "
//			+ "case when REGEXP_COUNT(gua.seleccionfestivos || Hit.Diasaplicables, 'D')=2 then 'D' end "
//			+ " DIAS, "
//			+ " (select hit2.PRECIOHITO from Scs_Hitofacturableguardia hit2 "
//			+ " where hit2.IDINSTITUCION = hit.idinstitucion and hit2.IDTURNO = hit.idturno and hit2.IDGUARDIA = hit.idguardia "
//			+ "     and hit2.DIASAPLICABLES = hit.diasaplicables and hit2.AGRUPAR = hit.agrupar and hit2.IDHITO = case hit.idhito when 7 then 19 when 22 then 19 when 5 then 10 when 20 then 10 when 44 then 54 when 1 then 53 end) "
//			+ "		DISPONIBILIDAD,"
//			+ " (select hit2.PRECIOHITO from Scs_Hitofacturableguardia hit2 where hit2.IDINSTITUCION = hit.idinstitucion and hit2.IDTURNO = hit.idturno and hit2.IDGUARDIA = hit.idguardia"
//			+ "       and hit2.DIASAPLICABLES = hit.diasaplicables and hit2.AGRUPAR = hit.agrupar and hit2.IDHITO = case hit.idhito when 44 then 56 when 1 then 55 end) "
//			+ "     NUM_MINIMO_SIMPLE ,  "
//			+ " (select hit2.PRECIOHITO from Scs_Hitofacturableguardia hit2 where hit2.IDINSTITUCION = hit.idinstitucion and hit2.IDTURNO = hit.idturno and hit2.IDGUARDIA = hit.idguardia "
//			+ "       and hit2.DIASAPLICABLES = hit.diasaplicables and hit2.AGRUPAR = hit.agrupar and hit2.IDHITO = case hit.idhito when 7 then 7 when 9 then 9 when 22 then 22 when 5 then 5 when 20 then 20 when 44 then 44 when 1 then 1 when 12 then 12 when 13 then 13 end) "
//			+ "		SIMPLE_O_IMPORTE_INDIVIDUAL,  "
//			+ " (select hit2.PRECIOHITO from Scs_Hitofacturableguardia hit2 where hit2.IDINSTITUCION = hit.idinstitucion and hit2.IDTURNO = hit.idturno and hit2.IDGUARDIA = hit.idguardia "
//			+ "       and hit2.DIASAPLICABLES = hit.diasaplicables and hit2.AGRUPAR = hit.agrupar and hit2.IDHITO = case hit.idhito when 44 then 46 when 1 then 45 end) "
//			+ "		NUM_DOBLAR,  "
//			+ "  (select hit2.PRECIOHITO from Scs_Hitofacturableguardia hit2 where hit2.IDINSTITUCION = hit.idinstitucion and hit2.IDTURNO = hit.idturno and hit2.IDGUARDIA = hit.idguardia "
//			+ "       and hit2.DIASAPLICABLES = hit.diasaplicables and hit2.AGRUPAR = hit.agrupar and hit2.IDHITO = case hit.idhito when 7 then 8 when 22 then 8 when 5 then 3 when 20 then 3 when 9 then 6 when 24 then 6 when 44 then 4 when 1 then 2 end) maximo, "
//			+ "       case when Nvl(Hit.Agrupar, 0) = '1' then 'No' else 'Si' end "
//			+ "		POR_DIA ",
//			" From Scs_Hitofacturableguardia Hit, Scs_Hitofacturable Tip, Scs_Guardiasturno Gua ",
//			" Where Hit.Idhito = Tip.Idhito "
//			+ " And Hit.Idinstitucion = Gua.Idinstitucion "
//			+ " And Hit.Idturno = Gua.Idturno "
//			+ " And Hit.Idguardia = Gua.Idguardia "
//			+ " and hit.idhito in (7, 9, 25, 22, 5, 20, 44, 1, 12, 13) " 		
//			+ " and Hit.Idturno = #{idTurno,jdbcType=INTEGER} "
//			+ " and Hit.Idinstitucion = #{idInstitucion,jdbcType=DECIMAL} "
//			+ " and gua.idguardia = #{idGuardia,jdbcType=DECIMAL} "
//			+ " Group By hit.idinstitucion, hit.idturno, hit.idguardia, gua.DIASGUARDIA, "
//			+ " gua.tipodiasguardia, Tip.Descripcion, gua.seleccionlaborables, "
//			+ " gua.seleccionfestivos, Hit.Preciohito, Hit.Diasaplicables, Hit.Agrupar, hit.idhito "
//			+ " Order By 2, 1"
//	}
//			)
//	
//	@Results({
//		@Result(column = "GUARDIAS", property = "guardias", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "N_DIAS", property = "nDias", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "BAREMO", property = "baremo", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "DIAS", property = "dias", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "DISPONIBILIDAD", property = "disponibilidad", jdbcType = JdbcType.DECIMAL),
//		@Result(column = "NUM_MINIMO_SIMPLE", property = "numMinimoSimple", jdbcType = JdbcType.DECIMAL),
//		@Result(column = "SIMPLE_O_IMPORTE_INDIVIDUAL", property = "simpleOImporteIndividual", jdbcType = JdbcType.DECIMAL),
//		@Result(column = "NUM_DOBLAR", property = "numDoblar", jdbcType = JdbcType.DECIMAL),
//		@Result(column = "MAXIMO", property = "maximo", jdbcType = JdbcType.DECIMAL),
//		@Result(column = "POR_DIA", property = "porDia", jdbcType = JdbcType.VARCHAR)
//	})
//	List<BaremosGuardiaItem> searchBaremosGuardia(BaremosGuardiaItem baremosGuardiaItem, Short idinstitucion);
	
	@SelectProvider(type = ScsBaremosGuardiaSqlProvider.class, method = "searchBaremosGuardia")
@Results({
	
	@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
	@Result(column = "NOMBRETURNO", property = "nomTurno", jdbcType = JdbcType.VARCHAR),
	@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
	@Result(column = "GUARDIAS", property = "guardias", jdbcType = JdbcType.VARCHAR),
	@Result(column = "N_DIAS", property = "nDias", jdbcType = JdbcType.VARCHAR),
	@Result(column = "BAREMO", property = "baremo", jdbcType = JdbcType.VARCHAR),
	@Result(column = "DIAS", property = "dias", jdbcType = JdbcType.VARCHAR),
	@Result(column = "DISPONIBILIDAD", property = "disponibilidad", jdbcType = JdbcType.DECIMAL),
	@Result(column = "NUM_MINIMO_SIMPLE", property = "numMinimoSimple", jdbcType = JdbcType.DECIMAL),
	@Result(column = "SIMPLE_O_IMPORTE_INDIVIDUAL", property = "simpleOImporteIndividual", jdbcType = JdbcType.DECIMAL),
	@Result(column = "NAPARTIR", property = "naPartir", jdbcType = JdbcType.INTEGER),
	@Result(column = "MAXIMO", property = "maximo", jdbcType = JdbcType.DECIMAL),
	@Result(column = "POR_DIA", property = "porDia", jdbcType = JdbcType.VARCHAR),
	@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
	@Result(column = "IDHITO", property = "idHito", jdbcType = JdbcType.DECIMAL),
	@Result(column = "IDHITOCONFIGURACION", property = "idhitoconfiguracion", jdbcType = JdbcType.DECIMAL),
})
List<BaremosRequestItem> searchBaremosGuardia(BaremosGuardiaItem baremosGuardiaItem, Short idinstitucion);

	@SelectProvider(type = ScsBaremosGuardiaSqlProvider.class, method = "searchBaremosFichaGuardia")
	@Results({

			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRETURNO", property = "nomTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUARDIAS", property = "guardias", jdbcType = JdbcType.VARCHAR),
			@Result(column = "N_DIAS", property = "nDias", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BAREMO", property = "baremo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DIAS", property = "dias", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DISPONIBILIDAD", property = "disponibilidad", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NUM_MINIMO_SIMPLE", property = "numMinimoSimple", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SIMPLE_O_IMPORTE_INDIVIDUAL", property = "simpleOImporteIndividual", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NAPARTIR", property = "naPartir", jdbcType = JdbcType.INTEGER),
			@Result(column = "MAXIMO", property = "maximo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "POR_DIA", property = "porDia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
			@Result(column = "IDHITO", property = "idHito", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDHITOCONFIGURACION", property = "idhitoconfiguracion", jdbcType = JdbcType.DECIMAL),
	})
	List<BaremosRequestItem> searchBaremosFichaGuardia(String idGuardia, Short idinstitucion, Integer tamMaximo);
}
