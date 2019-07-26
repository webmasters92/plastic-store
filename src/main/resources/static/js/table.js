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

    $('.list-group > li').on('click', function (e) {
        var $this = $(this);
        $('#example').DataTable().column(6).search(
            $this.find('span').text()
        ).draw();
    });

    $(".list-group li").click(function (e) {
        $(".list-group li").removeClass("active");
        $(e.target).addClass("active");
    });
});




