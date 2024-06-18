import './bootstrap';

import 'bootstrap';

import '@popperjs/core';

import $ from 'jquery';
// import 'bootstrap';  // Если вы используете Bootstrap

$(document).ready(function() {
    console.log("jQuery is ready!");  // Проверка загрузки jQuery
    $('#emailModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var email = button.data('email');
        var modal = $(this);
        modal.find('.modal-body #emailInput').val(email);
    });
});

//
// // Пример использования jQuery
// $(document).ready(function() {
//     $('#emailModal').on('show.bs.modal', function (event) {
//         var button = $(event.relatedTarget); // Button that triggered the modal
//         var email = button.data('email'); // Extract info from data-* attributes
//         var modal = $(this);
//         modal.find('.modal-body #emailInput').val(email);
//     });
// });
