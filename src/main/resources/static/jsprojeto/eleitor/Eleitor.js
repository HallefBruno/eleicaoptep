$(function () {
    
    var context = $("meta[name=context]").prop("content");
    
    $('input[type="checkbox"]').on('change', function () {
        $('input[id="' + this.id + '"]').not(this).prop('checked', false);
    });
    
    $("#cpf").mask("000.000.000-00", {reverse: true});

});