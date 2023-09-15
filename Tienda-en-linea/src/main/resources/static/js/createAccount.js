function previsualizarImagen(event) {
    var input = event.target;
    var file = input.files[0];

    var compressor = new Compressor(file, {
        quality: 0.6,
        maxWidth: 800,
        maxHeight: 800,
        success: function (compressedResult) {
            var reader = new FileReader();
            reader.onload = function () {
                var dataURL = reader.result;
                var img = document.createElement("img");
                img.src = dataURL;
                var previsualizacion = document.getElementById("previsualizacion");
                previsualizacion.innerHTML = '';
                previsualizacion.appendChild(img);
            };
            reader.readAsDataURL(compressedResult);
        },
        error: function (error) {
        }
    });
}