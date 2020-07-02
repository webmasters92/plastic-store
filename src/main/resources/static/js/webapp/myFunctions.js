function deleteFromMiniCart(size, price, color, code) {
    $.ajax({
        url: '/cart/delete_minicart_item/' + size + '/' + price + '/' + color + '/' + code,
        type: 'get',
        success: function (response) {
            $(".navigation-menu-top").replaceWith(response);
            $("#cart-icon").on("click", function (event) {
                event.stopPropagation();
                $("#cart-floating-box").slideToggle();
                $("#accountList").slideUp("slow");
                $("#languageList").slideUp("slow");
            });

            $("body:not(#cart-icon)").on("click", function () {
                $("#cart-floating-box").slideUp("slow");
            });
        }
    });
}