function previewImage(event) {
    const input = event.target;
    const preview = document.getElementById('image-preview');
    const previewContainer = document.getElementById('preview-container');

    if (input.files && input.files[0]) {
        const reader = new FileReader();

        reader.onload = function(e) {
            preview.src = e.target.result;
            previewContainer.style.display = "block";
        }

        reader.readAsDataURL(input.files[0]);
    }

}

function clearInput() {
console.log("Helllo")
        document.getElementById("image").value = "";
        document.getElementById("preview-container").style.display = "none";
}


document.addEventListener("DOMContentLoaded", function() {
    var toastElement = document.getElementById('liveToast');

    setTimeout(function() {
        toastElement.classList.remove('show');
        toastElement.classList.add('hide');
        console.log("Hello")
    }, 3000);
});
