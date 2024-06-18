@include('components.admin_navigation_layout')
@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div>
    <div class="container">
        @if(session('success'))
            <div class="alert alert-success">
                {{ session('success') }}
            </div>
        @endif

        <h1>Пользователи</h1>
        <a href="{{ route('users.create') }}" class="btn btn-success">Добавить</a>
        <button type="button" class="btn btn-primary" id="sendBulkEmailButton" data-bs-toggle="modal"
                data-bs-target="#exampleModal1" >Отправить письмо выбранным
            пользователям
        </button>

        <div class="accordion mt-3" id="userAccordion">
            @foreach($users as $user)
                <div class="accordion-item">
                    <h2 class="accordion-header d-flex align-items-center" id="heading{{ $user->id }}">
                        <input class="form-check-input  user-checkbox m-2" type="checkbox" value="{{ $user->email }}"
                               id="userCheckbox{{ $user->id }}">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapse{{ $user->id }}" aria-expanded="false"
                                aria-controls="collapse{{ $user->id }}">
                            {{ $user->email }}
                        </button>
                    </h2>
                    <div id="collapse{{ $user->id }}" class="accordion-collapse collapse"
                         aria-labelledby="heading{{ $user->id }}" data-bs-parent="#userAccordion">
                        <div class="accordion-body">
                            <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                    data-bs-target="#exampleModal" data-email="{{ $user->email }}"
                                    data-bs-whatever="@mdo">
                                Отправить письмо
                            </button>

                            <a href="{{route('users.events', $user->id)}}" class="btn btn-secondary">Зарегистрировать на мероприятие</a>
{{--                            <form action="{{route('users.events', $user->id)}}" method="get">--}}
{{--                                <button type="submit" class="btn btn-primary">Зарегистрировать на мероприятие</button>--}}
{{--                            </form>--}}

                            <div class="table-responsive mt-3">
                                <h5>Личная информация</h5>
                                <table class="table table-bordered">
                                    <thead class="thead-light">
                                    <tr>
                                        <th>Фамилия</th>
                                        <th>Имя</th>
                                        <th>Отчество</th>
                                        <th>Дата рождения</th>
                                        <th>Пол</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    @if($user->personalInformation)
                                        <tr>
                                            <td>{{ $user->personalInformation->first_name }}</td>
                                            <td>{{ $user->personalInformation->last_name }}</td>
                                            <td>{{ $user->personalInformation->middle_name }}</td>
                                            <td>{{ $user->personalInformation->birth_date }}</td>
                                            <td>{{ $user->personalInformation->gender }}</td>
                                        </tr>
                                    @else
                                        <tr>
                                            <td colspan="5">Данные не предоставлены</td>
                                        </tr>
                                    @endif
                                    </tbody>
                                </table>

                                <h5>Дополнительная информация</h5>
                                <table class="table table-bordered">
                                    <thead class="thead-light">
                                    <tr>
                                        <th>Серия паспорта</th>
                                        <th>Номер паспорта</th>
                                        <th>Дата выдачи</th>
                                        <th>Адрес регистрации</th>
                                        <th>Адрес фактического проживания</th>
                                        <th>Сирота</th>
                                        <th>Инвалидность</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    @if($user->additionalInformation)
                                        <tr>
                                            <td>{{ $user->additionalInformation->passport_series }}</td>
                                            <td>{{ $user->additionalInformation->passport_number }}</td>
                                            <td>{{ $user->additionalInformation->passport_issue_date }}</td>
                                            <td>{{ $user->additionalInformation->registration_address }}</td>
                                            <td>{{ $user->additionalInformation->residential_address }}</td>
                                            <td>{{ $user->additionalInformation->disability ? 'Да' : 'Нет' }}</td>
                                            <td>{{ $user->additionalInformation->orphan ? 'Да' : 'Нет' }}</td>
                                        </tr>
                                    @else
                                        <tr>
                                            <td colspan="7">Данные не предоставлены</td>
                                        </tr>
                                    @endif
                                    </tbody>
                                </table>

                                <h5>Контактная информация</h5>
                                <table class="table table-bordered">
                                    <thead class="thead-light">
                                    <tr>
                                        <th>Телеграм</th>
                                        <th>Вконтакте</th>
                                        <th>Номер телефона</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    @if($user->contactInformation)
                                        <tr>
                                            <td>{{ $user->contactInformation->telegram }}</td>
                                            <td>{{ $user->contactInformation->vk }}</td>
                                            <td>{{ $user->contactInformation->phone }}</td>
                                        </tr>
                                    @else
                                        <tr>
                                            <td colspan="3">Данные не предоставлены</td>
                                        </tr>
                                    @endif
                                    </tbody>
                                </table>

                                <h5>Информация о школе</h5>
                                <table class="table table-bordered">
                                    <thead class="thead-light">
                                    <tr>
                                        <th>Школа</th>
                                        <th>Класс</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    @if($user->schoolInformation)
                                        <tr>
                                            <td>{{ $user->schoolInformation->school->name }}</td>
                                            <td>{{ $user->schoolInformation->class }}</td>
                                        </tr>
                                    @else
                                        <tr>
                                            <td colspan="2">Данные не предоставлены</td>
                                        </tr>
                                    @endif
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            @endforeach
        </div>
    </div>

    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Новое сообщение</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                </div>
                <div class="modal-body">
                    <form action="{{ route('send.email') }}" method="POST">
                        @csrf
                        <div class="modal-body">
                            <div class="form-group">
                                <input type="checkbox" id="sendToMobile" name="sendToMobile">
                                <label for="sendToMobile">Отправить уведомление на мобильное устройство</label>
                            </div>
                            <input type="hidden" name="email" id="emailInput">
                            <div class="form-group">
                                <label for="message">Текст для отправки</label>
                                <textarea class="form-control" id="message" name="message" rows="4" required></textarea>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                            <button type="submit" class="btn btn-primary">Отправить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="exampleModal1" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Новое сообщение</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                </div>
                <div class="modal-body">
                    <form action="{{ route('send.bulk.email') }}" method="POST" id="bulkEmailForm">
                        @csrf
                        <div class="modal-body">
                            <div class="form-group">
                                <input type="checkbox" id="sendToMobile" name="sendToMobile">
                                <label for="sendToMobile">Отправить уведомление на мобильное устройство</label>
                            </div>
                            <div class="form-group">
                                <label for="message">Текст для отправки</label>
                                <textarea class="form-control" id="message" name="message" rows="4" required></textarea>
                            </div>
                        </div>
                        <input type="hidden" name="emails" id="bulkEmailInput">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                            <button type="submit" class="btn btn-primary" id="btnSendMultiEmail">Отправить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        var exampleModal = document.getElementById('exampleModal');
        exampleModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget; // Button that triggered the modal
            var email = button.getAttribute('data-email'); // Extract info from data-* attributes
            var modal = this; // Use the modal instance
            var input = modal.querySelector('#emailInput'); // Select the input field within the modal
            input.value = email; // Update the input field with the email
        });

        var sendBulkEmailButton = document.getElementById('btnSendMultiEmail');
        sendBulkEmailButton.addEventListener('click', function () {
            var selectedEmails = [];
            var checkboxes = document.querySelectorAll('.user-checkbox:checked');
            checkboxes.forEach(function (checkbox) {
                selectedEmails.push(checkbox.value);
            });

            if (selectedEmails.length > 0) {
                var emailInput = document.getElementById('bulkEmailInput');
                emailInput.value = selectedEmails.join(',');
                var bulkEmailForm = document.getElementById('bulkEmailForm');
                bulkEmailForm.submit();
            } else {
                alert('Пожалуйста, выберите хотя бы одного пользователя.');
            }
        });
    });
</script>

{{--<form id="bulkEmailForm" action="{{ route('send.bulk.email') }}" method="POST" style="display: none;">--}}
{{--    @csrf--}}
{{--    <input type="hidden" name="emails" id="bulkEmailInput">--}}
{{--    <input type="hidden" name="message" value="Ваше сообщение для выбранных пользователей">--}}
{{--</form>--}}
