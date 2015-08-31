package br.gov.df.emater.aterwebsrv.bo.teste;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.df.emater.aterwebsrv.dao.teste.TesteDao;

@Service
public class TesteCmd /*implements Command*/ {
	
	public TesteCmd() {
		System.out.println("novo TesteCmd");
	}

	@Autowired
	private TesteDao testeDao;

	@Transactional
	public void salvar(TesteCmd teste) {
		// testeDao.save(teste);
	}

	// @Transactional
	// public void apagarTudo() {
	// testeDao.deleteAll();
	// }

	// @Transactional(readOnly = true)
	// public Page<Teste> listarTudo() {
	// System.out.println(testeDao.contarPorNome("%5%"));
	// System.out.println(testeDao
	// .countByNomeContainingIgnoreCase("%5%"));
	//
	// return testeDao.findAll(new PageRequest(1, 20));
	// }
	//
	// @Transactional(readOnly = true)
	// public Teste listar(Long id) {
	// return testeDao.findOne(id);
	// }

	@Transactional(readOnly = true)
	public Authentication autenticaUsuario(Authentication autenticacao) {
		// TODO Auto-generated method stub
		String usuario = "usr", senha = "senha";
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		return new UsernamePasswordAuthenticationToken(usuario, senha, authorities);
	}

	// @Override
	@Transactional
	public boolean execute(Context context) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Ai..." + testeDao.count());
		return false;
	}

}