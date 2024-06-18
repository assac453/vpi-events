@include('components.admin_navigation_layout')
@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div class="container">
    <div class="row">
        <div class="col-md-3">
            <!-- Включение левого сайдбара -->
            @include('components.admin_navigation_layout')
        </div>
        <!-- Основное содержимое страницы -->
        <div class="col-m-9">

            @if(session('success'))
                <div class="alert alert-success">
                        <?= session('success') ?>
                </div>
            @endif

            <h1>Уведомления пользователям</h1>

            <!-- Кнопка для перехода на страницу истории уведомлений -->
            <a href="{{ route('admin.notifications.history') }}" class="btn btn-secondary mb-3">История уведомлений</a>

            <form action="{{ route('notifications.send.by.criteria') }}" method="POST">
                @csrf

                <div class="form-group">
                    <label for="gender">Пол:</label>
                    <select id="gender" name="gender" class="form-control">
                        <option value="">Любой</option>
                        <option value="муж">Мужской</option>
                        <option value="жен">Женский</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="age">Возраст:</label>
                    <input type="number" id="age" name="age" class="form-control" placeholder="Введите возраст">
                </div>

                <div class="form-group">
                    <label for="class">Класс обучения:</label>
                    <input type="number" id="class" name="class" class="form-control" placeholder="Введите класс обучения">
                </div>

                <div class="form-group">
                    <label for="event_type">Тип посещенного мероприятия:</label>
                    <select id="event_type" name="event_type" class="form-control">
                        <option value="">Любой</option>
                        @foreach($eventTypes as $eventType)
                            <option value="{{ $eventType->id }}">{{ $eventType->name }}</option>
                        @endforeach
                    </select>
                </div>

                <div class="form-group">
                    <label for="title">Заголовок:</label>
                    <input type="text" id="title" name="title" class="form-control" placeholder="Введите заголовок" required>
                </div>

                <div class="form-group">
                    <label for="body">Сообщение:</label>
                    <textarea id="body" name="body" class="form-control" placeholder="Введите сообщение" required></textarea>
                </div>

                <button type="submit" class="btn btn-primary">Отправить уведомление</button>
            </form>
        </div>
    </div>
</div>
