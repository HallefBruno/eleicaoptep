/* global Swal */

var Eleicoes = Eleicoes || {};

Eleicoes.DialogoExcluir = (function () {

    function DialogoExcluir() {
        this.exclusaoBtn = $('.js-exclusao-btn');
    }

    DialogoExcluir.prototype.iniciar = function () {
        this.exclusaoBtn.on('click', onExcluirClicado.bind(this));

        if (window.location.search.indexOf('excluido') > -1) {
            Swal.fire('Pronto!', 'Excluído com sucesso!', 'success');
        }
    };

    function onExcluirClicado(evento) {
        event.preventDefault();
        var botaoClicado = $(evento.currentTarget);
        var url = botaoClicado.data('url');
        var objeto = botaoClicado.data('objeto');

        Swal.fire({
            title: 'Tem certeza?',
            text: 'Excluir "' + objeto + '"? Você não poderá recuperar depois.',
            showCancelButton: true,
            confirmButtonColor: '#DD6B55',
            confirmButtonText: 'Sim, exclua agora!'
        }).then((result) => {
            if (result.isConfirmed) {
                onExcluirConfirmado(url);
            }
        });
    }

    function onExcluirConfirmado(url) {
        $.ajax({
            url: url,
            method: 'DELETE',
            success: onExcluidoSucesso.bind(this),
            error: onErroExcluir.bind(this)
        });
    }

    function onExcluidoSucesso() {
        var urlAtual = window.location.href;
        var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
        var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
        window.location = novaUrl;
    }

    function onErroExcluir(e) {
        console.log('erro', e.responseText);
        Swal.fire('Oops!', e.responseText, 'error');
    }

    return DialogoExcluir;

}());


Eleicoes.LoadGif = (function () {

    function LoadGif() {}
    LoadGif.prototype.iniciar = function () {
        $(document).ajaxSend(function (event, jqxhr, settings) {
            $("#divLoading").addClass("loading");
        }.bind(this));
        $(document).ajaxComplete(function (event, jqxhr, settings) {
            $("#divLoading").removeClass("loading");
        }.bind(this));
    };
    return LoadGif;
}());

Eleicoes.MessageToast = (function () {
    
    function MessageToast() {};
    
    MessageToast.prototype.show = function (icon,message) {
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 8000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer);
                toast.addEventListener('mouseleave', Swal.resumeTimer);
            }
        });

        Toast.fire({
            icon: `${icon}`,
            text: `${message}`
        });
    };
    
    return MessageToast;
    
}());


$(function () {

    var dialogo = new Eleicoes.DialogoExcluir();
    dialogo.iniciar();
    
    
    var loadGif = new Eleicoes.LoadGif();
    loadGif.iniciar();
});