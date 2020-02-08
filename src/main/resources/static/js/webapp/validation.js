$(document).ready(function () {
    $('#customer_zip_code').val("");
    $('#guest_zip_code').val("");

    $.validator.setDefaults({
        errorClass: "my-error-class",
        validClass: "my-valid-class",
        errorElement: "em",

        highlight: function (element, errorClass, validClass) {
            $(element).addClass(errorClass).removeClass(validClass);
            $(element.form).find("label[for=" + element.id + "]")
                .addClass(errorClass);
        },

        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass(errorClass).addClass(validClass);
            $(element.form).find("label[for=" + element.id + "]")
                .removeClass(errorClass);
        }
    });

    $("#account_form").validate({
        rules: {
            first: "required",
            last: "required",
            address: "required",
            zip_code: "required",
            city: "required",
            email: "required",
            phone: "required"
        }
    });

    $("#comment_form").validate({
        rules: {
            email: "required",
            name: "required",
            comment: "required"
        }
    });

    $("#contact-form").validate();

    $("#registration_form").validate({
        rules: {
            first_name: "required",
            last_name: "required",
            email: "required",
            username: "required",
            password: "required",
            confirm_password: "required"
        }
    });

    $("#checkout-form-validation").validate({
        rules: {
            customer_first_name: "required",
            guest_first_name: "required",
            customer_last_name: "required",
            guest_last_name: "required",
            customer_email: {
                required: true,
                email: true
            },
            guest_email: {
                required: true,
                email: true
            },
            customer_phone_number: {
                required: true,
                digits: true,
                max: 5
            },
            guest_phone_number: {
                required: true,
                digits: true,
                max: 5
            },
            customer_address: "required",
            guest_address: "required",
            customer_city: "required",
            guest_city: "required",

            customer_zip_code: {
                required: true,
                digits: true,
                range: [5, 5]
            },
            guest_zip_code: {
                required: true,
                digits: true,
                range: [5, 5]
            }
        }

    });
});
