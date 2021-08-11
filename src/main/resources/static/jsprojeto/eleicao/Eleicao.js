/* global MessageToast, Eleicoes */

$(function () {
    $("#nome").focus();
    $('[data-bs-toggle="tooltip"]').tooltip();
    $("#formCadastro").submit(function (event) {
        if($("#dataInicio").val() && $("#dataFinal").val()) {
            var timeDataInicial = new Date($("#dataInicio").val()).getTime();
            var timeDataFinal = new Date($("#dataFinal").val()).getTime();
            if(timeDataInicial > timeDataFinal || timeDataFinal < timeDataInicial) {
                new Eleicoes.MessageToast().show("warning","Por favor conferir as datas!");
                return false;
            }
        }
    });
});