package org.itcgae.siga.form.services;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.age.EventoDTO;
import org.itcgae.siga.DTOs.form.CargaMasivaInscripcionesDTO;
import org.itcgae.siga.DTOs.form.CertificadoCursoDTO;
import org.itcgae.siga.DTOs.form.CertificadoCursoItem;
import org.itcgae.siga.DTOs.form.CursoDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.FormadorCursoDTO;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.form.PreciosCursoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IFichaCursosService {
	
	public void updateEstadoCursoAuto();
	
	public FormadorCursoDTO getTrainersCourse(String idCurso, HttpServletRequest request);
	
	public ComboDTO getRolesTrainers(HttpServletRequest request);
	
	public ComboDTO getTypeCostTrainers(HttpServletRequest request);

	public UpdateResponseDTO updateTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request);

	public InsertResponseDTO saveTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request);

	public UpdateResponseDTO deleteTrainersCourse(FormadorCursoDTO formadorCursoDTO, HttpServletRequest request);
	
	public InsertResponseDTO saveCourse(CursoItem cursoItem, HttpServletRequest request);

	public UpdateResponseDTO updateCourse(CursoItem cursoItem, HttpServletRequest request);
	
	public UpdateResponseDTO releaseOrAnnounceCourse(CursoDTO cursoDTO , HttpServletRequest request);

	public CursoItem searchCourse(String idCurso, HttpServletRequest request);
	
	public EventoDTO getSessionsCourse(CursoItem cursoItem, HttpServletRequest request);

	public ComboDTO getServicesCourse(HttpServletRequest request);
	
	public ComboDTO getServicesSpecificCourse(HttpServletRequest request, String idCurso);
	
	public InscripcionItem getCountIncriptions(HttpServletRequest request, String idCurso);
	
	public File createExcelInscriptionsFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector) throws BusinessException;
	
	public ResponseEntity<InputStreamResource> generateExcelInscriptions(CursoItem cursoItem, HttpServletRequest request);

	public UpdateResponseDTO uploadFileExcel(int idCurso, MultipartHttpServletRequest request) throws IllegalStateException, IOException;
	
	public CargaMasivaInscripcionesDTO getMassiveLoadInscriptions(HttpServletRequest request, String idCurso);

	public UpdateResponseDTO deleteInscriptionsCourse(CargaMasivaInscripcionesDTO cargaMasivaInscripcionesDTO, HttpServletRequest request);
	
	public UpdateResponseDTO autovalidateInscriptionsCourse(CargaMasivaInscripcionesDTO cargaMasivaInscripcionesDTO, HttpServletRequest request);

	public ComboDTO getTopicsCourse(HttpServletRequest request);

	public ComboDTO getTopicsSpecificCourse(HttpServletRequest request, String idCurso);
	
	public UpdateResponseDTO cancelCourse(CursoDTO cursoDTO , HttpServletRequest request);

	public UpdateResponseDTO finishCourse(CursoDTO cursoDTO , HttpServletRequest request);
	
	public PreciosCursoDTO getPricesCourse(HttpServletRequest request, String idCurso);
	
	public ComboDTO getQualificationsCourse(HttpServletRequest request);
	
	public CertificadoCursoDTO getTypesCertificatesCourse (HttpServletRequest request);
	
	public CertificadoCursoDTO getCertificatesCourse(HttpServletRequest request, String idCurso);

	public InsertResponseDTO saveCertificateCourse(CertificadoCursoItem certificadoCursoItem, HttpServletRequest request);

	public UpdateResponseDTO updateCertificatesCourse(CertificadoCursoDTO certifcadoCursoDTO, HttpServletRequest request);

	public UpdateResponseDTO deleteCertificatesCourse(CertificadoCursoDTO certifcadoCursoDTO, HttpServletRequest request);

	public UpdateResponseDTO releaseCourse(CursoDTO cursoDTO , HttpServletRequest request);

	public UpdateResponseDTO announceCourse(CursoDTO cursoDTO , HttpServletRequest request);
	
	public int createServiceCourse(ForCurso cursoItem, AdmUsuarios usuario, Short idInstitucion);


}
