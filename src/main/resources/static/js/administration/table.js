$(document).ready(function () {

    $('#products_table').DataTable({
        "language": {
            "lengthMenu": "Prikaži _MENU_ proizvoda",
            "zeroRecords": "Nema pronađenih proizvoda",
            "info": "Prikazano od _START_ do _END_ od ukupno _TOTAL_ proizvoda",
            "infoEmpty": "Nema pronađenih proizvoda",
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

    $('#orders_table').DataTable({
        "language": {
            "lengthMenu": "Prikaži _MENU_ porudžbina",
            "zeroRecords": "Nema pronađenih porudžbina",
            "info": "Prikazano od _START_ do _END_ od ukupno _TOTAL_ porudžbina",
            "infoEmpty": "Nema pronađenih porudžbina",
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

    $('#customers_table').DataTable({
        "language": {
            "lengthMenu": "Prikaži _MENU_ korisnika",
            "zeroRecords": "Nema pronađenih korisnika",
            "info": "Prikazano od _START_ do _END_ od ukupno _TOTAL_ korisnika",
            "infoEmpty": "Nema pronađenih korisnika",
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

    $('#messages_table').DataTable({
        "language": {
            "lengthMenu": "Prikaži _MENU_ poruka",
            "zeroRecords": "Nema pronađenih poruka",
            "info": "Prikazano od _START_ do _END_ od ukupno _TOTAL_ poruka",
            "infoEmpty": "Nema pronađenih poruka",
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

    $('.list-group li').on('click', function () {
        $('#products_table').DataTable().column(7).search(
            $(this).find('span').text()
        ).draw();
    });

    $(".list-group li:first").on('click', function (e) {
        location.reload();
    });

    $(".list-group li:first").addClass("active");

    $(".list-group li").on('click', function (e) {
        $(".list-group li").removeClass("active");
        $(this).addClass("active");
    });
});




