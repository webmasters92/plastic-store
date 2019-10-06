$(document).ready(function () {

    //aktivacija dropdown menija
    $('#multiple-checkboxes').selectpicker();
    $('#category').selectpicker();
    $('#subcategory').selectpicker();

    //aktivacija dropdown menija
    $('#capacity').select2({
        tags: true,
        placeholder: "Unesite zapreminu"
    });
    $('#dimension').select2({
        tags: true,
        placeholder: "Unesite dimenzije"
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