package gov.emater.aterweb.service.impl;

import static gov.emater.aterweb.comum.UtilitarioString.removeAspas;
import gov.emater.aterweb.dao.UtilDao;
import gov.emater.aterweb.service.ServiceException;
import gov.emater.aterweb.service.UtilService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilServiceImpl implements UtilService {

	private static final Logger logger = Logger.getLogger(UtilServiceImpl.class);

	@Autowired
	private UtilDao dao;

	/**
	 * M�todo para execu��o de queries livres no banco de dados
	 * 
	 * @param sql
	 *            a ser executada
	 * @param params
	 *            parametros da query
	 * @return resultado da query
	 */
	@Override
	@Transactional(readOnly = true)
	public List<?> executaQuery(String sql, List<?> params) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Executando query gen�rica [%s]", sql));
		}

		// ajustar a lista de par�metros
		if (params != null) {
			for (Object param : params) {
				if (param instanceof String) {
					String temp = removeAspas((String) param);
					if (temp.contains("|")) {
						String[] item = temp.split("\\|");
						String tipo = item[0];
						String valor = item[1];
						switch (tipo) {
						case "Long":
							param = Long.parseLong(valor);
							break;
						case "Integer":
							param = Integer.parseInt(valor);
							break;
						}
					}
				}
			}
		}

		return dao.executaQuery(sql, params);
	}

	/**
	 * M�todo gen�rico para retorno de entidades de dom�nio do sistema
	 * 
	 * @param entidade
	 *            nome da entidade a ser chamada
	 * @param nomeChavePrimaria
	 *            nome da chave prim�ria da entidade
	 * @param valorChavePrimaria
	 *            valor da chave prim�ria da entidade
	 * @return a rela��o das entidades em formato JSon
	 */
	@Override
	@Transactional(readOnly = true)
	public List<?> getDominio(String entidade, String nomeChavePrimaria, Integer valorChavePrimaria, String order, String[] fetchs) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Recuperando dom�nio para Entidade[%s], NomeChavePrimaria[%s], ValorChavePrim�ria[%d]", entidade, nomeChavePrimaria, valorChavePrimaria));
		}

		List<?> result = dao.getDominio(entidade, nomeChavePrimaria, valorChavePrimaria, order);

		fetch(result, entidade, fetchs);

		return result;
	}

	/**
	 * M�todo gen�rico para retorno de entidades de dom�nio do sistema
	 * 
	 * @param entidade
	 *            nome da entidade a ser chamada
	 * @param nomeChavePrimaria
	 *            nome da chave prim�ria da entidade
	 * @param valorChavePrimaria
	 *            valor da chave prim�ria da entidade
	 * @return a rela��o das entidades em formato JSon
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@Override
	@Transactional(readOnly = true)
	public List<?> getDominio(String entidade, String nomeChavePrimaria, String valorChavePrimaria, String order, String[] fetchs) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Recuperando dom�nio para Entidade[%s], NomeChavePrimaria[%s], ValorChavePrim�ria[%s]", entidade, nomeChavePrimaria, valorChavePrimaria));
		}

		List<?> result = dao.getDominio(entidade, nomeChavePrimaria, valorChavePrimaria, order);

		fetch(result, entidade, fetchs);

		return result;
	}

	private void fetch(List<?> result, String entidade, String[] fetchs) throws Exception {
		// verificar se deve fazer o fetch de algum resultado
		if (fetchs != null && result != null) {
			List<Method> metodos = new ArrayList<Method>();
			for (String campo : fetchs) {
				metodos.add(Class.forName(String.format("gov.emater.aterweb.model.%s", entidade)).getMethod(String.format("get%s%s", campo.substring(0, 1).toUpperCase(), campo.substring(1, campo.length()))));
			}
			for (Object registro : result) {
				for (Method fetch : metodos) {
					List<?> itens = (List<?>) fetch.invoke(registro);
					itens.size();
				}
			}
		}
	}

	/**
	 * M�todo gen�rico para retorno de Enumera��es do sistema (enum)
	 * 
	 * @param enumeracao
	 *            nome da Enumera��o a ser chamada
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getEnumeracao(String enumeracao) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Class<?> c = null;
		try {
			c = Class.forName(String.format("gov.emater.aterweb.model.domain.%s", enumeracao));
		} catch (ClassNotFoundException e) {
			c = Class.forName(enumeracao);
		}
		if (!c.isEnum()) {
			throw new ServiceException(String.format("% n�o � uma enumera��o v�lida!", enumeracao));
		}
		boolean emOrdem = false;
		for (Field campo : c.getDeclaredFields()) {
			if (campo.isEnumConstant()) {
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("codigo", ((Enum<?>) campo.get(c)).name());

				if (campo.getDeclaringClass().getDeclaredFields() != null) {
					for (Field subCampo : campo.getDeclaringClass().getDeclaredFields()) {
						if (!subCampo.isEnumConstant() && !"ENUM$VALUES".equals(subCampo.getName())) {
							subCampo.setAccessible(true);
							item.put(subCampo.getName(), subCampo.get(campo.get(c)));
							if (!emOrdem && "ordem".equals(subCampo.getName())) {
								emOrdem = true;
							}
						}
					}
				}
				result.add(item);
			}
		}
		if (emOrdem) {
			Collections.sort(result, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return ((Integer) o1.get("ordem")).compareTo(((Integer) o2.get("ordem")));
				}
			});
		}
		return result;
	}

}