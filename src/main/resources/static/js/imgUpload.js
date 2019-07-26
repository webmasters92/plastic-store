$(document).ready(function () {
    //upload slike na formi za unos proizvoda
    $(document).on('change', '.btn-file :file', function () {
        var input = $(this),
            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [label]);
    });

    $('.btn-file :file').on('fileselect', function (event, label) {

        var input = $(this).parents('.input-group').find(':text'),
            log = label;

        if (input.length) {
            input.val(log);
        } else {
            if (log) alert(log);
        }

    });

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#img-upload').attr('src', e.target.result);
                $('#img-upload').removeAttr('hidden');
                $('.c1691').height($('.c1691').height() + 120);
                $('.c1867').height($('.c1867').height() + 120);
                $('.c1859').height($('.c1859').height() + 120);

            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#imgInp").change(function () {
        readURL(this);
    });

    //prikaz uspesnog cuvanja proizvoda
    $("#success-alert").hide();
    $("#btn-return").click(function showAlert() {
        $("#success-alert").fadeTo(2000, 500).slideUp(500, function () {
            $("#success-alert").slideUp(500);
        });
    });
});