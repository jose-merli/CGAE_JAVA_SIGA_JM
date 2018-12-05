package org.itcgae.siga.cen.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.SolModifDatosBancariosItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.cen.services.ISolModifDatosBancariosDetailService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenCuentasbancariasKey;
import org.itcgae.siga.db.entities.CenSolicmodicuentas;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicmodicuentasExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolModifDatosBancariosDetailServiceImpl implements ISolModifDatosBancariosDetailService {

	@Autowired
	private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;

	@Autowired
	private CenSolicmodicuentasExtendsMapper cenSolicmodicuentasExtendsMapper;

	@Override
	public SolModifDatosBancariosItem searchDatosBancariosDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		SolModifDatosBancariosItem solModifDatosBancariosItem = new SolModifDatosBancariosItem();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		CenCuentasbancariasKey cenCuentasbancariasKey = new CenCuentasbancariasKey();
		cenCuentasbancariasKey.setIdcuenta(Short.valueOf(solModificacionItem.getCodigo()));
		cenCuentasbancariasKey.setIdinstitucion(idInstitucion);
		cenCuentasbancariasKey.setIdpersona(Long.valueOf(solModificacionItem.getIdPersona()));

		CenCuentasbancarias cenCuentasBancarias = cenCuentasbancariasExtendsMapper
				.selectByPrimaryKey(cenCuentasbancariasKey);

		if(cenCuentasBancarias != null) {
			
			if(cenCuentasBancarias.getAbonocargo() != null) {
				solModifDatosBancariosItem.setAbonoCargo(cenCuentasBancarias.getAbonocargo());
			}

			if (cenCuentasBancarias.getAbonosjcs().equals("1")) {
				solModifDatosBancariosItem.setAbonoJCS("Sí");
			} else {
				solModifDatosBancariosItem.setAbonoJCS("No");
			}

			if(cenCuentasBancarias.getCodigosucursal() != null) {
				solModifDatosBancariosItem.setCodigoSucursal(cenCuentasBancarias.getCodigosucursal());
			}
			
			if(cenCuentasBancarias.getDigitocontrol() != null){
				solModifDatosBancariosItem.setDigitoControl(cenCuentasBancarias.getDigitocontrol());
			}
			
			solModifDatosBancariosItem.setIdCuenta(String.valueOf(cenCuentasBancarias.getIdcuenta()));
			
			if(cenCuentasBancarias.getIban() != null) {
				solModifDatosBancariosItem.setIban(cenCuentasBancarias.getIban());
			}
			
			solModifDatosBancariosItem.setIdPersona(String.valueOf(cenCuentasBancarias.getIdpersona()));
			
			if(cenCuentasBancarias.getNumerocuenta() != null) {
				solModifDatosBancariosItem.setNumeroCuenta(cenCuentasBancarias.getNumerocuenta());
			}
			
			if(cenCuentasBancarias.getTitular() != null) {
				solModifDatosBancariosItem.setTitular(cenCuentasBancarias.getTitular());
			}
		}
		return solModifDatosBancariosItem;
	}

	@Override
	public SolModifDatosBancariosItem searchSolModifDatosBancariosDetail(int numPagina,
			SolModificacionItem solModificacionItem, HttpServletRequest request) {
		SolModifDatosBancariosItem solModifDatosBancariosItem = new SolModifDatosBancariosItem();

		CenSolicmodicuentas cenSolicmodicuentas = cenSolicmodicuentasExtendsMapper
				.selectByPrimaryKey(Long.valueOf(solModificacionItem.getIdSolicitud()));

		if (cenSolicmodicuentas != null) {
			solModifDatosBancariosItem.setAbonoCargo(cenSolicmodicuentas.getAbonocargo());

			if (cenSolicmodicuentas.getAbonosjcs().equals("1")) {
				solModifDatosBancariosItem.setAbonoJCS("Sí");
			} else {
				solModifDatosBancariosItem.setAbonoJCS("No");
			}

			solModifDatosBancariosItem.setIdCuenta(String.valueOf(cenSolicmodicuentas.getIdcuenta()));
			
			if(cenSolicmodicuentas.getCodigosucursal() != null) {
				solModifDatosBancariosItem.setCodigoSucursal(cenSolicmodicuentas.getCodigosucursal());
			}
				
			if(cenSolicmodicuentas.getDigitocontrol() != null) {
				solModifDatosBancariosItem.setDigitoControl(cenSolicmodicuentas.getDigitocontrol());
			}
			
			if(cenSolicmodicuentas.getIban() != null) {
				solModifDatosBancariosItem.setIban(cenSolicmodicuentas.getIban());
			}
			
			solModifDatosBancariosItem.setIdPersona(String.valueOf(cenSolicmodicuentas.getIdpersona()));
			
			if(cenSolicmodicuentas.getNumerocuenta() != null) {
				solModifDatosBancariosItem.setNumeroCuenta(cenSolicmodicuentas.getNumerocuenta());
			}
			
			if(cenSolicmodicuentas.getTitular() != null) {
				solModifDatosBancariosItem.setTitular(cenSolicmodicuentas.getTitular());
			}
		}

		return solModifDatosBancariosItem;
	}
}
