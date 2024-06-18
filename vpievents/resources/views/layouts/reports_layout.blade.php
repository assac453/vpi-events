<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <style>
        .center-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            justify-content: center;
        }
    </style>
    <title>@yield('app_name')</title>
    @vite(['resources/sass/app.scss', 'resources/js/app.js'])
</head>
<body>

<!-- resources/views/welcome.blade.php -->

<div class="row">
    <div class="col-md-3">
        @include('components.admin_navigation_layout')
    </div>
    <div class="col-md-8">
        <!-- Основное содержимое страницы -->
        <div class="content">
            <div class="my-5">
                <div class="card-header">
                    <h1>Отчёты</h1>
                </div>
            </div>
            <!-- Секции 2 на 2 -->
            <div class="row">
                <div class="col-md-6 mb-4">
                    <div class="card text-center">
                        <div class="card-body">
                            <h3 class="card-title">Мероприятия</h3>
                            <p class="card-text">Отчёты по мероприятиям</p>
                            <a href="{{route('reports.events')}}" class="btn btn-primary">Перейти</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 mb-4">
                    <div class="card text-center">
                        <div class="card-body">
                            <h3 class="card-title">Пользователи</h3>
                            <p class="card-text">Отчёты по пользователям</p>
                            <a href="{{route('reports.users')}}" class="btn btn-primary">Перейти</a>
                        </div>
                    </div>
                </div>
{{--                <div class="col-md-6 mb-4">--}}
{{--                    <div class="card text-center">--}}
{{--                        <div class="card-body">--}}
{{--                            <h3 class="card-title">Пользователи</h3>--}}
{{--                            <p class="card-text">Управление пользователями</p>--}}
{{--                            <a href="/users" class="btn btn-primary">Перейти</a>--}}
{{--                        </div>--}}
{{--                    </div>--}}
{{--                </div>--}}
{{--                <div class="col-md-6 mb-4">--}}
{{--                    <div class="card text-center">--}}
{{--                        <div class="card-body">--}}
{{--                            <h3 class="card-title">Заявки</h3>--}}
{{--                            <p class="card-text">Просмотр заявок</p>--}}
{{--                            <a href="/requests" class="btn btn-primary">Перейти</a>--}}
{{--                        </div>--}}
{{--                    </div>--}}
{{--                </div>--}}
            </div>
        </div>
    </div>
</div>


{{--<div class="container-fluid">--}}
{{--    <div class="row">--}}
{{--        @yield('admin_navigation')--}}
{{--        @yield('content')--}}
{{--    </div>--}}

{{--</div>--}}
</body>
</html>
