@include('components.admin_navigation_layout')
@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div class="container">

    <h1>Краткая справка о пользователе {{ $user->email }}</h1>

    <!-- Возраст пользователя -->
    @php
        $birthdate = optional($user->personalInformation)->birth_date ? \Carbon\Carbon::parse($user->personalInformation->birth_date) : null;
        $age = $birthdate ? $birthdate->age : 'Данные не предоставлены';
    @endphp
    <p>Возраст: {{ $age }} лет</p>

    <!-- ФИО пользователя -->
    <p>
        ФИО:
        {{ optional($user->personalInformation)->last_name ?? 'Данные не предоставлены' }}
        {{ optional($user->personalInformation)->first_name ?? '' }}
        {{ optional($user->personalInformation)->middle_name ?? '' }}
    </p>

    <!-- Количество баллов пользователя -->
    <p>Количество баллов: {{ $user->points }}</p>

    <h2>Посещенные мероприятия:</h2>

    <ul class="list-group">
        @if($attendedEvents->isEmpty())
            <li class="list-group-item">Пользователь не посещал мероприятия</li>
        @else
            @foreach($attendedEvents as $event)
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    {{ $event->name }}
                    <form action="{{ route('users.unregister', ['userId' => $user->id, 'eventId' => $event->id]) }}"
                          method="POST"
                          class="d-flex align-items-center m-0"
                    >
                        @csrf
                        @method('DELETE')
                        <button type="submit" class="btn btn-danger">Дерегистрировать</button>
                    </form>
                </li>
            @endforeach
        @endif
    </ul>


    <h2>Доступные мероприятия:</h2>
    <ul class="list-group">
        @foreach($availableEvents as $event)
            <li class="list-group-item d-flex justify-content-between align-items-center">
                {{ $event->name }}
                <form action="{{ route('user.register.event', ['userId' => $user->id, 'eventId' => $event->id]) }}"
                      method="POST" class="d-flex align-items-center m-0">
                    @csrf
                    <button type="submit" class="btn btn-primary m-0">Зарегистрироваться</button>
                </form>
            </li>
        @endforeach
    </ul>
</div>
