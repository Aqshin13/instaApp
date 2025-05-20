 window.onload = function() {
            const button = document.getElementById('login-btn');
            const username = document.getElementById('username');
            const password = document.getElementById('password');
            const fullName = document.getElementById('fullName');

            const errorMessage=document.querySelectorAll(".error-message");
            console.log(errorMessage);
            function toggleButtonState() {
                if(errorMessage){
                errorMessage.forEach((x)=>{
                x.style.display="none"
                })
                }

                if (username.value && password.value) {
                  button.disabled = false;
                }
                 else {
                    button.disabled = true;
                }
            }

            username.addEventListener('input', toggleButtonState);
            password.addEventListener('input', toggleButtonState);
            fullName.addEventListener('input', toggleButtonState);

}