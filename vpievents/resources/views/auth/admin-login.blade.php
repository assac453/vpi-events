@vite(['resources/sass/app.scss', 'resources/js/app.js'])
<div class="container d-flex justify-content-center align-items-center" style="height: 100vh;">
    <div>
        <h1 class="text-center">Авторизация администратора</h1>
        <form action="{{ route('admin.login.post') }}" method="POST">
            @csrf
            <div class="form-group">
                <label for="email">Электронная почта</label>
                <input type="email" name="email" id="email" class="form-control" required>
                @error('email')
                    <div class="text-danger">{{ $message }}</div>
                @enderror
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" name="password" id="password" class="form-control" required>
                @error('password')
                    <div class="text-danger">{{ $message }}</div>
                @enderror
            </div>
            <button type="submit" class="btn btn-primary btn-block">Войти</button>
            <a class="btn btn-primary btn-block" href="{{route("admin.register")}}">Зарегистрироваться</a>

            <a class="btn btn-danger btn-block" href="{{route("admin.forgot.password")}}">Забыл пароль</a>
        </form>
{{--        <form method="get" action="{{route('admin.forgot.password')}}">--}}
{{--            @csrf--}}
{{--            <button class="btn btn-danger btn-block" type="submit">Забыл пароль</button>--}}
{{--        </form>--}}
    </div>
</div>
