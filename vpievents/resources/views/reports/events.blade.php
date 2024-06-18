@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div class="row">
    <div class="col-md-2">
        @include('components.admin_navigation_layout')
    </div>
    <div class="col-md-9">
        <!-- Основное содержимое страницы -->
        <div class="content">
            <h1>Отчеты по мероприятиям</h1>
            <table class="table table-striped">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Название</th>
                    <th scope="col">Дата начала</th>
                    <th scope="col">Дата окончания</th>
                    <th scope="col">Действие</th>
                </tr>
                </thead>
                <tbody>
                @foreach($events as $event)
                    <tr>
                        <td>{{ $event->name }}</td>
                        <td>{{ $event->begin }}</td>
                        <td>{{ $event->end }}</td>
                        <td><a href="{{ route('reports.event.download', $event->id) }}">Скачать отчет</a></td>
                    </tr>
                @endforeach
                </tbody>
            </table>
        </div>
    </div>
</div>
