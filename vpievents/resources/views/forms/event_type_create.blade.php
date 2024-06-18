<!-- resources/views/event_type/create.blade.php -->
@vite(['resources/sass/app.scss', 'resources/js/app.js'])
<div class="row">
    <div class="col-md-3">
        <!-- Включение левого сайдбара -->
        @include('components.admin_navigation_layout')
    </div>
    <!-- Основное содержимое страницы -->
    <div class="content">
        <div class="container col-9">
            <form action="{{ route('event_type.store') }}" method="POST">
                @csrf
                <div class="form-group">
                    <label for="name">Название типа события:</label>
                    <input type="text" name="name" id="name" class="form-control">
                </div>
                <button type="submit" class="btn btn-primary">Создать</button>
            </form>
        </div>
    </div>
</div>

