package gov.emater.aterweb.service.impl;

import gov.emater.aterweb.comum.UtilitarioImagem;
import gov.emater.aterweb.dao.ArquivoDao;
import gov.emater.aterweb.model.Arquivo;
import gov.emater.aterweb.mvc.dto.ArquivoCarregadoDto;
import gov.emater.aterweb.service.ArquivoService;
import gov.emater.aterweb.service.ConstantesService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class ArquivoServiceImpl extends CrudServiceImpl<Arquivo, Integer, ArquivoDao> implements ArquivoService {

	private static final Logger logger = Logger.getLogger(ArquivoServiceImpl.class);

	@Autowired
	private ConstantesService constantesService;

	@Autowired
	public ArquivoServiceImpl(ArquivoDao dao) {
		super(dao);
	}

	@Override
	@Transactional(readOnly = true)
	public void descer(HttpServletRequest request, HttpServletResponse response, @PathVariable String nomeArquivo) {
		try {
			List<Arquivo> arquivos = getDao().restore(new Arquivo(nomeArquivo));
			if (arquivos.size() == 0) {
				throw new FileNotFoundException(String.format("O arquivo [%s] não foi encontrado", nomeArquivo));
			} else if (arquivos.size() == 1) {
				Arquivo arquivo = arquivos.get(0);

				File arquivoLocalFile = new File(String.format("%s%s%s", constantesService.getServletResourcesLocalDirectoryPath(request), arquivo.getMd5(), arquivo.getExtensao()));

				// recuperar o arquivo no system file
				if (!arquivoLocalFile.exists()) {
					if (arquivo.getConteudo() == null || arquivo.getConteudo().length == 0) {
						throw new FileNotFoundException(String.format("Nao foi possivel recuperar o arquivo [%s]", arquivo.getNome()));
					}

					// recuperar o conteúdo do arquivo caso tenha sido apagado
					try (OutputStream out = new FileOutputStream(arquivoLocalFile)) {
						out.write(arquivo.getConteudo());
						out.flush();
					}
				}

				// ler o conteudo do arquivo
				byte[] bytes = new byte[arquivo.getTamanho()];
				try (InputStream in = new FileInputStream(arquivoLocalFile)) {
					in.read(bytes);
				}

				// Preparar a resposta
//				response.setHeader("Content-Disposition", String.format("attachment; filename=%s", arquivo.getNome()));
				response.setHeader("Content-Disposition", String.format("inline; filename=%s", arquivo.getNome()));
				response.setContentType(arquivo.getTipo());
				response.setContentLength(arquivo.getTamanho());
				response.getOutputStream().write(bytes);
				response.flushBuffer();

				arquivo = null;
			} else {
				throw new IllegalStateException(String.format("Mais de um arquivo encontrado com o mesmo nome [%s]", nomeArquivo));
			}
		} catch (Exception e) {
			new RuntimeException(e);
		}
	}

	@Override
	@Transactional
	public Map<String, Object> subir(MultipartHttpServletRequest request) {
		List<ArquivoCarregadoDto> result = new ArrayList<ArquivoCarregadoDto>();
		ArquivoCarregadoDto ufile = null;
		MultipartFile mpf = null;
		String arquivoUrl = null;
		String arquivoLocal = null;
		File arquivoLocalFile = null;
		Iterator<String> itr = request.getFileNames();
		try {
			while (itr.hasNext()) {
				mpf = request.getFile(itr.next());
				ufile = new ArquivoCarregadoDto(mpf.getOriginalFilename(), mpf.getBytes(), mpf.getContentType());

				arquivoUrl = String.format("%s/resources/upload/%s%s", constantesService.getBaseUrl(request), ufile.getMd5(), ufile.getFileExtension());

				arquivoLocal = String.format("%s%s%s", constantesService.getServletResourcesLocalDirectoryPath(request), ufile.getMd5(), ufile.getFileExtension());

				arquivoLocalFile = new File(arquivoLocal);

				if (logger.isDebugEnabled()) {
					logger.debug(String.format("carregando [%s]...", ufile));
				}

				// registrar arquivo no banco de dados
				Arquivo arquivo = new Arquivo();
				arquivo.setMd5(ufile.getMd5());
				List<Arquivo> arquivos = getDao().restore(arquivo);
				if (arquivos.size() == 0) {
					arquivo.setDataUpload(Calendar.getInstance());
					arquivo.setExtensao(ufile.getFileExtension());
					arquivo.setMd5(ufile.getMd5());
					arquivo.setNome(ufile.getName());
					arquivo.setTamanho(ufile.getSize());
					arquivo.setTipo(ufile.getType());
					arquivo.setConteudo(ufile.getBytes());
					arquivo = save(arquivo);
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("salvo no banco de dados [%s]...", ufile));
					}
					if (arquivoLocalFile.exists()) {
						arquivoLocalFile.delete();
					}
				} else if (arquivos.size() == 1) {
					arquivo = arquivos.get(0);
				} else if (arquivos.size() > 1) {
					throw new IllegalStateException("Mais de um arquivo registrado no banco de dados");
				}
				// gravar somente se o arquivo não existir
				if (!arquivoLocalFile.exists()) {
					try (OutputStream o = new FileOutputStream(arquivoLocal)) {
						o.write(ufile.getBytes());
						o.flush();
					}
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("gravado no sistema de arquivos [%s]...", ufile));
					}
				}

				if (logger.isDebugEnabled()) {
					logger.debug(String.format("arquivo carregado! [%s] [%d]", ufile, arquivo.getId()));
				}
				ufile.setUrl(arquivoUrl);

				switch (ufile.getFileExtension().toLowerCase()) {
				case ".jpg":
				case ".png":
				case ".gif":
				case ".jpeg":
					// gerar thumbnail de 120
					File thumbnail = UtilitarioImagem.redimensionar(arquivoLocalFile, 120);
					arquivoUrl = String.format("%s/resources/upload/%s", constantesService.getBaseUrl(request), thumbnail.getName());
				}

				ufile.setThumbnailUrl(arquivoUrl);
				ufile.setId(arquivo.getId());
				result.add(ufile);
			}
		} catch (IOException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("falha ao carregar arquivo! [%s] [%s]", ufile, e.getMessage()));
			}
			e.printStackTrace();
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("files", result);
		return map;
	}
}