<style>
    .sidebar {
        height: 100%;
        width: 200px;
        position: fixed;
        top: 0;
        left: 0;
        background-color: #f4f4f4;
        /*background-color: #3a2785;*/
        padding-top: 20px;
    }

    .sidebar h2 {
        text-align: center;
        margin-bottom: 20px;
    }

    .sidebar nav ul {
        list-style-type: none;
        padding: 0;
        text-align: center;
    }

    .sidebar nav ul li {
        padding: 10px 0;
    }

    .sidebar nav ul li a {
        /*color: #333;*/
        color: #3a2785;
        text-decoration: none;
        font-size: 18px;
    }

    .sidebar nav ul li a:hover {
        color: #555;
    }
</style>


<div class="sidebar">
    <h2 style="color: #3a2785">ВПИ События</h2>
    <nav>
        <ul>
            <li><a href="{{route('admin.index')}}">Главная</a></li>
            <li><a href="{{route('event.main')}}">Мероприятия</a></li>
            <li><a href="{{route('reports.main')}}">Отчёты</a></li>
            <li><a href="{{route('users.main')}}">Пользователи</a></li>
            <li><a href="{{route('rewards.index')}}">Награды</a></li>
            <li><a href="{{route('requests.index')}}">Заявки</a></li>
            <li><a href="#">Школы</a></li>
            <li><a href="#">Экспортирование данных</a></li>
            <li></li>
            <li></li>
            <li></li>
            <li>
        </ul>
        <div style="position: absolute; bottom: 0; width: 100%;"> <!-- Зафиксировать последний пункт меню в нижней части боковой панели -->
            <ul>

                <li>Вы авторизированы: <span style="color: #3a2785; font-weight: bold"><a href="{{route('admin.dashboard')}}">{{ Auth::user()->email }}</a></span></li>
                <li>
                    <a href="{{ route('admin.logout') }}" onclick="event.preventDefault(); document.getElementById('logout-form').submit();">
                        Выйти
                    </a>
                    <form id="logout-form" action="{{ route('admin.logout') }}" method="POST" style="display: none;">
                        @csrf
                    </form>
                </li>
            </ul>
        </div>
    </nav>
</div>

