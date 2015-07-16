package br.gov.emater.df.cadastro;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.gov.emater.df.cadastro.util.StringFunc;

public class Main {

	public static void main(String[] args) throws Exception {
		new Main().executar();
	}

	private Connection destinoConnection;

	private PreparedStatement inserePessoa;

	private PreparedStatement inserePessoaPropriedade;

	private PreparedStatement inserePropriedade;

	private PreparedStatement pesqPessoaChave;

	private PreparedStatement pesqPropriedadeChave;

	private PreparedStatement pessoaPropPesq;

	public Main() throws Exception {
		destinoConnection = ConexaoMysql.getInstance().createConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `cad_geral`.`cad_pessoa`").append("\n");
		sql.append("   (`chave_externa`, `chave`, `nome`, `cpf`, `escolaridade`, `sexo`, `nascimento`, `reside_propriedade`, `caixa_postal`, `endereco`, `bairro`,").append("\n");
		sql.append("    `cidade`, `uf`, `cep`, `telefone_1`, `telefone_2`, `email`, `numero_inscricao_sef_df`, `numero_dap`, `data_informacoes`,`origem_id`, ").append("\n");
		sql.append("    `status`)").append("\n");
		sql.append("VALUES").append("\n");
		sql.append("   (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").append("\n");
		inserePessoa = destinoConnection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

		sql = new StringBuffer();
		sql.append("INSERT INTO `cad_geral`.`cad_propriedade`").append("\n");
		sql.append("(`chave_externa`, `nome`, `cnpj`, `razao_social`, `caixa_postal`, `endereco`, `bairro`, `cep`, `cidade`,").append("\n");
		sql.append("`uf`, `geo_referenciamento_s`, `geo_referenciamento_w`, `telefone_1`, `telefone_2`, `inicio_atividade`,").append("\n");
		sql.append("`area_propriedade`, `numero_empregado_fixo`, `situacao_fundiaria`, `situacao_fundiaria_outro`,").append("\n");
		sql.append("`arrendatario`, `tem_plano_utilizacao`, `tem_reserva_legal`, `tem_app`, `origem_id`, `status`)").append("\n");
		sql.append("VALUES").append("\n");
		sql.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").append("\n");
		inserePropriedade = destinoConnection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

		sql = new StringBuffer();
		sql.append("INSERT INTO `cad_geral`.`cad_pessoa_propriedade`").append("\n");
		sql.append("(`cad_pessoa_id`, `cad_propriedade_id`)").append("\n");
		sql.append("VALUES").append("\n");
		sql.append("(?, ?)").append("\n");
		inserePessoaPropriedade = destinoConnection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

		sql = new StringBuffer();
		sql.append("select id").append("\n");
		sql.append("from   cad_geral.cad_pessoa").append("\n");
		sql.append("where  chave = ?").append("\n");

		pessoaPropPesq = destinoConnection.prepareStatement("select 1 from cad_geral.cad_pessoa_propriedade where cad_pessoa_id = ? and cad_propriedade_id = ?");

		pesqPessoaChave = destinoConnection.prepareStatement("select id from cad_geral.cad_pessoa where chave = ?");

		pesqPropriedadeChave = destinoConnection.prepareStatement("select id from cad_geral.cad_propriedade where chave_externa = ?");

	}

	private String criticaEmail(String email) {
		if (email != null && email.contains("@")) {
			return email;
		}
		return null;
	}

	private String deParaArrendatario(String arrendatario) {
		return "Arrendamento".equals(arrendatario) ? "S" : "N";
	}

	private String deParaEscolaridade(String escolaridade) {
		// AI Não sabe ler / escrever (Analfabeto)
		// AC Sabe ler / escrever (Alfabetizado)
		// FI Fundamental Incompleto
		// FC Fundamental Completo
		// MI Médio Incompleto
		// MC Médio Completo
		// SI Superior Incompleto
		// SC Superior Completo
		// ES Especialização/ Residência
		// ME Mestrado
		// DO Doutorado
		// PD Pós-Doutorado
		if (escolaridade == null) {
			return null;
		}

		switch (escolaridade) {
		case "Analfabeto":
			return "AI";
		case "Ensino fundamental":
		case "Primário completo":
		case "Fundamental completo":
			return "FC";
		case "Primário incompleto":
		case "Fundamental incompleto":
			return "FI";
		case "2º Grau":
		case "Medio completo":
		case "Médio completo":
			return "MC";
		case "Médio incompleto":
			return "MI";
		case "Superior completo":
			return "SC";
		case "Superior incompleto":
			return "SI";
		default:
			return null;
		}
	}

	private int deParaOrigem(String string) {
		return 1;
	}

	private Integer deParaPropriedadeChave(String idPrp) throws SQLException {
		pesqPropriedadeChave.setString(1, idPrp);

		ResultSet rs = pesqPropriedadeChave.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	private String deParaResidePropriedade(String residePropriedade) {
		return "Propriedade".equals(residePropriedade) ? "S" : "N";
	}

	private String deParaSexo(String sexo) {
		if (sexo == null)
			return null;
		switch (sexo) {
		case "Feminino":
			return "F";
		case "Masculino":
			return "M";
		default:
			return null;
		}
	}

	private String deParaSituacaoFundiaria(String sit) {

		if (sit == null) {
			return null;
		}

		switch (sit) {
		case "Arrendatário":
			return "A";
		case "Concessão de uso":
			return "C";
		case "Escritura definitiva":
			return "R";
		case "Posse":
			return "P";
		default:
			return null;
		}
	}

	private String deParaTemApp(String temApp) {
		try {
			return Integer.parseInt(temApp) > 0 ? "S" : "N";
		} catch (Exception e) {
			return null;
		}
	}

	private String deParaTemReservaLegal(String temReservaLegal) {
		try {
			return Integer.parseInt(temReservaLegal) > 0 ? "S" : "N";
		} catch (Exception e) {
			return null;
		}
	}

	private void executar() throws Exception {
		final String bases[] = {
				// "ASBRA", "ASCRI", "ASFOR", "ASPBE", "ASPIP", "ASSOB",
				"ELALG", "ELBRA", "ELBSB", "ELCEI", "ELGAM", "ELJAR", "ELPAD", "ELPAR", "ELPIP", "ELPLA", "ELRIP", "ELRSS", "ELSOB", "ELTAB", "ELTAQ", "ELVAR", };

		StringBuilder benefSql = new StringBuilder();
		benefSql.append("SELECT ").append("\n");
		benefSql.append("       substring(benef.bfnome from 1 for 45) AS nome,").append("\n");
		benefSql.append("       benef.bfcpf AS cpf,").append("\n");
		benefSql.append("       benef.bfins AS escolaridade,").append("\n");
		benefSql.append("       benef.bfgenero AS sexo,").append("\n");
		benefSql.append("       benef.bfdtnasc AS nascimento,").append("\n");
		benefSql.append("       benef.bfmoradia AS residePropriedade,").append("\n");
		benefSql.append("       NULL AS caixaPostal,").append("\n");
		benefSql.append("       substring(benef.BFENDERECO1 from 1 for 65) AS endereco,").append("\n");
		benefSql.append("       com.comunidade AS bairro,").append("\n");
		benefSql.append("       com.regiao AS cidade,").append("\n");
		benefSql.append("       'DF' AS uf,").append("\n");
		benefSql.append("       benef.bfcep1 AS cep,").append("\n");
		benefSql.append("       benef.bftelef AS telefone1,").append("\n");
		benefSql.append("       benef.bfcelular AS telefone2,").append("\n");
		benefSql.append("       benef.bfemail AS email,").append("\n");
		benefSql.append("       NULL AS numeroInscricaoSefDf,").append("\n");
		benefSql.append("       benef.bfdapnr AS numeroDap,").append("\n");
		benefSql.append("       TIMESTAMP 'NOW' AS dataInformacoes,").append("\n");
		benefSql.append("       'EMATER DF'       AS origemId,").append("\n");
		benefSql.append("       'V'       AS status,").append("\n");
		benefSql.append("       substring(p.ppnome from 1 for 45) as p_nome,").append("\n");
		benefSql.append("       null as p_cnpj,").append("\n");
		benefSql.append("       null as p_razaoSocial,").append("\n");
		benefSql.append("       null as p_caixaPostal,").append("\n");
		benefSql.append("       substring(p.ppend from 1 for 45) as p_endereco,").append("\n");
		benefSql.append("       p_com.comunidade as p_bairro,").append("\n");
		benefSql.append("       p.ppcep as p_cep,").append("\n");
		benefSql.append("       p_com.regiao as p_cidade,").append("\n");
		benefSql.append("       'DF' as p_uf,").append("\n");
		benefSql.append("       substring(p.pputmn from 1 for 11) as p_geoReferenciamentoS,").append("\n");
		benefSql.append("       substring(p.pputme from 1 for 11) as p_geoReferenciamentoW,").append("\n");
		benefSql.append("       null as p_telefone1,").append("\n");
		benefSql.append("       null as p_telefone2,").append("\n");
		benefSql.append("       benef.bfativtrad as p_inicioAtividade,").append("\n");
		benefSql.append("       b_prop.exparea as p_areaPropriedade,").append("\n"); // p.ppatot
		benefSql.append("       p.pppemp as p_numeroEmpregadoFixo,").append("\n");
		benefSql.append("       p.ppfund as p_situacaoFundiaria,").append("\n");
		benefSql.append("       null as p_situacaoFundiariaOutro,").append("\n");
		benefSql.append("       b_prop.expreg as p_arrendatario,").append("\n");
		benefSql.append("       null as p_temPlanoUtilizacao,").append("\n");
		benefSql.append("       p.ppaleg as p_temReservaLegal,").append("\n");
		benefSql.append("       p.ppapre as p_temApp,").append("\n");
		benefSql.append("       'EMATER DF' AS p_origemId,").append("\n");
		benefSql.append("       'V' as p_status,").append("\n");

		benefSql.append("       p.idprp as p_chaveExterna,").append("\n");
		benefSql.append("       benef.idben as chaveExterna").append("\n");

		benefSql.append("FROM benef00 benef").append("\n");
		benefSql.append("JOIN com00 com ON com.idcom = benef.idcom").append("\n");
		benefSql.append("JOIN benef01 b_prop ON b_prop.idben = benef.idben").append("\n");
		benefSql.append("JOIN prop00 p ON p.idprp = b_prop.idprp").append("\n");
		benefSql.append("JOIN com00 p_com ON p_com.idcom = p.idcom").append("\n");
		benefSql.append("WHERE benef.bfcategoria = 'Empreendedor'").append("\n");
		benefSql.append("AND   benef.bfcpf != ''").append("\n");

		benefSql.append("AND   p.idprp not in(SELECT IDPRP FROM PROP00 WHERE IDPRP BETWEEN '10000416' AND '10000490')").append("\n");

		benefSql.append("ORDER BY p.idprp,").append("\n");
		benefSql.append("         benef.idben").append("\n");

		destinoConnection.setAutoCommit(false);

		int ct = 0;
		for (String base : bases) {
			try {
				try (Connection c = ConexaoFirebird.getInstance().createConnection(base)) {
					try (PreparedStatement ps = c.prepareStatement(benefSql.toString())) {
						try (ResultSet rs = ps.executeQuery()) {
							boolean continuar = true;
							while (continuar) {
								try {
									continuar = rs.next();
								} catch (org.firebirdsql.jdbc.FBSQLException ex) {
									ex.printStackTrace();
									// continuar = rs.relative(2);
								}

								if (continuar) {
									System.out.format("%d. [%s][%s]\n", ++ct, rs.getString("p_chaveExterna"), base);
									Integer pessoaId = salvarPessoa(rs);
									if (pessoaId == null) {
										continue;
									}
									Integer propriedadeId = salvarPropriedade(rs, base);
									salvarPropriedadePessoa(pessoaId, propriedadeId);
								}
							}
						}
					}
				}
				destinoConnection.commit();
				System.out.println("Salvo com sucesso");
			} catch (Exception e) {
				System.out.println("Erro no processamento");
				destinoConnection.rollback();
				e.printStackTrace();
			}

		}
		System.out.println("Fim do processo!");
	}

	private String formataCep(String cep) {
		if (cep == null || cep.trim().length() == 0)
			return null;
		cep = StringFunc.getInstance().limpaNumero(cep);
		if (cep.length() == 8) {
			return String.format("%s-%s", cep.substring(0, 5), cep.substring(5, 8));
		}
		return null;

		// andrea 14:30 quarta
	}

	private Integer pesqPessoaChave(String chave) throws SQLException {
		pesqPessoaChave.setString(1, chave);
		try (ResultSet rs = pesqPessoaChave.executeQuery();) {
			return rs.next() ? rs.getInt(1) : null;
		}
	}

	private Integer salvarPessoa(ResultSet rs) throws Exception {
		Integer result = null;

		String idBen = rs.getString("chaveExterna");
		String nome = rs.getString("nome");
		String cpf = rs.getString("cpf");
		String escolaridade = rs.getString("escolaridade");
		String sexo = rs.getString("sexo");
		String nascimento = rs.getString("nascimento");
		String residePropriedade = rs.getString("residePropriedade");
		String caixaPostal = rs.getString("caixaPostal");
		String endereco = rs.getString("endereco");
		String bairro = rs.getString("bairro");
		String cidade = rs.getString("cidade");
		String uf = rs.getString("uf");
		String cep = rs.getString("cep");
		String telefone1 = rs.getString("telefone1");
		String telefone2 = rs.getString("telefone2");
		String email = rs.getString("email");
		String numeroInscricaoSefDf = rs.getString("numeroInscricaoSefDf");
		String numeroDap = rs.getString("numeroDap");
		String dataInformacoes = rs.getString("dataInformacoes");
		String origemId = rs.getString("origemId");
		String status = rs.getString("status");

		String chave = StringFunc.getInstance().limpaNumero(cpf);
		if (chave != null) {
			chave = chave.length() <= 11 ? StringFunc.getInstance().criticaCPF(chave) : StringFunc.getInstance().criticaCNPJ(chave);
		}
		if (chave != null && chave.trim().length() > 14) {
			chave = null;
		}
		if (chave == null) {
			System.out.format("registro ignorado %s - %s\n", cpf, nome);
			return null;
		}

		result = pesqPessoaChave(chave);
		if (result != null) {
			System.out.format("pessoa recuperada %d\n", result);
			return result;
		}
		cpf = chave;

		inserePessoa.setString(1, idBen); // chave_externa,
		inserePessoa.setString(2, chave); // chave,
		inserePessoa.setString(3, nome); // nome,
		inserePessoa.setString(4, cpf); // cpf,
		inserePessoa.setString(5, deParaEscolaridade(escolaridade)); // escolaridade,
		inserePessoa.setString(6, deParaSexo(sexo)); // sexo,
		inserePessoa.setDate(7, StringFunc.getInstance().strToData(nascimento)); // nascimento,
		inserePessoa.setString(8, deParaResidePropriedade(residePropriedade)); // reside_propriedade,
		inserePessoa.setString(9, caixaPostal); // caixa_postal,
		inserePessoa.setString(10, endereco); // endereco,
		inserePessoa.setString(11, bairro); // bairro,
		inserePessoa.setString(12, cidade); // cidade,
		inserePessoa.setString(13, uf); // uf,
		inserePessoa.setString(14, formataCep(cep)); // cep,
		inserePessoa.setString(15, telefone1); // telefone_1,
		inserePessoa.setString(16, telefone2); // telefone_2,
		inserePessoa.setString(17, criticaEmail(email)); // email,
		inserePessoa.setString(18, numeroInscricaoSefDf); // numero_inscricao_sef_df,
		inserePessoa.setString(19, numeroDap); // numero_dap,
		inserePessoa.setTimestamp(20, StringFunc.getInstance().strToDataHora(dataInformacoes)); // data_informacoes,
		inserePessoa.setInt(21, deParaOrigem(origemId)); // origem_id,
		inserePessoa.setString(22, status); // status,

		inserePessoa.executeUpdate();

		ResultSet rsPk = inserePessoa.getGeneratedKeys();
		if (rsPk.next()) {
			result = rsPk.getInt(1);
		}

		System.out.format("pessoa inserida %d\n", result);

		return result;
	}

	private Integer salvarPropriedade(ResultSet rs, String base) throws Exception {
		Integer result = null;

		String chaveExterna = base + rs.getString("p_chaveExterna");
		String nome = rs.getString("p_nome");
		String cnpj = rs.getString("p_cnpj");
		String razaoSocial = rs.getString("p_razaoSocial");
		String caixaPostal = rs.getString("p_caixaPostal");
		String endereco = rs.getString("p_endereco");
		String bairro = rs.getString("p_bairro");
		String cep = rs.getString("p_cep");
		String cidade = rs.getString("p_cidade");
		String uf = rs.getString("p_uf");
		String geoReferenciamentoS = rs.getString("p_geoReferenciamentoS");
		String geoReferenciamentoW = rs.getString("p_geoReferenciamentoW");
		String telefone1 = rs.getString("p_telefone1");
		String telefone2 = rs.getString("p_telefone2");
		String inicioAtividade = rs.getString("p_inicioAtividade");
		String areaPropriedade = rs.getString("p_areaPropriedade");
		String numeroEmpregadoFixo = rs.getString("p_numeroEmpregadoFixo");
		String situacaoFundiaria = rs.getString("p_situacaoFundiaria");
		String situacaoFundiariaOutro = rs.getString("p_situacaoFundiariaOutro");
		String arrendatario = rs.getString("p_arrendatario");
		String temPlanoUtilizacao = rs.getString("p_temPlanoUtilizacao");
		String temReservaLegal = rs.getString("p_temReservaLegal");
		String temApp = rs.getString("p_temApp");
		String origemId = rs.getString("p_origemId");
		String status = rs.getString("p_status");

		result = deParaPropriedadeChave(chaveExterna);
		if (result != null) {
			System.out.format("propriedade recuperada %d\n", result);
			return result;
		}

		inserePropriedade.setString(1, chaveExterna);
		inserePropriedade.setString(2, nome);
		inserePropriedade.setString(3, cnpj);
		inserePropriedade.setString(4, razaoSocial);
		inserePropriedade.setString(5, caixaPostal);
		inserePropriedade.setString(6, endereco);
		inserePropriedade.setString(7, bairro);
		inserePropriedade.setString(8, formataCep(cep));
		inserePropriedade.setString(9, cidade);
		inserePropriedade.setString(10, uf);
		try {
			inserePropriedade.setBigDecimal(11, StringFunc.getInstance().stroToBigDecimal(geoReferenciamentoS));
		} catch (Exception e) {
			inserePropriedade.setBigDecimal(11, null);
		}
		try {
			inserePropriedade.setBigDecimal(12, StringFunc.getInstance().stroToBigDecimal(geoReferenciamentoW));
		} catch (Exception e) {
			inserePropriedade.setBigDecimal(12, null);
		}
		inserePropriedade.setString(13, telefone1);
		inserePropriedade.setString(14, telefone2);
		try {
			Date data = StringFunc.getInstance().strToData(inicioAtividade + "-01-01");
			if (data.after(new Date(new java.util.Date().getTime()))) {
				data = null;
			}
			inserePropriedade.setDate(15, data);
		} catch (Exception e) {
			inserePropriedade.setDate(15, null);
		}
		inserePropriedade.setString(16, areaPropriedade);
		inserePropriedade.setString(17, numeroEmpregadoFixo);
		inserePropriedade.setString(18, deParaSituacaoFundiaria(situacaoFundiaria));
		inserePropriedade.setString(19, situacaoFundiariaOutro);
		inserePropriedade.setString(20, deParaArrendatario(arrendatario));
		inserePropriedade.setString(21, temPlanoUtilizacao);
		inserePropriedade.setString(22, deParaTemReservaLegal(temReservaLegal));
		inserePropriedade.setString(23, deParaTemApp(temApp));
		inserePropriedade.setInt(24, deParaOrigem(origemId));
		inserePropriedade.setString(25, status);

		inserePropriedade.executeUpdate();

		ResultSet rsPk = inserePropriedade.getGeneratedKeys();
		if (rsPk.next()) {
			result = rsPk.getInt(1);
		}

		System.out.format("propriedade inserida %d\n", result);

		return result;
	}

	private void salvarPropriedadePessoa(Integer pessoaId, Integer propriedadeId) throws Exception {
		pessoaPropPesq.setInt(1, pessoaId);
		pessoaPropPesq.setInt(2, propriedadeId);
		ResultSet pessoaPropPesqRs = pessoaPropPesq.executeQuery();
		if (!pessoaPropPesqRs.next()) {
			inserePessoaPropriedade.setInt(1, pessoaId);
			inserePessoaPropriedade.setInt(2, propriedadeId);
			System.out.format("vinculo %d, %d. %d\n", inserePessoaPropriedade.executeUpdate(), pessoaId, propriedadeId);
		}

	}

}
