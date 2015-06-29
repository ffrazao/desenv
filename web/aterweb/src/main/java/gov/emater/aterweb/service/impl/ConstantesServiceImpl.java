package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.dao.PessoaGrupoCidadeViDao;
import gov.emater.aterweb.dao.PessoaGrupoEstadoViDao;
import gov.emater.aterweb.dao.PessoaGrupoMunicipioViDao;
import gov.emater.aterweb.dao.PessoaGrupoPaisViDao;
import gov.emater.aterweb.dao.PessoaGrupoTipoDao;
import gov.emater.aterweb.dao.RelacionamentoTipoDao;
import gov.emater.aterweb.model.PessoaGrupoTipo;
import gov.emater.aterweb.model.RelacionamentoTipo;
import gov.emater.aterweb.service.ConstantesService;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public final class ConstantesServiceImpl implements ConstantesService {

	private static final Logger logger = Logger.getLogger(ConstantesServiceImpl.class);

	private PessoaGrupoTipo baciaHidrograficaPessoaGrupoTipo;

	private String baseUrl;

	private PessoaGrupoTipo comunidadePessoaGrupoTipo;

	private PessoaGrupoTipo personalizadoPessoaGrupoTipo;

	@Autowired
	private PessoaGrupoCidadeViDao pessoaGrupoCidadeViDao;

	@Autowired
	private PessoaGrupoEstadoViDao pessoaGrupoEstadoViDao;

	private String pessoaGrupoJson;

	@Autowired
	private PessoaGrupoMunicipioViDao pessoaGrupoMunicipioViDao;

	@Autowired
	private PessoaGrupoPaisViDao pessoaGrupoPaisViDao;

	@Autowired
	private PessoaGrupoTipoDao pessoaGrupoTipoDao;

	@Autowired
	private RelacionamentoTipoDao relacionamentoTipoDao;

	private String servletLocalDirectoryPath;

	private String servletResourcesLocalDirectoryPath;

	public ConstantesServiceImpl() {
	}

	@Override
	@Transactional(readOnly = true)
	public PessoaGrupoTipo getBaciaHidrograficaPessoaGrupoTipo() {
		if (baciaHidrograficaPessoaGrupoTipo == null) {
			this.baciaHidrograficaPessoaGrupoTipo = getPessoaGrupoTipo(PessoaGrupoTipo.Codigo.BACIA_HIDROGRAFICA);
		}
		return baciaHidrograficaPessoaGrupoTipo;
	}

	@Override
	@Transactional(readOnly = true)
	public String getBaseUrl(HttpServletRequest request) {
		if (baseUrl == null) {
			baseUrl = request.getRequestURL().toString().replaceFirst(request.getRequestURI(), request.getContextPath());
		}
		return baseUrl;
	}

	@Override
	@Transactional(readOnly = true)
	public PessoaGrupoTipo getComunidadePessoaGrupoTipo() {
		if (this.comunidadePessoaGrupoTipo == null) {
			this.comunidadePessoaGrupoTipo = getPessoaGrupoTipo(PessoaGrupoTipo.Codigo.COMUNIDADE);
		}
		return comunidadePessoaGrupoTipo;
	}

	@Override
	@Transactional(readOnly = true)
	public PessoaGrupoTipo getPersonalizadoPessoaGrupoTipo() {
		if (this.personalizadoPessoaGrupoTipo == null) {
			this.personalizadoPessoaGrupoTipo = getPessoaGrupoTipo(PessoaGrupoTipo.Codigo.PERSONALIZADO);
		}
		return this.personalizadoPessoaGrupoTipo;
	}

	@Override
	@Transactional(readOnly = true)
	public String getPessoaGrupoJson() {
		if (pessoaGrupoJson == null) {
			// somente uma thread poderá atualizar esta variável
			synchronized (this) {
				// executar somente se a variavel continuar nula
				if (pessoaGrupoJson == null) {
					long tempo = System.currentTimeMillis();
					if (logger.isDebugEnabled()) {
						logger.debug("Construindo json de localidades");
					}
					// construir pessoaGrupoJson
					List<Map<String, Object>> paisList = pessoaGrupoPaisViDao.restoreMap();
					if (paisList != null && paisList.size() > 0) {
						for (Map<String, Object> pais : paisList) {
							List<Map<String, Object>> estadoList = pessoaGrupoEstadoViDao.restoreMap((Integer) pais.get("id"));
							if (estadoList != null && estadoList.size() > 0) {
								for (Map<String, Object> estado : estadoList) {
									List<Map<String, Object>> municipioList = pessoaGrupoMunicipioViDao.restoreMap((Integer) estado.get("id"));
									if (municipioList != null && municipioList.size() > 0) {
										for (Map<String, Object> municipio : municipioList) {
											List<Map<String, Object>> cidadeList = pessoaGrupoCidadeViDao.restoreMap((Integer) municipio.get("id"));
											if (cidadeList != null && cidadeList.size() > 0) {
												municipio.put("filhos", cidadeList);
											}
										}
										estado.put("filhos", municipioList);
									}
								}
								pais.put("filhos", estadoList);
							}
						}
					}
					// transformar o resultado em json
					String json = null;
					if (paisList != null) {
						ObjectMapper objectMapper = new ObjectMapper();
						try {
							json = objectMapper.writeValueAsString(paisList);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
					}
					pessoaGrupoJson = json;
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("json de localidades construido em %d milisegundos", System.currentTimeMillis() - tempo));
					}
					if (logger.isTraceEnabled()) {
						logger.trace(String.format("json de localidades [%s]", getPessoaGrupoJson()));
					}
				}
			}
		}
		return pessoaGrupoJson;
	}

	@Override
	@Transactional(readOnly = true)
	public PessoaGrupoTipo getPessoaGrupoTipo(PessoaGrupoTipo.Codigo codigo) {
		return pessoaGrupoTipoDao.restore(new PessoaGrupoTipo(codigo)).get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public RelacionamentoTipo getRelacionamentoTipo(RelacionamentoTipo.Codigo codigo) {
		RelacionamentoTipo relacionamentoTipo = new RelacionamentoTipo(codigo);
		relacionamentoTipo.setTemporario(null);
		relacionamentoTipo.setGeradoPeloSistema(null);
		return relacionamentoTipoDao.restore(relacionamentoTipo).get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public String getServletLocalDirectoryPath(HttpServletRequest request) {
		if (servletLocalDirectoryPath == null) {
			StringBuffer path = new StringBuffer(request.getServletContext().getRealPath("/"));
			if (!path.toString().endsWith(File.separator)) {
				path.append(File.separator);
			}
			servletLocalDirectoryPath = path.toString();
		}
		return servletLocalDirectoryPath;
	}

	@Override
	@Transactional(readOnly = true)
	public String getServletResourcesLocalDirectoryPath(HttpServletRequest request) {
		if (servletResourcesLocalDirectoryPath == null) {
			StringBuffer path = new StringBuffer(getServletLocalDirectoryPath(request));
			if (!path.toString().endsWith(File.separator)) {
				path.append(File.separator);
			}
			path.append("resources").append(File.separator).append("upload").append(File.separator);

			File teste = new File(path.toString());
			if (!teste.exists()) {
				teste.mkdirs();
			}
			if (!teste.isDirectory()) {
				throw new IllegalStateException("Estrutura de upload invalida");
			}

			servletResourcesLocalDirectoryPath = path.toString();
		}
		return servletResourcesLocalDirectoryPath;
	}

}