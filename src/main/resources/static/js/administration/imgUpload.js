$("#fileUpload").on('change', function (event) {
    //Get count of selected files
    var countFiles = $(this)[0].files.length;
    if (countFiles <= 4) {
        var imgPath = $(this)[0].value;
        var extn = imgPath.substring(imgPath.lastIndexOf('.') + 1).toLowerCase();
        var image_holder = $("#image-holder");
        image_holder.empty();

        if (extn == "png" || extn == "jpg" || extn == "jpeg") {
            if (typeof (FileReader) != "undefined") {
                for (var i = 0; i < countFiles; i++) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("<img />", {
                            "src": e.target.result,
                            "class": "thumb-image"
                        }).appendTo(image_holder);
                    };
                    image_holder.show();
                    reader.readAsDataURL($(this)[0].files[i]);
                }
            } else {
                alert("This browser does not support FileReader.");
                return false;
            }
        } else {
            alert("Izaberite samo slike");
            location.reload();
        }
    } else {
        alert("Maksimalan broj slika koje mozete uneti je 4");
        return false;
    }
});

