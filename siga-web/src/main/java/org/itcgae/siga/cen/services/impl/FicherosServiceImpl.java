package org.itcgae.siga.cen.services.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.FicherosService;
import org.itcgae.siga.db.entities.GenFichero;
import org.itcgae.siga.db.mappers.GenFicheroMapper;
import org.itcgae.siga.db.services.cen.mappers.GenFicheroExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FicherosServiceImpl implements FicherosService {
	private static Logger LOGGER = Logger.getLogger(FicherosServiceImpl.class);

	@Autowired
	private GenFicheroExtendsMapper genFicheroExtendsMapper;
	
	public FicheroVo getFichero(FicheroVo ficheroVo) throws BusinessException {
		Date dateLog = new Date();
		LOGGER.info(dateLog+":inicio.FicherosServiceImpl.getFichero");
		GenFichero genFichero = getVo2Db(ficheroVo);
		genFichero = genFicheroExtendsMapper.selectByPrimaryKey(genFichero);
		ficheroVo = getDb2Vo(genFichero);
		LOGGER.info(dateLog+":fin.FicherosServiceImpl.getFichero");
		return ficheroVo;
	}

	private GenFichero getVo2Db(FicheroVo objectVo) {
		Date dateLog = new Date();
		LOGGER.info(dateLog+":inicio.FicherosServiceImpl.getVo2Db");
		GenFichero genFichero = new GenFichero();
		genFichero.setExtension(objectVo.getExtension());
		genFichero.setDirectorio(objectVo.getDirectorio());
		genFichero.setDescripcion(objectVo.getDescripcion());
		
		NewIdDTO idFichero = genFicheroExtendsMapper.selectMaxIdFicheroByIdInstitucion(String.valueOf(objectVo.getIdinstitucion()));
		
		if(idFichero == null) {
			genFichero.setIdfichero((long) 1);
		}else {
			genFichero.setIdfichero(Long.valueOf(idFichero.getNewId()) + 1);
		}
	
		genFichero.setIdinstitucion(objectVo.getIdinstitucion());
		genFichero.setFechamodificacion(objectVo.getFechamodificacion());
		genFichero.setUsumodificacion(objectVo.getUsumodificacion());
		LOGGER.info(dateLog+":fin.FicherosServiceImpl.getVo2Db");
		return genFichero;
	}

	public void insert(FicheroVo ficheroVo) throws BusinessException {
		Date dateLog = new Date();
		LOGGER.info(dateLog+":inicio.FicherosServiceImpl.insert");
		GenFichero genFichero = getVo2Db(ficheroVo);
		genFichero.setFechamodificacion(new Date());
		genFichero.setUsumodificacion(ficheroVo.getUsumodificacion());
		
		genFicheroExtendsMapper.insert(genFichero);
		FicheroVo auxFicheroVo = getDb2Vo(genFichero);
		ficheroVo.setIdfichero(auxFicheroVo.getIdfichero());
		ficheroVo.setNombre(auxFicheroVo.getNombre());
		LOGGER.info(dateLog+":fin.FicherosServiceImpl.insert");
	}

	private FicheroVo getDb2Vo(GenFichero objectDb) {
		Date dateLog = new Date();
		LOGGER.info(dateLog+":inicio.FicherosServiceImpl.getDb2Vo");
		FicheroVo ficheroVo = new FicheroVo();
		ficheroVo.setExtension(objectDb.getExtension());
		ficheroVo.setDirectorio(objectDb.getDirectorio());
		
		ficheroVo.setDescripcion(objectDb.getDescripcion());
		ficheroVo.setIdfichero(objectDb.getIdfichero());
		ficheroVo.setIdinstitucion(objectDb.getIdinstitucion());
		ficheroVo.setNombre(getNombreFichero(ficheroVo));
		ficheroVo.setFechamodificacion(objectDb.getFechamodificacion());
		ficheroVo.setUsumodificacion(objectDb.getUsumodificacion());
		LOGGER.info(dateLog+":fin.FicherosServiceImpl.getDb2Vo");
		return ficheroVo;
	}
	
	private String  getNombreFichero(FicheroVo objectVo){
		Date dateLog = new Date();
		LOGGER.info(dateLog+":inicio.FicherosServiceImpl.getNombreFichero");
		StringBuffer nombreFichero = new StringBuffer();
		nombreFichero.append(objectVo.getIdinstitucion());
		nombreFichero.append("_");
		nombreFichero.append(objectVo.getIdfichero());
		nombreFichero.append(".");
		nombreFichero.append(objectVo.getExtension());
		LOGGER.info(dateLog+":fin.FicherosServiceImpl.getNombreFichero");
		return nombreFichero.toString();
	}
}