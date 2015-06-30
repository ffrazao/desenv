<div class="row">
	<div class="form-group col-md-12">
		<label class="control-label">Arquivos</label>
		<ng-include src="'views/pessoa/sub-arquivo.html'" />
	</div>
</div>

<!-- 
<div class="container">
								<div class="tab-pane fade" id="arquivos">
								<div class="container col-md-12">
									<div class="visible-md visible-lg">
											< !--  	<galeria fonte="pessoa"></galeria> -- >
											<br> <br>
											<div class="table-responsive col-md-12">
												<div class="panel panel-default">
												<div class="panel-heading">
												<h4 class="panel-title">Arquivos</h4>
														<div style="float: right;">
														<upload fonte="pessoa"></upload>
														<meus-arquivos fonte="pessoa"></meus-arquivos>
														</div>
															<br> <br>
															<table class="table table-striped">
														
														<tr data-ng-repeat="arquivo in arquivos">
														
														< !-- td><a target="_blank"	id="{{arquivo.arquivo.id}}" href="./descer?arq={arquivo.arquivo.md5}}{{arquivo.arquivo.extensao}}">{{arquivo.arquivo.nome}}</a></td-- >
														<td><a target="_blank"	id="{{arquivo.arquivo.id}}" href="./resources/upload/{{arquivo.arquivo.md5}}{{arquivo.arquivo.extensao}}">{{arquivo.arquivo.nome}}</a></td>
														<td><button type="button" data-placement="top"  data-original-title="Remover" data-ng-click="removerArquivo(arquivo)" class="btn btn-danger btn-xs ttip"><span class="glyphicon glyphicon-trash ttip"></span></button></td>
															</tr>		
													</table>								
											</div>
											</div>
											</div>
											
										<!-- 	<div class="col-md-6">
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
									</div> -->
											<!-- div id="listagemArquivos" class="row col-md-6">
													
													<div id="meus-arquivos">
														<span data-ng-repeat="arquivo in arquivos">
														
							                                <span data-ng-if="arquivos.length <= 0">Sem arquivos</span>
							                                
							                                <div data-ng-if="arquivo.arquivo.extensao == '.jpg' || arquivo.arquivo.extensao == '.png' || arquivo.arquivo.extensao == '.bmp' || arquivo.arquivo.extensao == '.gif'">
							                                    <div class="col-md-2 meus-arquivos-grade-miniatura arquivo">

							                                    </div>
							                                </div>
							
							                                <div data-ng-if="arquivo.arquivo.extensao != '.jpg' && arquivo.arquivo.extensao != '.png' && arquivo.arquivo.extensao != '.bmp' && arquivo.arquivo.extensao != '.gif'">
							                                    <div class="col-md-2 meus-arquivos-grade-miniatura arquivo">

							                                    </div>
							                                </div>
							
							                            </span>
													</div>
												</div-- >
											
										</div>
									</div>
								<br> <br> <br>

							</div>

</div>
 -->
