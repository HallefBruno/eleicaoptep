$(function () {
    $("input[name=image]").change(function () {
        if (this.files && this.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('.card-img-top').attr('src', e.target.result);
            };
            reader.readAsDataURL(this.files[0]);
        }
    });

    $("#formNovoCandidato").submit(function () {
        $("#divLoading").addClass("loading");
        return true;
    });
});