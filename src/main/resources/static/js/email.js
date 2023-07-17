function validationMail() {
    // Przykładowy starter JavaScript do wyłączania przesyłania formularzy, jeśli istnieją nieprawidłowe pola
    (() => {
        'use strict'

        // Pobierz wszystkie formularze, do których chcemy zastosować niestandardowe style walidacji Bootstrap
        const forms = document.querySelectorAll('.needs-validation')

        // Zapętlaj je i zapobiegaj poddaniu się
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
    })()
}