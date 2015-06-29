<!-- Frazão Inicio Grupo Social -->
<div class="container">


							<div class="tab-pane fade" id="documentos"
								data-ng-show="registro.pessoaTipo === 'PF'">
								<div class="container row">
									<br>
									<div class="col-md-6">
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">NIS - Número de Identificação
													Social</h4>
											</div>
											<div class="panel-body">
												<div class="row">
													<div class="form-group col-md-6">
														<label for="nisNumero" class="control-label">Número</label>
														<input class="form-control nis"
															data-ng-model="registro.nisNumero" id="nisNumero"
															name="nisNumero" placeholder="Número" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="col-md-6">
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">RG - Registro Geral</h4>
											</div>
											<div class="panel-body">
												<div class="row">
													<div class="form-group col-md-4">
														<label for="rgNumero" class="control-label">Registro</label>
														<input class="form-control"
															data-ng-model="registro.rgNumero" id="rgNumero"
															name="rgNumero" placeholder="Registro" />
													</div>
													<div class="form-group col-md-4">
														<label for="rgOrgaoEmissor" class="control-label">Órgão
															Emissor</label> <input class="form-control numero"
															data-ng-model="registro.rgOrgaoEmissor"
															id="rgOrgaoEmissor" name="rgOrgaoEmissor"
															placeholder="Órgão Emissor" />
													</div>
													<!-- Jean problemas aqui tb
                                        <div class="form-group col-md-3">
                                            <label for="rgLocalizacao" class="control-label">Local de Emissão</label>
                                            <input class="form-control" id="rgLocalizacao" name="rgLocalizacao" placeholder="Local de Emissão" />
                                        </div>
 -->
													<div class="form-group col-md-4">
														<label for="rgDataEmissao" class="control-label">Data
															de Emissão</label> <input class="form-control data"
															data-ng-model="registro.rgDataEmissao" id="rgDataEmissao"
															name="rgDataEmissao" placeholder="Data de Emissão" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="col-md-6">
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">Título de Eleitor</h4>
											</div>
											<div class="panel-body">
												<div class="row">
													<div class="form-group col-md-4">
														<label for="tituloNumero" class="control-label">Número</label>
														<input class="form-control numero"
															data-ng-model="registro.tituloNumero" id="tituloNumero"
															name="tituloNumero" placeholder="Número" />
													</div>
													<div class="form-group col-md-4">
														<label for="tituloSecao" class="control-label">Seção</label>
														<input class="form-control numero"
															data-ng-model="registro.tituloSecao" id="tituloSecao"
															name="tituloSecao" placeholder="Seção" />
													</div>
													<div class="form-group col-md-4">
														<label for="tituloZona" class="control-label">Zona</label>
														<input class="form-control"
															data-ng-model="registro.tituloZona" id="tituloZona"
															name="tituloZona" placeholder="Zona" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="col-md-6">
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">CNH - Carteira Nacional de
													Habilitação</h4>
											</div>
											<div class="panel-body">
												<div class="row">
													<div class="form-group col-md-3">
														<label for="cnhCategoria" class="control-label">Categoria</label>
														<select class="form-control" data-ng-model="registro.cnhCategoria" id="cnhCategoria" name="cnhCategoria" 
															data-ng-options="item.codigo as item.descricao for item in apoio.cnhCategoriaList">
															<option value="">*** Não Informado ***</option>
														</select>
													</div>
													<div class="form-group col-md-3">
														<label for="cnhNumero" class="control-label">Número</label>
														<input class="form-control"
															data-ng-model="registro.cnhNumero" id="cnhNumero"
															name="cnhNumero" placeholder="Número" />
													</div>
													<div class="form-group col-md-3">
														<label for="cnhPrimeiraHabilitacao" class="control-label">1ª Habilitação</label> <input class="form-control data"
															data-ng-model="registro.cnhPrimeiraHabilitacao"
															id="cnhPrimeiraHabilitacao" name="cnhPrimeiraHabilitacao"
															placeholder="00/00/0000" />
													</div>
													<div class="form-group col-md-3">
														<label for="cnhValidade" class="control-label">Validade</label>
														<input class="form-control data"
															data-ng-model="registro.cnhValidade" id="cnhValidade"
															name="cnhValidade" placeholder="00/00/0000" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="col-md-6">
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">Certidão de Casamento</h4>
											</div>
											<div class="panel-body">
												<div class="row">
													<div class="form-group col-md-3">
														<label for="certidaoCasamentoCartorio"
															class="control-label">Cartório</label> <input
															class="form-control"
															data-ng-model="registro.certidaoCasamentoCartorio"
															id="certidaoCasamentoCartorio"
															name="certidaoCasamentoCartorio" placeholder="Cartório" />
													</div>
													<div class="form-group col-md-3">
														<label for="certidaoCasamentoFolha" class="control-label">Folha</label>
														<input class="form-control numero"
															data-ng-model="registro.certidaoCasamentoFolha"
															id="certidaoCasamentoFolha" name="certidaoCasamentoFolha"
															placeholder="Folha" />
													</div>
													<div class="form-group col-md-3">
														<label for="certidaoCasamentoLivro" class="control-label">Livro</label>
														<input class="form-control numero"
															data-ng-model="registro.certidaoCasamentoLivro"
															id="certidaoCasamentoLivro" name="certidaoCasamentoLivro"
															placeholder="Livro" />
													</div>
													<div class="form-group col-md-3">
														<label for="certidaoCasamentoRegime" class="control-label">Regime</label>
														<select class="form-control" data-ng-model="registro.certidaoCasamentoRegime" id="certidaoCasamentoRegime" name="certidaoCasamentoRegime" 
															data-ng-options="item.codigo as item.descricao for item in apoio.regimeCasamentoList">
															<option value="">*** Não Informado ***</option>
														</select>
													</div>
												</div>

											</div>
										</div>
									</div>

									<div class="col-md-6">
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">CAM - Certidão de Alistamento
													Militar</h4>
											</div>
											<div class="panel-body">
												<div class="row">
													<div class="form-group col-md-3">
														<label for="camNumero" class="control-label">Número</label>
														<input class="form-control numero"
															data-ng-model="registro.camNumero" id="camNumero"
															name="camNumero" placeholder="Número" />
													</div>
													<div class="form-group col-md-3">
														<label for="camOrgao" class="control-label">Órgão</label>
														<select class="form-control" data-ng-model="registro.camOrgao" id="camOrgao" name="camOrgao" 
															data-ng-options="item.codigo as item.descricao for item in apoio.camOrgaoList">
															<option value="">*** Não Informado ***</option>
														</select>
													</div>
													<div class="form-group col-md-2">
														<label for="camSerie" class="control-label">Série</label>
														<input class="form-control numero"
															data-ng-model="registro.camSerie" id="camSerie"
															name="camSerie" placeholder="Série" />
													</div>
													<div class="form-group col-md-4">
														<label for="camUnidadeMilitar" class="control-label">Unidade
															Militar</label> <input class="form-control"
															data-ng-model="registro.camUnidadeMilitar"
															id="camUnidadeMilitar" name="camUnidadeMilitar"
															placeholder="Unidade Militar" />
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

</div>
<!-- Frazão Fim Grupo Social -->
