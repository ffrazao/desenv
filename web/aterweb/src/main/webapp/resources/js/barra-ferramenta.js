/**
 * C�digo maniputador da barra de ferramentas
 */

var barraFerramenta          = null;
var btnFerramentaExecutar    = null;
var btnFerramentaFiltrar     = null;
var btnFerramentaIncluir     = null;
var btnFerramentaEditar      = null;
var btnFerramentaExcluir     = null;
var btnFerramentaSalvar      = null;
var btnFerramentaCancelar    = null;
var btnFerramentaAcoes       = null;
var btnFerramentaExportar    = null;
var btnFerramentaRegAnterior = null;
var btnFerramentaRegProximo  = null;

$(function() {
    barraFerramenta          = $("#barra-ferramenta");
    btnFerramentaExecutar    = barraFerramenta.find("#btn_ferramenta_executar");
    btnFerramentaFiltrar     = barraFerramenta.find("#btn_ferramenta_filtrar");
    btnFerramentaIncluir     = barraFerramenta.find("#btn_ferramenta_incluir");
    btnFerramentaEditar      = barraFerramenta.find("#btn_ferramenta_editar");
    btnFerramentaExcluir     = barraFerramenta.find("#btn_ferramenta_excluir");
    btnFerramentaSalvar      = barraFerramenta.find("#btn_ferramenta_salvar");
    btnFerramentaCancelar    = barraFerramenta.find("#btn_ferramenta_cancelar");
    btnFerramentaAcoes       = barraFerramenta.find("#btn_ferramenta_acoes");
    btnFerramentaExportar    = barraFerramenta.find("#btn_ferramenta_exportar");
    btnFerramentaRegAnterior = barraFerramenta.find("#btn_ferramenta_registro_anterior");
    btnFerramentaRegProximo  = barraFerramenta.find("#btn_ferramenta_registro_proximo");
});