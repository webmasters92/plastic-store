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
        },
        "autoWidth": false,
        "columnDefs": [
            {className: "dt-head-center", targets: [0, 1, 2, 3, 4, 5, 6, 7]},
            {"width": "15", "targets": 0},
            {"width": "350", "targets": 1},
            {"width": "90", "targets": 2},
            {"width": "70", "targets": 3},
            {"width": "30", "targets": 4},
            {"width": "30", "targets": 5},
            {"width": "70", "targets": 6},
            {"width": "15", "targets": 7}
        ]
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

    $('#promotions_table').DataTable({
        "language": {
            "lengthMenu": "Prikaži _MENU_ promocija",
            "zeroRecords": "Nema pronađenih promocija",
            "info": "Prikazano od _START_ do _END_ od ukupno _TOTAL_ promocija",
            "infoEmpty": "Nema pronađenih promocija",
            "infoFiltered": "",
            "search": "Pretraži:",
            "paginate": {
                "first": "Prva",
                "last": "Poslednja",
                "next": "Sledeća",
                "previous": "Prethodna"
            }
        },
        "autoWidth": false,
        "columnDefs": [
            {"width": "5", "targets": 0},
            {"width": "80", "targets": 1},
            {"width": "300", "targets": 2},
            {"width": "30", "targets": 3}
        ]
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




