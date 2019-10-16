$(document).ready(function () {

    $('#example').DataTable({
        "language": {
            "lengthMenu": "Prikaži _MENU_ proizvoda",
            "zeroRecords": "Nema pronađenih proizvoda",
            "info": "Prikazano od _START_ do _END_ od ukupno _TOTAL_ proizvoda",
            "infoEmpty": "Nema pronađenih rekorda",
            "infoFiltered": "",
            "search": "Pretraži:",
            "paginate": {
                "first": "Prva",
                "last": "Poslednja",
                "next": "Sledeća",
                "previous": "Prethodna"
            }
        }
    });

    $(".list-group > a li:first").before('<li class="list-group-item" id="allProducts"><span>Svi proizvodi</span></li>');
    $(".list-group > a li:first").on('click', function (e) {
         location.reload();
    });
    $(".list-group a li:first").addClass("active");
    $('.list-group > a li').on('click', function (e) {
        var $this = $(this);
        $('#example').DataTable().column(7).search(
            $this.find('span').text()
        ).draw();
    });

    $(".list-group > a li").on('click',function (e) {
        $(".list-group > a li").removeClass("active");
        $(e.target).addClass("active");
    });

    $( '#navcol-1 ul li a' ).on( 'click', function () {
        $( '#navcol-1 ul' ).find( 'li.active' ).removeClass( 'active' );
        $( this ).parent( 'li' ).addClass( 'active' );
    });
});




