@include('components.admin_navigation_layout')
@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div>
    <div class="container">
        <h1>Все мероприятия</h1>
        <a href="{{route('event.create')}}" class="btn btn-success">Добавить</a>

        <table class="table">
            <thead>
            <tr>
                <th scope="col">Контекстные действия</th>
                <th scope="col">Название</th>
                <th scope="col">Адрес проведения</th>
                <th scope="col">Описание</th>
                <th scope="col">Баллы</th>
                <th scope="col">Фотография</th>
                <th scope="col">Тип мероприятия</th>
            </tr>
            </thead>
            <tbody>

            @foreach($events as $item)
                <tr>
                    <th scope="row">
                        <a href="{{ route('event.edit', $item->id) }}" class="btn btn-warning">Изменить</a>
                        <br><br>
                        <a href="{{ route('event.qrcode', $item->id) }}" target="_blank" class="btn btn-secondary">Получить QR-code</a>
                        <br><br>
{{--                        <a href="{{ route('event.report', $item->id) }}" class="btn btn-secondary">Сгенерировать отчёт</a>--}}
{{--                        <br><br>--}}
                        <form id="delete-form-{{$item->id}}" action="{{ route('event.delete', $item->id) }}"
                              method="POST">
                            @csrf
                            @method('DELETE')
                            <button type="submit" class="btn btn-danger" onclick="
        return confirm('Вы уверены, что хотите удалить этот элемент?') ? document.getElementById('delete-form-{{$item->id}}').submit() : false;
    ">Удалить
                            </button>
                        </form>
                    </th>
                    <td>{{$item->name}}</td>
                    <td>{{$item->address}}</td>
                    <td>{{$item->description}}</td>
                    <td>{{$item->points}}</td>
                    <td>
                        <img src="{{ asset('images/' . $item->image) }}" width="200"  alt="{{ asset('public/images/' . $item->image) }}">
                    </td>

                    <td>{{$item->event_type->name}}</td>
                </tr>
            @endforeach
            </tbody>
        </table>
    </div>
</div>

