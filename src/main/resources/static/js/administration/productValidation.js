$(document).ready(function () {
    //aktivacija dropdown menija
    $('#multiple-checkboxes').selectpicker();
    $('#category').selectpicker();
    $('#subcategory').selectpicker();

    $('#dimension').select2({
        tags: true,
        placeholder: "Unesite dimenzije"
    });

    $('#price').select2({
        tags: true,
        placeholder: "Unesite cene"
    });

    //event na promenu kategorije
    $("#category").on('change', function () {
        sendAjaxRequest();
    });
    //okidanje event-a za dropdown kategorije
    $("#category").trigger('change');

    //validacija forme za unos proizvoda
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
            sizes: {
                validators: {
                    notEmpty: {
                        message: 'Veličina nije uneta'
                    }
                }
            },
            prices: {
                validators: {
                    notEmpty: {
                        message: 'Cena nije uneta'
                    }
                }
            },
            imgData: {
                validators: {
                    notEmpty: {
                        message: 'Slike nisu unete'
                    }
                }
            },
            selectedColors: {
                validators: {
                    notEmpty: {
                        message: 'Boje nisu unete'
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

$('form').on("submit", function (event) {
    var edit = $('#edit').val();
    var dimensions = $('#dimension :selected').length;
    var prices = $('#price :selected').length;
    if (dimensions !== prices) {
        event.preventDefault();
        alert("Broj veličina i cena mora biti isti");
        location.reload();
    }
    if (edit) {
        $("#fileUpload").removeAttribute('required');
    }
});

//automatsko zatvaranje bootrstap popup-a
window.setTimeout(function () {
    $(".alert").fadeTo(500, 0).slideUp(500, function () {
        $(this).remove();
    });
}, 5000);

//slanje ajax zahteva za ucitavanje podkategorija
function sendAjaxRequest() {
    var category = $("#category").val();
    $.get("/sub_categories?category=" + category, function (data) {
        $("#subcategory").empty();
        data.forEach(function (item, i) {
            $('#subcategory').append($("<option></option>").attr("value", item.id).text(item.name));
        });
        $('#subcategory').selectpicker('refresh');
    });
};