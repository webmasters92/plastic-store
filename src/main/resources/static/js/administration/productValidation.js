$(document).ready(function () {
    $('#multiple-checkboxes').multiselect();

    $('form').bootstrapValidator({
        excluded: [':disabled', ':hidden', ':not(:visible)'],

        feedbackIcons: {
            valid: 'fa fa-check',
            invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            code: {
                validators: {
                    remote: {
                        url: 'product/checkCode',
                        type: 'GET',
                        message: "Sifra proizvoda vec postoji"
                    },
                    notEmpty: {
                        message: 'Sifra proizvoda nije uneta'
                    },
                    integer: {
                        message: 'Morate uneti broj'
                    }
                }
            },
            name: {
                validators: {
                    notEmpty: {
                        message: 'Naziv proizoda nije unet'
                    }
                }
            },
            price: {
                validators: {
                    notEmpty: {
                        message: 'Cena nije uneta'
                    },
                    integer: {
                        message: 'Morate uneti broj'
                    }
                }
            },
            description: {
                validators: {
                    notEmpty: {
                        message: 'Opis proizvoda nije unet'
                    },
                    stringLength: {
                        min: 5,
                        message: 'Opis proizvoda mora imati najmanje 15 karaktera'
                    }
                }
            }
        }
    });
});

$('form').on("submit", function () {
    var edit = $('#edit').val();
    if ($('#multiple-checkboxes').val().length == 0) {
        alert("Odaberte dostupne boje za izabrani proizvod");
        return false;
    }
    if (edit) {
        $("#fileUpload").removeAttribute('required');
    }
});

window.setTimeout(function() {
    $(".alert").fadeTo(500, 0).slideUp(500, function(){
        $(this).remove();
    });
}, 5000);
