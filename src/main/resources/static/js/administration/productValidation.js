$(document).ready(function () {
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
                        min: 15,
                        message: 'Opis proizvoda mora imati najmanje 8 karaktera'
                    }
                }
            },
            imgData: {
                validators: {
                    notEmpty: {
                        message: 'Nije odabrana slika proizvoda'
                    }
                }
            }
        }
    })
});

