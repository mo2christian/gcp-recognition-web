
$(document).ready(function(){

    $('#file').change(function(e){
        const imageCompressor = new ImageCompressor();
        const file = e.target.files[0];
        options = {
            quality: .4
        }

        imageCompressor.compress(file, options)
            .then((result) => {
                e.target.files[0] = result;
            })
            .catch((err) => {
                console.log(err);
            });

    });
});
