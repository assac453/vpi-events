@vite(['resources/sass/app.scss', 'resources/js/app.js'])
<div class="container d-flex justify-content-center align-items-center" style="height: 100vh;">
    <div>
        <h1 class="text-center mb-4">Регистрация администратора</h1>
        <form action="{{ route('admin.register.post') }}" method="POST">
            @csrf
            <div class="form-group">
                <label for="email">Почта</label>
                <input type="email" name="email" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" name="password" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="password_confirmation">Подтверждение пароля</label>
                <input type="password" name="password_confirmation" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Зарегистрироваться</button>
            <a type="submit" class="btn btn-primary btn-block" href="{{route('admin.login')}}">Авторизироваться</a>
        </form>
    </div>
</div>
