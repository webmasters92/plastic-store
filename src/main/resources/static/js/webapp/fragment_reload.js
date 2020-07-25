$(document).ready(function () {
    $(".category_fragment").on('click', function (e) {
        e.preventDefault();
        $.ajax({
            url: '/product/product_list_fragment_category/' + $(".category_fragment").attr('id'),
            type: 'get',
            success: function (response) {
                if ($(response).find('.has-error').length) {
                    $("#products_fragment").replaceWith(response);
                } else {
                    $("#products_fragment").replaceWith(response);
                }
            }
        });
    });
});

