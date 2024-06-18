@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div class="row">
    <div class="col-md-3">
        <!-- Включение левого сайдбара -->
        @include('components.admin_navigation_layout')
    </div>
    <!-- Основное содержимое страницы -->
    <div class="content">
        <div class="container col-9">
            <div >
                <h1>Админ Панель</h1>
                <p>Добро пожаловать в админскую панель.</p>



                @if(Auth::check())
                    <p>Вы авторизированы под {{ Auth::user()->email }}</p>
                    @if(Auth::user()->email_verified_at)
                        <p>Почта подтверждена</p>
                    @else
                        <p>Почта не подтверждена</p>
                    @endif
                @endif

                <form action="{{ route('admin.change-password') }}" method="POST">
                    @csrf
                    @if(session('success'))
                        <div class="alert alert-success">
                            {{ session('success') }}
                        </div>
                    @endif
                    <div class="form-group">
                        <label for="current_password">Текущий пароль</label>
                        <input type="password" name="current_password" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="new_password">Новый пароль</label>
                        <input type="password" name="new_password" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="new_password_confirmation">Подтвердите новый пароль</label>
                        <input type="password" name="new_password_confirmation" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Изменить пароль</button>
                </form>

                <form action="{{ route('admin.confirm-email') }}" method="POST">
                    @csrf
                    <button type="submit" class="btn btn-primary">Подтвердить почту</button>
                </form>

                <form id="logout-form" action="{{ route('admin.logout') }}" method="POST" style="display: none;">
                    @csrf
                    <button type="submit" href="{{ route('admin.logout') }}">
                        Выйти
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

