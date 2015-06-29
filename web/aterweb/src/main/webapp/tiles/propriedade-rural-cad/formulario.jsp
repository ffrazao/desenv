<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="div_formulario">
    <div class="panel panel-success">
        <div class="panel-heading">
            <h3 class="panel-title">Formulário</h3>
        </div>
        <div class="panel-body">

            <script src="resources/js/libs/jquery.inputmask.js" charset="UTF-8"></script>
            <script src="resources/js/libs/jquery.mask.min.js" charset="UTF-8"></script>

            <span data-ng-if="linhasSelecionadas.length > 1">
                <!--div class="col-md-6"><a id="btn_ferramenta_registro_anterior" class="btn btn-default" data-ng-click="registroAnterior()">{{linhasSelecionadas[linhaAtual - 1].nomePropriedade}} &larr; Registro Anterior</a></div>
                <div class="col-md-6 text-right"><a id="btn_ferramenta_registro_proximo" class="btn btn-default" data-ng-click="registroProximo()">{{linhasSelecionadas[linhaAtual + 1].nomePropriedade}} Próximo Registro -></a></div-->
                <ul class="pager">
                    <li class="previous">
                        <a id="btn_ferramenta_registro_anterior" class="btn btn-default" data-ng-click="registroAnterior()">{{linhasSelecionadas[linhaAtual - 1].nomePropriedade}} <- Registro Anterior</a>
                    </li>

                    <li class="next">
                        <a id="btn_ferramenta_registro_proximo" class="btn btn-default" data-ng-click="registroProximo()">{{linhasSelecionadas[linhaAtual + 1].nomePropriedade}} Próximo Registro &rarr;</a>
                    </li>
                </ul>

                <br />
            </span>

            <!--div>

                <input type="text" data-ng-model="valor"/> <p/>

                Default currency {{valor| currency : "$"}} <p/>

                Real {{ valor | currency}}

            </div-->

            <form name="form" id="form_propriedade">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input type="hidden" class="form-control" id="inputId" name="id" placeholder="Id" />
                <div class="col-md-8">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="row">
                                <div class="form-group col-md-8">
                                    <label for="inputNome" class="control-label">Nome da Propriedade</label>
                                    <input class="form-control" data-ng-model="registro.nome" id="inputNome" name="nome" placeholder="Nome da Propriedade" required />
                                </div>

                                <div class="form-group col-md-4 ">
                                    <label for="inputSituacaoFundiaria" class="control-label">Situação Fundiária</label>
                                    <select class="form-control" data-ng-model="registro.situacaoFundiaria" id="selectSituacaoFundiaria" name="situacaoFundiaria">
                                        <option value="">Selecione...</option>
                                        <option value="E">Escritura Definitiva</option>
                                        <option value="P">Posse</option>
                                        <option value="C">Concessão de Uso</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row">
                                <input type="hidden" id="classeMeioContato" nomeCampo=".@class" value="gov.emater.aterweb.model.MeioContatoEndereco" tag="meioContato" />
                                <input type="hidden" id="meioContatoTipo" nomeCampo=".meioContatoTipo" value="END" tag="meioContato" />

                                <div class="clearfix "></div>

                                <div class="form-group">                        
                                    <div class="col-md-2">
                                        <label for="cepEndereco" class="control-label">CEP</label><br>
                                        <input class="form-control cep" data-ng-model="registro.meioContatoEndereco.cep" data-ng-blur="retornaEnderecoPorCep(registro.meioContatoEndereco.cep, registro.meioContatoEndereco)" exibir="true" title="CEP" id="cepEndereco" nomeCampo=".cep"  tag="meioContatoEndereco" />
                                    </div>
                                    <div class="col-md-2">
                                        <label for="paisEndereco" class="control-label">País</label><br>
                                        <select class="form-control" data-ng-model="registro.meioContatoEndereco.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi.id" id="paisEndereco" data-ng-change="retornaEstados(registro.meioContatoEndereco.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.pessoaGrupoPaisVi.id)" data-ng-options="pais.id as pais.nome for pais in selectPaises">
                                            <option value="">Selecione...</option>
                                            <!--GERADO POR ANGULAR-->
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <label for="ufEndereco" class="control-label">Estado</label><br>
                                        <!--select class="form-control estados" id="ufEndereco"-->
                                        <select class="form-control" data-ng-model="registro.meioContatoEndereco.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.id" id="ufEndereco" data-ng-change="retornaMunicipios(registro.meioContatoEndereco.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.pessoaGrupoEstadoVi.id)" data-ng-options="estado.id as estado.nome for estado in selectEstados">
                                            <option value="">Selecione...</option>
                                            <!--GERADO POR ANGULAR-->
                                        </select>

                                    </div>
                                    <div class="col-md-3">
                                        <label for="municipioEndereco" class="control-label">Município</label><br>
                                        <!--select class="form-control municipios" id="municipioEndereco"-->
                                        <select class="form-control" data-ng-model="registro.meioContatoEndereco.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.id" id="municipioEndereco" data-ng-change="retornaCidades(registro.meioContatoEndereco.pessoaGrupoCidadeVi.pessoaGrupoMunicipioVi.id)" data-ng-options="municipio.id as municipio.nome for municipio in selectMunicipios">
                                            <option value="">Selecione...</option>
                                            <!--GERADO POR ANGULAR-->
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <label for="cidadeEndereco" class="control-label">Cidade</label><br>
                                        <!--select class="form-control cidades" exibir="true" title="Cidade" id="cidadeEndereco" nomeCampo=".id"  tag="localizacao"-->
                                        <select class="form-control" data-ng-model="registro.meioContatoEndereco.pessoaGrupoCidadeVi.id" id="cidadeEndereco" data-ng-change="retornaComunidadeBacia(registro.meioContatoEndereco.pessoaGrupoCidadeVi.id)" data-ng-options="cidade.id as cidade.nome for cidade in selectCidades">
                                            <option value="">Selecione...</option>
                                            <!--GERADO POR ANGULAR-->
                                        </select>
                                    </div>
                                </div>
                                <div class="clearfix "><br/></div>

                                <div class="form-group col-md-12">
                                    <label for="localEndereco" class="control-label form-group-topo">Endereço</label><br />
                                    <input class="form-control" exibir="true" data-ng-model="registro.meioContatoEndereco.descricao" title="Endereço" id="localEndereco" nomeCampo=".descricao" tag="meioContatoEndereco" />
                                </div>
                                <div class="form-group itensPropriedadeRural">
                                    <div class="form-group col-md-3">
                                        <label for="inputLatitude" class="control-label">Latitude</label>
                                        <input class="form-control" id="inputLatitude" data-ng-model="registro.meioContatoEndereco.latitude" tag="meioContatoEndereco" name=".latitude" placeholder="Latitude" />
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label for="inputLongitude" class="control-label">Longitude</label>
                                        <input class="form-control" id="inputLongitude" data-ng-model="registro.meioContatoEndereco.longitude" tag="meioContatoEndereco" name=".longitude" placeholder="Longitude" />
                                    </div>


                                    <div class="form-group col-md-3">
                                        <label for="inputAreaTotal" class="control-label">Área Total</label>

                                        <div class="input-group">
                                            <input class="form-control" id="inputAreaTotal" data-ng-model="registro.areaTotal" placeholder="Área Total" />
                                            <span class="input-group-addon">ha</span>
                                        </div>
                                    </div>

                                    <div class="row"></div>

                                    <div class="col-md-6 subItensPropriedadeRural">
                                        <label for="comuEndereco" class="control-label">Comunidade</label>
                                        <br />
                                        <span>
                                            <select id="comunidade" class="form-control" data-ng-model="registro.comunidade.id" data-ng-options="comunidade.x_id as comunidade.x_nome for comunidade in comunidadesBacias.COMUNIDADE">
                                                <option value="">Selecione...</option>
                                                <!-- ANGULAR -->
                                            </select>
                                        </span>
                                    </div>

                                    <div class="col-md-6 subItensPropriedadeRural">
                                        <label for="baciaEndereco" class="control-label">Bacia</label>
                                        <br />
                                        <span> 
                                            <select id="bacia" class="form-control" data-ng-model="registro.bacia.id" data-ng-options="bacia.x_id as bacia.x_nome for bacia in comunidadesBacias.BACIA_HIDROGRAFICA">
                                                <option value="">Selecione...</option>
                                                <!-- ANGULAR -->
                                            </select>
                                        </span>
                                    </div>
                                </div>

                                <br />
                            </div>


                            <br />

                            <div class="row">
                                <div class="form-group col-md-3">
                                    <label for="inputRegistro" class="control-label">Número do Registro</label>
                                    <input class="form-control" id="inputRegistro" data-ng-model="registro.numeroRegistro" name="numeroRegistro" placeholder="Reg. Núm., LV., FL., Matrícula" />
                                </div>

                                <div class="form-group col-md-3">
                                    <label for="inputDataRegistro" class="control-label">Data de Registro</label>
                                    <input class="form-control data" name="cartorioDataRegistro" data-ng-model="registro.cartorioDataRegistro" id="inputDataRegistro" placeholder="01/01/2000" />
                                </div>

                                <div class="form-group col-md-6">
                                    <label for="inputSistemaProducao" class="control-label">Sistema de Produção</label>
                                    <select class="form-control" data-ng-model="registro.sistemaProducao.id" data-ng-options="sp.id as sp.nome for sp in selectSistemaProducao">                                  
                                        <option value="">Selecione...</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-md-3">
                                    <label for="inputOutorga" class="control-label">Outorga</label>
                                    <select id="selectOutorga" data-ng-model="registro.outorga" name="outorga" class="form-control">
                                        <option value="">Selecione...</option>
                                        <option value='S'>Sim</option>
                                        <option value='N'>Não</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-3">
                                    <label for="inputNumOutorga" class="control-label">Número da Outorga</label>
                                    <input class="form-control" id="inputNumOutorga" data-ng-model="registro.outorgaNumero" name="outorgaNumero" placeholder="Número da Outorga" />
                                </div>
                                <div class="form-group col-md-3">
                                    <label for="inputValidadeOutorga" class="control-label">Data de Validade</label>
                                    <input class="form-control data" id="inputValidadeOutorga" data-ng-model="registro.outorgaValidade" name="outorgaValidade" placeholder="Data de Validade" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-md-4">
                                    <label for="inputFonteAguaPrincipal" class="control-label">Fonte d'água principal</label>
                                    <!--input class="form-control" id="inputFonteAguaPrincipal" placeholder="Fonte d'água principal" /-->
                                    <select id="selectFonteAguaPrincipal" data-ng-model="registro.fonteAguaPrincipal" name="fonteAguaPrincipal" class="form-control">
                                        <option value="">Selecione...</option>
                                        <option value="CA">Canal</option>
                                        <option value="CO">Córrego / Rio</option>
                                        <option value="LA">Lago / Lagoa</option>
                                        <option value="NA">Nascente</option>
                                        <option value="PR">Poço Raso (< 30M)</option>
                                        <option value="PT">Poço Tubular</option>
                                        <option value="OU">Outras</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-2">
                                    <label for="inputVazaoPrincipal" class="control-label">Vazão</label>
                                    <input class="form-control" id="inputVazaoPrincipal" data-ng-model="registro.fonteAguaPrincipalVazao" name="fonteAguaPrincipalVazao" placeholder="0000" />
                                </div>
                            
                                <div class="form-group col-md-4">
                                    <label for="inputFonteAguaDomestica" class="control-label">Fonte d'água doméstica</label>
                                    <!--input class="form-control" id="inputFonteAguaDomestica" placeholder="Fonte d'água doméstica" /-->
                                    <select id="selectFonteAguaDomestica" data-ng-model="registro.fonteAguaDomestica" name="fonteAguaDomestica" class="form-control">
                                        <option value="">Selecione...</option>
                                        <option value="CA">Canal</option>
                                        <option value="CO">Córrego / Rio</option>
                                        <option value="LA">Lago / Lagoa</option>
                                        <option value="NA">Nascente</option>
                                        <option value="PR">Poço Raso (< 30M)</option>
                                        <option value="PT">Poço Tubular</option>
                                        <option value="OU">Outras</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-2">
                                    <label for="inputVazaoDomestica" class="control-label">Vazão</label>
                                    <input class="form-control" id="inputVazaoDomestica" data-ng-model="registro.fonteAguaDomesticaVazao" name="fonteAguaDomesticaVazao" placeholder="0000" />
                                </div>
                            </div> 

                        </div>    
                    </div>

                </div>

                <div id="div_mapa" class="col-md-4">
                    <div id="map-canvas"></div>

                    <button id="div_mapa_acao" class="btn btn-primary" style="margin-top: 5px;" type="button">Expandir Mapa</button>        
                    <br />

                    <!-- <div>
                        <center>
                            <galeria fonte="propriedadeRural"></galeria>
                            <upload fonte="propriedadeRural"></upload>
                            <meus-arquivos fonte="propriedadeRural"></meus-arquivos>
                        </center>
                    </div> -->
                </div>

                <div class="row"></div>
                <h3>Detalhes da Propriedade</h3>
                <div class="row">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#solo" data-toggle="tab">Uso do Solo</a></li>
                        <li><a href="#pastagens" data-toggle="tab">Pastagens</a></li>
                        <li><a href="#areasIrrigadas" data-toggle="tab">Áreas Irrigadas</a></li>
                        <li><a href="#pessoas" data-toggle="tab">Pessoas Vinculadas a Propriedade</a></li>
                        <li><a href="#arquivos" data-toggle="tab">Arquivos</a></li>
                    </ul>

                    <!-- Tab panes -->
                    <div class="tab-content">            

                        <div class="tab-pane fade in active" id="solo">
                            <div class="container">
                                <div class="table-responsive col-md-12">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <td></td>
                                                <td>Área</td>
                                                <td>Valor Unitário</td>
                                                <td>Valor Total</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td><label class="control-label">Culturas Perenes</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputUsoSoloCulturasPerenesArea" data-ng-model="usoSolo.usoSoloCulturaPereneHa" data-ng-change="calculaUsoSoloCulturasPerenes()" name="usoSoloCulturaPereneHa" type="text" class="form-control area" value="0" />
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloCulturasPerenesUnitario" data-ng-model="usoSolo.usoSoloCulturaPereneVlUnit" data-ng-change="calculaUsoSoloCulturasPerenes()" name="usoSoloCulturaPereneVlUnit" type="text" class="form-control moeda" value="0" />
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloCulturasPerenesTotal" disabled data-ng-value="usoSolo.usoSoloCulturaPereneHa * usoSolo.usoSoloCulturaPereneVlUnit" name="usoSoloCulturaPereneVlTot" type="text" class="form-control" value="0" />
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Culturas Temporárias</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputUsoSoloCulturasTermporarias" data-ng-model="usoSolo.usoSoloCulturaTemporariaHa" name="usoSoloCulturaTemporariaHa" type="text" class="form-control area" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloCulturasTermporariasUnitario" data-ng-model="usoSolo.usoSoloCulturaTemporariaVlUnit" name="usoSoloCulturaTemporariaVlUnit" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloCulturasTermporariasTotal" disabled data-ng-model="usoSolo.usoSoloCulturaTemporariaVlTot" name="usoSoloCulturaTemporariaVlTot" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Pastagens</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputUsoSoloPastagens" data-ng-model="usoSolo.usoSoloPastagemHa" name="usoSoloPastagemHa" type="text" class="form-control area" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloPastagensUnitario" data-ng-model="usoSolo.usoSoloPastagemVlUnit" name="usoSoloPastagemVlUnit" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloPastagensTotal" disabled data-ng-model="usoSolo.usoSoloPastagemVlTot" name="usoSoloPastagemVlTot" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Benfeitorias</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputUsoSoloBenfeitoria" name="usoSoloBenfeitoriaHa" data-ng-model="usoSolo.usoSoloBenfeitoriaHa" type="text" class="form-control area" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloBenfeitoriaUnitario" name="usoSoloBenfeitoriaVlUnit" data-ng-model="usoSolo.usoSoloBenfeitoriaVlUnit" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloBenfeitoriaTotal" disabled name="usoSoloBenfeitoriaVlTot" data-ng-model="usoSolo.usoSoloBenfeitoriaVlTot" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Reserva Legal</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputUsoSoloReservaLegal" name="usoSoloReservaLegalHa" data-ng-model="usoSolo.usoSoloReservaLegalHa" type="text" class="form-control area" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloReservaLegalUnitario" name="usoSoloReservaLegalVlUnit" data-ng-model="usoSolo.usoSoloReservaLegalVlUnit" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloReservaLegalTotal" disabled name="usoSoloReservaLegalVlTot" data-ng-model="usoSolo.usoSoloReservaLegalVlTot" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Preservação Permanente</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputUsoSoloReservaPermanente" name="usoSoloPreservPermanenteHa" data-ng-model="usoSolo.usoSoloPreservPermanenteHa" type="text" class="form-control area" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloReservaPermanenteUnitario" name="usoSoloPreservPermanenteVlUnit" data-ng-model="usoSolo.usoSoloPreservPermanenteVlUnit" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloReservaPermanenteTotal" disabled name="usoSoloPreservPermanenteVlTot" data-ng-model="usoSolo.usoSoloPreservPermanenteVlTot" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Outras</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputUsoSoloOutras" name="usoSoloOutrasHa" data-ng-model="usoSolo.usoSoloOutrasHa" type="text" class="form-control area" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloOutrasUnitario" name="usoSoloOutrasVlUnit" data-ng-model="usoSolo.usoSoloOutrasVlUnit" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloOutrasTotal" disabled name="usoSoloOutrasVlTot" data-ng-model="usoSolo.usoSoloOutrasVlTot" type="text" class="form-control moeda" value="0">
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Total</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputUsoSoloTotal" data-ng-model="usoSolo.usoSoloTotalHa" type="text" class="form-control moeda" value="0" />
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon">R$</span>
                                                        <input id="inputUsoSoloValorTotal" data-ng-model="usoSolo.usoSoloTotalVlTot" type="text" class="form-control moeda" value="0" />
                                                    </div>
                                                </td>
                                            </tr>

                                        </tbody>
                                    </table>
                                </div>
                            </div>

                        </div>
                        <div class="tab-pane fade" id="pastagens">
                            <div class="container">
                                <div class="table-responsive col-md-12">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <td></td>
                                                <td>Área</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td><label class="control-label">Área de Canavial</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputPastagemCanavial" name="pastagemCanavial" data-ng-model="registro.pastagemCanavial" type="text" class="form-control">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Área de Capineira</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputPastagemCapineira" name="pastagemCapineira" data-ng-model="registro.pastagemCapineira" type="text" class="form-control">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Área para Silagem</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputPastagemSilagem" name="pastagemSilagem" data-ng-model="registro.pastagemSilagem" type="text" class="form-control">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Área para Feno</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputPastagemFeno" name="pastagemFeno" data-ng-model="registro.pastagemFeno" type="text" class="form-control">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Área de Pastagem Natural</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputPastagemPastagemNatural" name="pastagemNatural" data-ng-model="registro.pastagemNatural" type="text" class="form-control">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Área de Pastagem Artificial</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputPastagemPastagemArtificial" name="pastagemArtificial" data-ng-model="registro.pastagemArtificial" type="text" class="form-control">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Área de Pastagem Rotacionada</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputPastagemPastagemRotacionada" name="pastagemRotacionada" data-ng-model="registro.pastagemRotacionada" type="text" class="form-control">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Área ILP/ILPF</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputPastagemILPILPF" name="pastagemIlpIlpf" data-ng-model="registro.pastagemIlpIlpf" type="text" class="form-control">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="areasIrrigadas">
                            <div class="container">
                                <div class="table-responsive col-md-12">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <td></td>
                                                <td>ha</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td><label class="control-label">Aspersão Convencional</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputAreaIrrigadaAspersao" name="areaIrrigadaAspersao" data-ng-model="registro.areaIrrigadaAspersao" type="text" class="form-control" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Auto-propelido</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputAreaIrrigadaAutoPropelido" name="areaIrrigadaAutoPropelido" data-ng-model="registro.areaIrrigadaAutoPropelido" type="text" class="form-control" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Pivô-central</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputAreaIrrigadaPivoCentral" name="areaIrrigadaPivoCentral" data-ng-model="registro.areaIrrigadaPivoCentral" type="text" class="form-control" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Gotejamento</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputAreaIrrigadaGotejamento" name="areaIrrigadaGotejamento" data-ng-model="registro.areaIrrigadaGotejamento" type="text" class="form-control" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Micro-aspersão</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputAreaIrrigadaMicroAspersao" name="areaIrrigadaMicroAspersao" data-ng-model="registro.areaIrrigadaMicroAspersao" type="text" class="form-control" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Superfície</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputAreaIrrigadaSuperficie" name="areaIrrigadaSuperficie" data-ng-model="registro.areaIrrigadaSuperficie" type="text" class="form-control" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Outros</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputAreaIrrigadaOutros" name="areaIrrigadaOutros" data-ng-model="registro.areaIrrigadaOutros" type="text" class="form-control" value="0">
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label class="control-label">Total</label></td>
                                                <td>
                                                    <div class="input-group">
                                                        <input id="inputAreaIrrigadaTotal" type="text" data-ng-model="registro.areaIrrigadaTotal" class="form-control" disabled>
                                                        <span class="input-group-addon">ha</span>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="pessoas">
                            <div class="container"> <br />
                                <div class="table-responsive col-md-12">

                                    <relacionamentos fonte="propriedadeRural"></relacionamentos>

                                </div>
                            </div>
                        </div>
							<div class="tab-pane fade" id="arquivos">
								<div class="container col-md-12">
									<div class="visible-md visible-lg">
											<!--  	<galeria fonte="pessoa"></galeria> -->
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
																										
														<!-- td><a target="_blank"	id="{{arquivo.arquivo.id}}" href="./descer?arq={arquivo.arquivo.md5}}{{arquivo.arquivo.extensao}}">{{arquivo.arquivo.nome}}</a></td-->
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
												</div-->
											
										</div>
									</div>
								<br> <br> <br>

							</div>
							
                    </div>
                </div>
            </form>

            <!-- Modal -->


            <script type="text/javascript">
                var map;
                var local = [0, 0];

                $(document).on("change", "#inputLatitude, #inputLongitude", function() {
                    local = [$("#inputLatitude").val(),
                        $("#inputLongitude").val()];
                    //console.log("AQUI "+ {{registro}})
                    if (local[0] !== '') {
                        initialize();
                    }
                });

                function initialize() {
                    if ($("#inputLongitude").val() === '') {
                        local = ['-15.732805',
                            '-47.903791'];
                    } else {
                        local = [$("#inputLatitude").val(),
                            $("#inputLongitude").val()];
                    }

                    var posicao = new google.maps.LatLng(local[0], local[1]);
                    var mapOptions = {
                        zoom: 15,
                        center: posicao,
                        panControl: true,
                        zoomControl: true,
                        scaleControl: true,
                        mapTypeId: google.maps.MapTypeId.SATELLITE
                    };

                    map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
                    map.setCenter(posicao);

                    var marker = new google.maps.Marker({
                        position: posicao,
                        title: "Local Informado!",
                        draggable: true
                    });

                    marker.setMap(map);

                    google.maps.event.addDomListener(marker, "dragend", function() {
                        var point = marker.getPosition();
                        map.panTo(point);
                        
                        $("#inputLatitude").val(point.k);
                        $("#inputLongitude").val(point.B);
                    });
                }
                initialize();
                //google.maps.event.addDomListener(window, 'load', initialize);

                $(function() {
                    //$(".moeda").maskMoney({allowZero: true, decimal: ',', thousands: '.'});
                    //$(".area").maskMoney({allowZero: true, decimal: ',', thousands: '.'});
                    $(".data").mask('99/99/9999');
                    //$(".area").maskMoney( {allowZero:true, decimal: ','} );
                    
                });

                $("#div_mapa_acao").click(function() {
                    mapa = $("#div_mapa");

                    if ($(this).text() === "Expandir Mapa") {
                        expandirDivMapa(mapa);
                    } else {
                        diminuirDivMapa(mapa);
                    }
                });
                function expandirDivMapa(mapa) {
                    $(mapa).removeClass("col-md-4");
                    $(mapa).addClass("col-md-12");
                    $(mapa).addClass("borda_div_mapa");
                    $(mapa).addClass("div_mapa_full");

                    $("#map-canvas").attr("style", "height: 100%");

                    $("#div_mapa_acao").text("Dimunuir Mapa");
                    initialize();
                }

                function diminuirDivMapa(mapa) {
                    $(mapa).removeClass("col-md-12");
                    $(mapa).addClass("col-md-4");
                    $(mapa).removeClass("borda_div_mapa");
                    $(mapa).removeClass("div_mapa_full");

                    $("#map-canvas").removeAttr("style", "height: 100%");

                    $("#div_mapa_acao").text("Expandir Mapa");
                    initialize();
                }
                /*
                 function calculaUsoSoloArea() {
                 var total = 0;
                     
                 total = transformaMoedaRealParaFloat($("#inputUsoSoloCulturasPerenesArea").val()) + transformaMoedaRealParaFloat($("#inputUsoSoloCulturasTermporarias").val()) + transformaMoedaRealParaFloat($("#inputUsoSoloPastagens").val()) + transformaMoedaRealParaFloat($("#inputUsoSoloBenfeitoria").val()) + transformaMoedaRealParaFloat($("#inputUsoSoloReservaLegal").val()) + transformaMoedaRealParaFloat($("#inputUsoSoloReservaPermanente").val()) + transformaMoedaRealParaFloat($("#inputUsoSoloOutras").val());
                     
                 $("#inputUsoSoloTotal").val(total);
                 $("#inputUsoSoloTotalB").val(total);
                 }
                     
                 function calculaUsoSoloValorTotal() {
                 var total = 0;
                     
                 var usoSoloTotal1 = transformaMoedaRealParaFloat($("#inputUsoSoloCulturasPerenesTotal").val());
                 var usoSoloTotal2 = transformaMoedaRealParaFloat($("#inputUsoSoloCulturasTermporariasTotal").val());
                 var usoSoloTotal3 = transformaMoedaRealParaFloat($("#inputUsoSoloPastagensTotal").val());
                 var usoSoloTotal4 = transformaMoedaRealParaFloat($("#inputUsoSoloBenfeitoriaTotal").val());
                 var usoSoloTotal5 = transformaMoedaRealParaFloat($("#inputUsoSoloReservaLegalTotal").val());
                 var usoSoloTotal6 = transformaMoedaRealParaFloat($("#inputUsoSoloReservaPermanenteTotal").val());
                 var usoSoloTotal7 = transformaMoedaRealParaFloat($("#inputUsoSoloOutrasTotal").val());
                     
                 total = usoSoloTotal1 + usoSoloTotal2 + usoSoloTotal3 + usoSoloTotal4 + usoSoloTotal5 + usoSoloTotal6 + usoSoloTotal7;
                 total = total.toFixed(2);
                     
                 $("#inputUsoSoloValorTotal").val(total);
                 $("#inputUsoSoloValorTotalB").val(total);
                 }
                     
                 function calculaValoresUsoSoloTotais() {
                 var total1 = 0;
                 var total2 = 0;
                 var total3 = 0;
                 var total4 = 0;
                 var total5 = 0;
                 var total6 = 0;
                 var total7 = 0;
                     
                 total1 = transformaMoedaRealParaFloat($("#inputUsoSoloCulturasPerenesArea").val()) * transformaMoedaRealParaFloat($("#inputUsoSoloCulturasPerenesUnitario").val());
                 total2 = transformaMoedaRealParaFloat($("#inputUsoSoloCulturasTermporarias").val()) * transformaMoedaRealParaFloat($("#inputUsoSoloCulturasTermporariasUnitario").val());
                 total3 = transformaMoedaRealParaFloat($("#inputUsoSoloPastagens").val()) * transformaMoedaRealParaFloat($("#inputUsoSoloPastagensUnitario").val());
                 total4 = transformaMoedaRealParaFloat($("#inputUsoSoloBenfeitoria").val()) * transformaMoedaRealParaFloat($("#inputUsoSoloBenfeitoriaUnitario").val());
                 total5 = transformaMoedaRealParaFloat($("#inputUsoSoloReservaLegal").val()) * transformaMoedaRealParaFloat($("#inputUsoSoloReservaLegalUnitario").val());
                 total6 = transformaMoedaRealParaFloat($("#inputUsoSoloReservaPermanente").val()) * transformaMoedaRealParaFloat($("#inputUsoSoloReservaPermanenteUnitario").val());
                 total7 = transformaMoedaRealParaFloat($("#inputUsoSoloOutras").val()) * transformaMoedaRealParaFloat($("#inputUsoSoloOutrasUnitario").val());
                     
                 $("#inputUsoSoloCulturasPerenesTotal").val(total1);
                 $("#inputUsoSoloCulturasTermporariasTotal").val(total2);
                 $("#inputUsoSoloPastagensTotal").val(total3);
                 $("#inputUsoSoloBenfeitoriaTotal").val(total4);
                 $("#inputUsoSoloReservaLegalTotal").val(total5);
                 $("#inputUsoSoloReservaPermanenteTotal").val(total6);
                 $("#inputUsoSoloOutrasTotal").val(total7);
                     
                 $(".moeda").maskMoney('mask');
                 }
                 */
                /*$(document).on("change", "#inputUsoSoloCulturasPerenesTotal, #inputUsoSoloCulturasTermporariasTotal, #inputUsoSoloPastagensTotal, #inputUsoSoloBenfeitoriaTotal, #inputUsoSoloReservaLegalTotal, #inputUsoSoloReservaPermanenteTotal, #inputUsoSoloOutrasTotal", function(){
                 calculaUsoSoloValorTotal();
                 });*/
                /*
                 $(document).on("change", "#inputUsoSoloCulturasPerenesUnitario, #inputUsoSoloCulturasTermporariasUnitario, #inputUsoSoloPastagensUnitario, #inputUsoSoloBenfeitoriaUnitario, #inputUsoSoloReservaLegalUnitario, #inputUsoSoloReservaPermanenteUnitario, #inputUsoSoloOutrasUnitario", function() {
                 calculaUsoSoloValorTotal();
                 });
                     
                 $(document).on("keyup", "#inputUsoSoloCulturasPerenesArea, #inputUsoSoloCulturasTermporarias, #inputUsoSoloPastagens, #inputUsoSoloBenfeitoria, #inputUsoSoloReservaLegal, #inputUsoSoloReservaPermanente, #inputUsoSoloOutras", function() {
                 calculaUsoSoloArea();
                 });
                     
                 $($(".table").eq(0)).on("keyup", ".form-control", function() {
                 calculaValoresUsoSoloTotais();
                 });
                     
                 function calculaAreasIrrigadas() {
                 var total1 = $("#inputAreaIrrigadaAspersao").val();
                 var total2 = $("#inputAreaIrrigadaAutoPropelido").val();
                 var total3 = $("#inputAreaIrrigadaPivoCentral").val();
                 var total4 = $("#inputAreaIrrigadaGotejamento").val();
                 var total5 = $("#inputAreaIrrigadaMicroAspersao").val();
                 var total6 = $("#inputAreaIrrigadaSuperficie").val();
                 var total7 = $("#inputAreaIrrigadaOutros").val();
                     
                 total = transformaMoedaRealParaFloat(total1) + transformaMoedaRealParaFloat(total2) + transformaMoedaRealParaFloat(total3) + transformaMoedaRealParaFloat(total4) + transformaMoedaRealParaFloat(total5) + transformaMoedaRealParaFloat(total6) + transformaMoedaRealParaFloat(total7);
                 $("#inputAreaIrrigadaTotal").val(total);
                 }
                     
                 $(document).on("keyup", "#inputAreaIrrigadaAspersao, #inputAreaIrrigadaAutoPropelido, #inputAreaIrrigadaPivoCentral, #inputAreaIrrigadaGotejamento, #inputAreaIrrigadaMicroAspersao, #inputAreaIrrigadaSuperficie, #inputAreaIrrigadaOutros", function() {
                 calculaAreasIrrigadas();
                 });
                 */
                $(document).on("click", ".btnNovaPessoaVinculada", function() {
                    procurarPessoa();
                });

 				setTimeout(function() {
					$(".ttip").tooltip();
				}, 800);
 				
            </script>  

            </body> 
            </html>
        </div>
    </div>
</div>