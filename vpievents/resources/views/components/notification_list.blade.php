@vite(['resources/sass/app.scss', 'resources/js/app.js'])
<div class="row">
    <div class="col-md-3">
        <!-- Включение левого сайдбара -->
        @include('components.admin_navigation_layout')
    </div>
    <!-- Основное содержимое страницы -->
    <div class="content">
        <div class="container col-9">
            @if(session('success'))
                <div class="alert alert-success">
                    <?= session('success')?>
                </div>
            @endif
            <h1>Уведомления по мероприятиям</h1>
            <h5>Отправка уведомлений пользователям, посетившим мероприятия</h5>
            @foreach($events as $event)
                <div class="accordion" id="eventsAccordion">
                    <div class="accordion-item m-2">
                        <h2 class="accordion-header" id="heading{{ $event->id }}">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#collapse{{ $event->id }}" aria-expanded="true"
                                    aria-controls="collapse{{ $event->id }}">
                                {{ $event->name }}
                            </button>
                        </h2>
                        <div id="collapse{{ $event->id }}" class="accordion-collapse collapse"
                             aria-labelledby="heading{{ $event->id }}" data-bs-parent="#eventsAccordion">
                            <div class="accordion-body">
                                <h3>Детали мероприятия</h3>
                                <p>Описание: {{ $event->description }}</p>
                                <p>Место проведения: {{ $event->address }}</p>
                                <p>Дата начала: {{ $event->begin }}</p>
                                <p>Дата окончания: {{ $event->end }}</p>
                                <p>Баллы: {{ $event->points }}</p>

                                <!-- Аккордеон для пользователей мероприятия -->
                                <div class="accordion" id="usersAccordion{{ $event->id }}">
                                    <div class="accordion-item">
                                        <h2 class="accordion-header" id="headingUsers{{ $event->id }}">
                                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                                    data-bs-target="#collapseUsers{{ $event->id }}"
                                                    aria-expanded="true"
                                                    aria-controls="collapseUsers{{ $event->id }}">
                                                Посетили мероприятие
                                            </button>
                                        </h2>
                                        <div id="collapseUsers{{ $event->id }}" class="accordion-collapse collapse"
                                             aria-labelledby="headingUsers{{ $event->id }}"
                                             data-bs-parent="#usersAccordion{{ $event->id }}">
                                            <div class="accordion-body m-0">
                                                @if($event->users->isEmpty())
                                                    <h3>Пользователей нет</h3>
                                                @else
                                                    <ul>
                                                        @foreach($event->users as $user)
                                                            <li>{{ $user->email }}</li>
                                                        @endforeach
                                                    </ul>
                                                @endif
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Форма отправки уведомления -->
                                <form action="{{ route('notifications.send.by.event') }}" method="POST">
                                    @csrf
                                    <input type="hidden" name="event_id" value="{{ $event->id }}">
                                    <div class="mb-3">
                                        <label for="notificationTitle" class="form-label">Заголовок уведомления</label>
                                        <input type="text" class="form-control" id="notificationTitle" name="title" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="notificationBody" class="form-label">Тело уведомления</label>
                                        <textarea class="form-control" id="notificationBody" name="body" required></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Отправить уведомление</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            @endforeach
        </div>
    </div>
</div>

