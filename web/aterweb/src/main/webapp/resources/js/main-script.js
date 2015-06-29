/**
 * Script Geral
 */
// codigo para criar funcao paracida com o String.format do java
// neste caso a sintaxe é: 
// 'The {0} is dead. Don\'t code {0}. Code {1} that is open source!'.format('ASP', 'PHP');
String.prototype.format = function() {
    var formatted = this;
    for (var i = 0; i < arguments.length; i++) {
        var regexp = new RegExp('\\{'+i+'\\}', 'gi');
        formatted = formatted.replace(regexp, arguments[i]);
    }
    return formatted;
};

function humanFileSize(bytes) {
	var thresh = 1024;
    if(bytes < thresh) return bytes + ' B';
    var units = ['kB','MB','GB','TB','PB','EB','ZB','YB'];
    var u = -1;
    do {
        bytes /= thresh;
        ++u;
    } while(bytes >= thresh);
    return bytes.toFixed(1)+' '+units[u];
};

function dataToInputData(data) {
	if (isUndefOrNull(data) || data.length != 10) {
		return null;
	}
	var d = data.split("/");
	return d[2] + "-" + d[1] + "-" + d[0];
}

function inputDataToData(data) {
	if (isUndefOrNull(data) || data.length != 10) {
		return null;
	}
	var d = data.split("-");
	return d[2] + "/" + d[1] + "/" + d[0];
}

function isUndefOrNull(variavel) {
	return typeof variavel === "undefined" || variavel === null;
}

function trocar(pagina){
        var url = '//' + window.location.host + window.location.pathname + '#/' + pagina;
        window.location.href = url;
}
// Menu criatividade!
$.fn.serializeAnything = function(tag) {
    var toReturn = {};
    if (tag !== undefined) {
        tag = '[tag="' + tag + '"]';
    } else {
        tag = '';
    }
    $(this)
            .find(tag + ':input')
            .each(
                    function() {

                        if ((this.name || $(this).attr('nomeCampo'))
                                && !this.disabled
                                && (this.checked
                                        || /select|textarea/i
                                        .test(this.nodeName) || /text|hidden|password/i
                                        .test(this.type))) {
                            var val = $(this).val();
                            var name = this.name.split('.');

                            if ($(this).attr('nomeCampo') !== undefined) {
                                name = $(this).attr('nomeCampo').split('.');
                            }

                            if (name.length <= 0) {
                                toReturn[name] = val;
                            } else if (name.length > 0 && name.length <= 2) {
                                toReturn[name[1]] = val;
                            }
                        }
                    });

    return toReturn;

};

jQuery.fn.reset = function() {
    this.each(function() {
        if ($(this).is('form')) {
            var button = jQuery(jQuery('<input type="reset" />'));
            button.hide();
            $(this).append(button);
            button.click().remove();
        } else if ($(this).parent('form').size()) {
            var button = jQuery(jQuery('<input type="reset" />'));
            button.hide();
            $(this).parent('form').append(button);
            button.click().remove();
        } else if ($(this).find('form').size()) {
            $(this).find('form').each(function() {
                var button = jQuery(jQuery('<input type="reset" />'));
                button.hide();
                $(this).append(button);
                button.click().remove();
            });
        }
    });
    return this;
};

$.fn.serializeJSON = function() {
    var json = {};
    var form = $(this);
    form.find('input, select').each(
            function() {
                var val;
                if (!this.name)
                    return;

                if ('radio' === this.type) {
                    if (json[this.name]) {
                        return;
                    }

                    json[this.name] = this.checked ? this.value : '';
                } else if ('checkbox' === this.type) {
                    val = json[this.name];

                    if (!this.checked) {
                        if (!val) {
                            json[this.name] = '';
                        }
                    } else {
                        json[this.name] = typeof val === 'string' ? [val,
                            this.value] : $.isArray(val) ? $.merge(val,
                                [this.value]) : this.value;
                    }
                } else {
                    json[this.name] = this.value;
                }
            });
    return json;
};

$.fn.serializeObject = function() {
    var self = this, json = {}, push_counters = {}, patterns = {
        "validate": /^[@a-zA-Z][@a-zA-Z0-9_\[\]\.]*(?:\[(?:\d*|[@a-zA-Z0-9_]+)\])*$/,
        "key": /[@a-zA-Z0-9_\[\]\.]+|(?=\[\])/g,
        "push": /^$/,
        "fixed": /^\d+$/,
        "named": /^[@a-zA-Z0-9_\[\]\.]+$/
    };
    this.build = function(base, key, value) {
        base[key] = value;
        return base;
    };
    this.push_counter = function(key) {
        if (push_counters[key] === undefined) {
            push_counters[key] = 0;
        }
        return push_counters[key]++;
    };
    $
            .each(
                    $(this).serializeArray(),
                    function() {
                        // skip invalid keys
                        if (!patterns.validate.test(this.name)) {
                            return;
                        }
                        var k, keys = this.name.match(patterns.key), merge = this.value, reverse_key = this.name;

                        // Ignora os campos que estiverem em branco
                        if (merge === undefined || merge === '') {
                            return;
                        }
                        while ((k = keys.pop()) !== undefined) {
                            // adjust reverse_key
                            reverse_key = reverse_key.replace(new RegExp("\\["
                                    + k + "\\]$"), '');
                            // push
                            if (k.match(patterns.push)) {
                                merge = self.build([], self
                                        .push_counter(reverse_key), merge);
                            }
                            // fixed
                            else if (k.match(patterns.fixed)) {
                                merge = self.build([], k, merge);
                            }

                            // named
                            else if (k.match(patterns.named)) {
                                merge = self.build({}, k, merge);
                            }
                        }
                        json = $.extend(true, json, merge);
                    });
    return json;
};

$(function() {
    $("#corpo").on("click", function() {
        $("#barra-ferramenta").pin({
            minWidth: 10,
            containerSelector: "#corpo"
        });
        $("#pessoa_visualizar").pin({
            minWidth: 10
        });
    });
});

/**
 * Recursividade no objeto, procurando campo informado;
 * 
 * @param obj
 *            objeto a ser percorrido
 * @param campo
 *            campo a ser pesquisado
 * @returns num
 *            valor do campo
 * @author adlerluiz
 */
var arrayComparacao = new Array();

function campoRecursivo(obj, campo) {
    if (obj[campo]) {
        arrayComparacao[obj['@jsonId']] = obj[campo];
        return obj[campo];
    } else {
        return arrayComparacao[obj];
    }
}

/**
 * Transforma o formato de moeda para float
 * 
 * @param num
 *            numero a ser transformado
 * 
 * @returns num
 *            numero em float
 * @author adlerluiz
 */
function transformaMoedaRealParaFloat(num) {
//    console.log('1>> ');
//    console.log(num);
    
    if(num.indexOf(',')) {
        num = num.replace('.', '');
        num = num.replace(',', '.');

        num = parseFloat(num);
    }
    
//    console.log('2>> ');
//    console.log(num);
    
    if(num % 1 === 0) {
        num = num + ',00';
    }
    
//    console.log('3>> ');
//    console.log(num);

    return num;
}

/**
 * Faz pesquisa em estruturas json em busca de objetos cujo atributo jsonId
 * corresponda ao valor informado
 * 
 * @param contexto
 *            estrurua json que mantem todos os dados possivelmente relacionados
 * @param campo
 *            indicacao do campo a ser encontrado
 * @returns Se o parametro campo nao for um UUID valido sera retornado o seu
 *          valor. Se for um UUID valido, o objeto que possui a propriedade
 *          jsonId igual ao valor do campo informado. Se nao for encontrado
 *          nenhum objeto com o UUID informado o valor original UUID sera
 *          retornado.
 * @author frazao
 */
function valorCampoJson(contexto, campo) {
    // reg exp para encontrar numeros UUUID
    if (/^[\da-f]{8}-[\da-f]{4}-[\da-f]{4}-[\da-f]{4}-[\da-f]{12}$/i
            .test(campo)) {
        // procurar o valor do objeto pelo @jsonId
        var result = pesquisaObjetoPeloJsonId(contexto, campo);
        if (result === null) {
            //alert("Erro, OBJETO nao encontrado");
            console.log("Erro, OBJETO nao encontrado");
        }
        // se nao encontrou retornar o UUID original
        result = result === null ? campo : result;
        return result;
    } else {
        // se o valor nao for um UUID retornar o valor do campo
        return campo;
    }
}

function remodelarCampoJson(fonte,objeto){
    if(angular.isObject(objeto) || angular.isArray(objeto)){
        angular.forEach(objeto, function(value,key){
            if (/^[\da-f]{8}-[\da-f]{4}-[\da-f]{4}-[\da-f]{4}-[\da-f]{12}$/i.test(objeto[key])) {
                if(key !== '@jsonId'){
                    var i = angular.copy(valorCampoJson(fonte,objeto[key]));
                    if(i['@class'] !== undefined && i['@class'] !== null){
                        objeto[key] = {id: i.id,'@class':i['@class']};
                    }else{
                        objeto[key] = {id: i.id};
                    }
                }
            }else{
                remodelarCampoJson(fonte,objeto[key]);
            }
            
        });
    }
}


/**
 * Funcao de apoio ao valorCampoJson responsavel por encontrar o objeto cuja
 * propriedade jsonId seja igual ao valor do campo informado
 * 
 * @param contexto
 *            estrutura json que mantem todos os dados relacionados atraves do
 *            jsonId
 * @param jsonId
 *            indicacao do campo a ser encontrado no formato UUID
 * @returns O objeto cuja propriedade jsonId seja igual ao UUID informado
 * @author frazao
 */
function pesquisaObjetoPeloJsonId(contexto, jsonId) {
    var result = null;
    if (!isUndefOrNull(contexto) && !isUndefOrNull(jsonId)) {
        if (contexto['@jsonId'] !== null && contexto['@jsonId'] === jsonId) {
            // console.warn("encontrei", contexto, contexto['@jsonId'], jsonId);
            result = contexto;
        } else if (result === null
                && (contexto instanceof Array || contexto instanceof Object)) {
            $.each(contexto, function(chave, valor) {
                result = pesquisaObjetoPeloJsonId(valor, jsonId);
                if (result !== null) {
                    return false;
                }
            });
        }
    }
    return result;
}

function abrirPopUp(url,w,h) {
    var left = (screen.width / 2) - (w / 2);
    var top  = (screen.height / 2) - (h / 2) - 50;
    
    janela = window.open(baseUrl+url, url, "width="+w+", height="+h+", top="+top+", left="+left+", scrollbars=1,resizable=0");
    janela.focus();
}

function detalharPessoaPorId(id) {
    abrirPopUp('pessoa-cad/?modo=P&id='+id, 1020, 700);
}

function procurarPessoa() {
    abrirPopUp('pessoa-cad/?modo=P', 1020, 700);
}

function detalharPesquisaTextual(url) {
    abrirPopUp(url, 700, 600);
}


function copiaJsonId(contexto, elemento) {
	var buffer = {};
	for (campo in elemento) {
		if (campo !== "@jsonId") {
			angular.copy(buffer[campo] = valorCampoJson(contexto, elemento[campo]));
//			delete elemento["@jsonId"];
		}
	}
	return buffer;
}

/**
 * Remove o atributo JsonId dos dados a serem enviados ao servidor
 * 
 * @param contexto
 *            estrurua json que possui atributos do tipo jsonid
 *            
 * @author frazao
 */
function removerJsonId(contexto) {
	if (isUndefOrNull(contexto)) {
		return;
	} else if (!isUndefOrNull(contexto['@jsonId'])) {
        // console.warn("encontrei", contexto, contexto['@jsonId']);
    	delete contexto['@jsonId'];
    } 
    if (contexto instanceof Array || contexto instanceof Object) {
        $.each(contexto, function(chave, valor) {
            removerJsonId(valor);
        });
    }
}

function removerCampo(contexto, campos) {
	if (isUndefOrNull(contexto)) {
		return;
	} else {
		for (idx in campos) {
			if (!isUndefOrNull(contexto[campos[idx]])) {
				delete contexto[campos[idx]];
			}
		}
	}
    if (contexto instanceof Array || contexto instanceof Object) {
        $.each(contexto, function(chave, valor) {
        	removerCampo(valor, campos);
        });
    }
}

/**
 * Remove o atributo JsonId dos dados a serem enviados ao servidor
 * 
 * @param contexto
 *            estrurua json que possui atributos do tipo jsonid
 *            
 * @author jean
 */
function removerArquivoList(contexto) {
	if (isUndefOrNull(contexto)) {
		return;
	} else if (!isUndefOrNull(contexto['arquivoList'])) {
        // console.warn("encontrei", contexto, contexto['@jsonId']);
    	delete contexto['arquivoList'];
    } 
    if (contexto instanceof Array || contexto instanceof Object) {
        $.each(contexto, function(chave, valor) {
            removerArquivoList(valor);
        });
    }
}

function removerReferenciasJsonId(objeto){
    if(angular.isObject(objeto) || angular.isArray(objeto)){
        angular.forEach(objeto, function(value,key){
            if (/^[\da-f]{8}-[\da-f]{4}-[\da-f]{4}-[\da-f]{4}-[\da-f]{12}$/i.test(objeto[key])) {
				if(angular.isArray(objeto)){
					objeto.splice(key,1);
				}else{
					delete objeto[key];
				}
            }else{
                removerReferenciasJsonId(objeto[key]);
            }
        });
    }
}

function parseURL(url) {
    var a =  document.createElement('a');
    a.href = url;
    return {
        source: url,
        protocol: a.protocol.replace(':',''),
        host: a.hostname,
        port: a.port,
        query: a.search,
        params: (function(){
            var ret = {},
                seg = a.search.replace(/^\?/,'').split('&'),
                len = seg.length, i = 0, s;
            for (;i<len;i++) {
                if (!seg[i]) { continue; }
                s = seg[i].split('=');
                ret[s[0]] = s[1];
            }
            return ret;
        })(),
        file: (a.pathname.match(/\/([^\/?#]+)$/i) || [,''])[1],
        hash: a.hash.replace('#',''),
        path: a.pathname.replace(/^([^\/])/,'/$1'),
        relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [,''])[1],
        segments: a.pathname.replace(/^\//,'').split('/')
    };
}

//$(function(){    
//    var janela = window.opener;
//    var id = parseURL(window.location.href).params['id'];
//    
//    console.log("JANELA");    
//    console.log(id);
//    
//    if(janela !== undefined){
//        var documento = janela.document;
//
//        $(documento).find(".detalharPessoa[id='"+id+"']").text("Cliquei Aqui");
//        var pessoa = $(documento).find(".detalharPessoa[id='"+id+"']").parent().parent();
//
//        editar(id);
//
//        $("#btn_salvar").click(function(){
//            salvar();
//
//            pessoa.find("[pessoa-nome]").html($("#nome").val());
//            pessoa.find("[pessoa-regime]").html("Prop");
//            pessoa.find("[pessoa-area]").html(10);
//        });
//
//        $("#btn_cancelar").click(function(){
//            window.close();
//        });
//}
//}); 

if (typeof cadastroCtrl === "undefined") {
	//console.log("nova funcao");
	cadastroCtrl = function($scope) {};
}

var objetosJson = [];
function transformaJsonId(json) {
	if (isUndefOrNull(json)) {
		return null;
	} else if (json instanceof Object) {
		if (!(json instanceof Array)) {
			if (!isUndefOrNull(json["@jsonId"])) {
				objetosJson.push(json);
			}
		}
		// se for array ou objeto (percorrer os elementos)
		for (var elemento in json) {
			// percorrer os elementos
			if (elemento !== "@jsonId") {
				json[elemento] = transformaJsonId(json[elemento]);
			}
		}
	} else if (/^[\da-f]{8}-[\da-f]{4}-[\da-f]{4}-[\da-f]{4}-[\da-f]{12}$/i.test(json)) {
		// encontrar o objeto com a referencia json
		for (var objeto in objetosJson) {
			if (!isUndefOrNull(objetosJson[objeto]["@jsonId"]) && objetosJson[objeto]["@jsonId"] === json) {
				return objetosJson[objeto];
			}
		}
		throw "Estado do objeto inv�lido, refer�ncia (" + json + ") n�o encontrada";
	}
	// sen�o, retornar o valor original
	return json;
}

function soNumeros(numero) {
	if (isUndefOrNull(numero)) {
		return null;
	}
	var result = "";
	for (var i = 0; i < numero.length; i++) {
		if (numero[i] >= '0' && numero[i] <= '9') {
			result = result + numero[i];
		}
	}
	return result;
}

function preenche(valor, tamanho, caractere, esquerda) {
	if (isUndefOrNull(valor)) {
		valor = "";
	}
	while (valor.length < tamanho) {
		valor = esquerda? caractere + valor: valor + caractere;
	}
	return valor;
}

function zeroEsq(numero, tamanho) {
	return preenche(numero, tamanho, '0', true);
}

function tudoRepetido(valor) {
	if (isUndefOrNull(valor)) {
		return null;
	}
	if (valor.length < 2) {
		return true;
	};
	var pos = 0;
	var c = valor[pos];
	while (++pos < valor.length) {
		if (valor[pos] != c) {
			return false;
		}
	}
	return true;
}

function formataCpf(numero) {
	if (isUndefOrNull(numero)) {
		return null;
	}
	if (numero.length != 11) {
		return null;
	};
	var result = ""; 
	result += numero.substring(0,3);
	result += ".";
	result += numero.substring(3,6);
	result += ".";
	result += numero.substring(6,9);
	result += "-";
	result += numero.substring(9,11);
	return result;
}

function formataCnpj(numero) {
	if (isUndefOrNull(numero)) {
		return null;
	}
	if (numero.length != 14) {
		return null;
	};
	var result = ""; 
	result += numero.substring(0,2);
	result += ".";
	result += numero.substring(2,5);
	result += ".";
	result += numero.substring(5,8);
	result += "/";
	result += numero.substring(8,12);
	result += "-";
	result += numero.substring(12,14);
	return result;
}

function formataCpfCnpj(numero) {
	numero = soNumeros(numero);
	numero = zeroEsq(numero, numero.length <= 11 ? 11 : 14);
	if (numero.length <= 11) {
		return formataCpf(numero);
	} else {
		return formataCnpj(numero);
	}
}

function validarCpfCnpj(numero) {
	numero = soNumeros(numero);
	numero = zeroEsq(numero, numero.length <= 11 ? 11 : 14);
	if (numero.length > 14 || tudoRepetido(numero)) {
		return false;
	}
	if (numero.length <= 11) {
		return validarCpf(numero);
	} else {
		return validarCnpj(numero);
	}
}

function validarCpf(numero) {
	exp = /\.|\-/g;
	numero = numero.toString().replace(exp, "");

	if (numero === null || numero === '11111111111' || numero === '22222222222'
			|| numero === '33333333333' || numero === '44444444444'
			|| numero === '55555555555' || numero === '66666666666'
			|| numero === '77777777777' || numero === '88888888888'
			|| numero === '99999999999' || numero === '00000000000') {
		return false;
	}

	var digitoDigitado = eval(numero.charAt(9) + numero.charAt(10));
	var soma1 = 0, soma2 = 0;
	var vlr = 11;

	for (var i = 0; i < 9; i++) {
		soma1 += eval(numero.charAt(i) * (vlr - 1));
		soma2 += eval(numero.charAt(i) * vlr);
		vlr--;
	}
	soma1 = (((soma1 * 10) % 11) === 10 ? 0 : ((soma1 * 10) % 11));
	soma2 = (((soma2 + (2 * soma1)) * 10) % 11);

	var digitoGerado = (soma1 * 10) + soma2;
	if (digitoGerado !== digitoDigitado) {
		return false;
	}
	return true;
}

function validarCnpj(numero) {
	var valida = new Array(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2);
	var dig1 = new Number;
	var dig2 = new Number;
//	console.log(cnpj);
	exp = /\.|\-|\//g;
	cnpj = cnpj.toString().replace(exp, "").toString();
//	console.log(cnpj);
	if (cnpj === null || cnpj === '11111111111111' || cnpj === '222222222222222'
			|| cnpj === '33333333333333' || cnpj === '44444444444444'
			|| cnpj === '555555555555555' || cnpj === '66666666666666'
			|| cnpj === '77777777777777' || cnpj === '888888888888888'
			|| cnpj === '99999999999999' || cnpj === '00000000000000') {
		return false;
	}

	var valida = new Array(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2);
	var dig1 = new Number;
	var dig2 = new Number;

	var digito = new Number(eval(cnpj.charAt(12) + cnpj.charAt(13)));
//	console.log(dig1, dig2, digito);
	for (var i = 0; i < valida.length; i++) {
		dig1 += (i > 0 ? (cnpj.charAt(i - 1) * valida[i]) : 0);
		dig2 += cnpj.charAt(i) * valida[i];
//		console.log(dig1, dig2);
	}
	dig1 = (((dig1 % 11) < 2) ? 0 : (11 - (dig1 % 11)));
	dig2 = (((dig2 % 11) < 2) ? 0 : (11 - (dig2 % 11)));
//	console.log('final', dig1, dig2);
	if (((dig1 * 10) + dig2).toString() !== digito.toString()) {
		return false;
	}
	return true;
}

$(document).on("click", ".detalharPessoa", function(){
    id = this.id;
    detalharPessoaPorId(id);
});

$(document).ready(function() {
	$("#pesquisaTextualTxt").keypress(function(e){
	    var code = e.keyCode || e.which;
	    if(code === 13){
	        $("#pesquisaTextualBtn").click();
	    }
	});
	$("#pesquisaTextualBtn").click(function() {
	    if($("#pesquisaTextualTxt").val().length<3){
	        alert('Informe pelo menos 3 letras para pesquisa!');
	        return 0;
	    }
	    var itensPorPagina = 10;
	    
	    $.getJSON(baseUrl + "pesquisa-textual/pesquisar","query=" + $("#pesquisaTextualTxt").val(),function(data){
	        $("#pesquisaTextualForm").modal('show');
	        $("#pesquisaTextualResultadoTxt").html('');
	        $("#pesquisaTextualResultadoTxt").append('<ul class="nav nav-tabs"></ul><div class="tab-content"></div>');
	        var menuResultado = $("#pesquisaTextualResultadoTxt").children('.nav-tabs').eq(0);
	        var conteudoResultado = $("#pesquisaTextualResultadoTxt").children('.tab-content').eq(0);
	        var encontrou = false;
	        if (data != null) {
	        	$.each(data,function(keyItem,valueItem){
	        		if(valueItem !== null && valueItem.length > 0){
	        			encontrou = true;
	        			var menuItem = $('<li><a href="#'+keyItem+'" data-toggle="tab">'+keyItem+'</a></li>');
	        			var conteudoItem = $('<div class="tab-pane fade" id="'+keyItem+'"><br></div>');
	        			var lista = $('<ul class="media-list"></ul>');
	        			
	        			$.each(valueItem,function(key,value){
	        				if($(lista).children('li').length<itensPorPagina){
	        					$(lista).append('<li class="media"><a class="pull-left" href="#"><img src="'+value.foto+'" style="width: 64px; height: 64px;" class="media-object" alt="64x64"></a><div class="media-body"><h4 class="media-heading"><a href="'+value.endereco+'">'+value.nome+'</a></h4>'+value.descricao+'</div></li>');
	        				}else{
	        					$(lista).append('<li class="media hide"><a class="pull-left" href="#"><img src="'+value.foto+'" style="width: 64px; height: 64px;" class="media-object" alt="64x64"></a><div class="media-body"><h4 class="media-heading"><a href="'+value.endereco+'">'+value.e+'</a></h4>'+value.descricao+'</div></li>');
	        				}
	        			});
	        			var paginas = Math.ceil(valueItem.length / itensPorPagina);
	        			if(paginas > 1){
	        				var paginacao = $('<ul class="pagination">');
	        				$(paginacao).append('<li><a href="#" class="anterior">&laquo;</a></li>');
	        				for(var i = 1;i <= paginas;i++){
	        					if(i===1){
	        						$(paginacao).append('<li class="active"><a href="#">'+i+'</a></li>');
	        					}else{
	        						$(paginacao).append('<li><a href="#">'+i+'</a></li>');
	        					}
	        					
	        				}
	        				$(paginacao).append('<li><a href="#" class="proximo">&raquo;</a></li>');
	        			}
	        			
	        			
	        			$(menuResultado).append(menuItem);
	        			$(conteudoItem).append(lista);
	        			$(conteudoItem).append(paginacao);
	        			$(conteudoResultado).append(conteudoItem);
	        			
	        			$(conteudoResultado).find('.pagination li a').not('.proximo, .anterior').click(function(){
	        				var numero = $(this).text();
	        				if(numero === '1'){
	        					$(conteudoResultado).find('a.anterior').parent('li').addClass('disabled');
	        					$(conteudoResultado).find('a.proximo').parent('li').removeClass('disabled');
	        				}else if(numero === ($(this).parents('ul').children('li').length - 2)){
	        					$(conteudoResultado).find('a.proximo').parent('li').addClass('disabled');
	        					$(conteudoResultado).find('a.anterior').parent('li').removeClass('disabled');
	        				}else{
	        					$(conteudoResultado).find('a.anterior,a.proximo').parent('li').removeClass('disabled');
	        				}
	        				//                            console.log(numero);
	        				$(this).parents('ul').children('.active').removeClass('active');
	        				$(this).parent('li').addClass('active');
	        				$(this).parents('.tab-pane').children('.media-list').children('li').addClass('hide');
	        				$(this).parents('.tab-pane').children('.media-list').children('li').hide();
	        				var pMax = (numero * itensPorPagina);
	        				var pMin = pMax - itensPorPagina;
	        				for(var i = pMin;i < pMax;i++){
	        					$(this).parents('.tab-pane').eq(0).children('.media-list').children('li').eq(i).removeClass('hide');
	        					$(this).parents('.tab-pane').eq(0).children('.media-list').children('li').eq(i).show();
	        				}
	        			});
	        			$(conteudoResultado).find('.pagination li a.proximo').click(function(){
	        				var atual = $(this).parents('ul').children('.active').index();
	        				var total = ($(this).parents('ul').children('li').length - 2);
	        				//                            console.log(atual,total);
	        				if(atual < total) {
	        					$(this).parents('ul').children('li').eq(atual + 1).children('a').click();
	        				}
	        			});
	        			$(conteudoResultado).find('.pagination li a.anterior').click(function(){
	        				var atual = $(this).parents('ul').children('.active').index();
	        				var total = ($(this).parents('ul').children('li').length - 2);
	        				//                            console.log(atual,total);
	        				if(atual > 1) {
	        					$(this).parents('ul').children('li').eq(atual - 1).children('a').click();
	        				}
	        			});
	        			$(conteudoResultado).find('.media-list').find('a').click(function(e){
	        				e.preventDefault();
	        				detalharPesquisaTextual($(this).attr('href'));
	        			});
	        			
	        		}
	        	});
	        	
	        }
	        if (!encontrou) {
	        	$(conteudoResultado).append('<p class="texto-erro">Nada foi localizado com o parâmetro informado!</p>');
	        }
	            $(menuResultado).tab();
	            $(menuResultado).children('li').eq(0).children('a').eq(0).click();	            
	    });
	});
});

function executarLogout() {
	$("#formLogout").attr("action", "logout");
	$("#formLogout").submit();
}
