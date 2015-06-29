SET FOREIGN_KEY_CHECKS = 0;
set sql_safe_updates = 0;

delete from ater.publico_alvo_setor;
delete from ater.publico_alvo;
delete from ater.propriedade_rural_arquivo;
delete from ater.benfeitoria;
delete from ater.propriedade_rural_localizacao;
delete from ater.propriedade_rural_arquivo;
delete from ater.propriedade_rural;
delete from ater.exploracao;
delete from pessoa.pessoa_meio_contato;
delete from pessoa.meio_contato_endereco;
delete from pessoa.pessoa_arquivo;
delete from pessoa.pessoa_relacionamento;
delete from pessoa.meio_contato_endereco;
delete from pessoa.meio_contato_email;
delete from pessoa.meio_contato_telefonico;
delete from pessoa.meio_contato;
update pessoa.pessoa_grupo set pessoa_grupo_id = null;
delete from pessoa.pessoa_grupo;
delete from pessoa.pessoa_juridica;
delete from pessoa.pessoa_fisica;
delete from sistema.usuario_modulo;
delete from sistema.usuario_perfil;
delete from sistema.usuario;
delete from pessoa.pessoa;

delete from pessoa.localizacao_agrupamento;
delete from pessoa.localizacao_grupo;
update pessoa.localizacao set localizacao_id = null;
delete from pessoa.localizacao;

truncate ater.publico_alvo_setor;
truncate ater.publico_alvo;
truncate ater.propriedade_rural_arquivo;
truncate ater.benfeitoria;
truncate ater.propriedade_rural_localizacao;
truncate ater.propriedade_rural_arquivo;
truncate ater.propriedade_rural;
truncate ater.exploracao;
truncate pessoa.pessoa_meio_contato;
truncate pessoa.meio_contato_endereco;
truncate pessoa.pessoa_arquivo;
truncate pessoa.pessoa_relacionamento;
truncate pessoa.meio_contato_endereco;
truncate pessoa.meio_contato_email;
truncate pessoa.meio_contato_telefonico;
truncate pessoa.meio_contato;
truncate pessoa.pessoa_grupo;
truncate pessoa.pessoa_juridica;
truncate pessoa.pessoa_fisica;
truncate sistema.usuario_modulo;
truncate sistema.usuario_perfil;
truncate sistema.usuario;
truncate pessoa.pessoa;

truncate pessoa.localizacao_grupo;
truncate pessoa.localizacao_agrupamento;
truncate pessoa.localizacao;

insert into pessoa.pessoa (id, pessoa_tipo, nome, apelido_sigla, publico_alvo, observacoes, situacao)
values (1, "PJ", "Empresa de Assistência Técnica e Extensão Rural do Distrito Federal", "EMATER-DF", "N", null, "A");

insert into pessoa.pessoa_juridica (id, cnpj, inscricao_estadual) values (1, "00.509.612/0001-04", null);

insert into sistema.usuario (id, pessoa_id, nome_usuario, senha, status_conta, acesso_expira_em)
values (1, 1, "a", "efaa153b0f682ae5170a3184fa0df28c", "A", null);

set @var_perfil = (select id from sistema.perfil where nome = 'ADMIN');
insert into sistema.usuario_perfil (id, usuario_id, perfil_id)
values (1, 1, @var_perfil);

set @var_modulo = (select id from sistema.modulo where nome = "ATER web");
insert into sistema.usuario_modulo (id, usuario_id, modulo_id)
values (1, 1, @var_modulo);

set sql_safe_updates = 1;
SET FOREIGN_KEY_CHECKS = 1;