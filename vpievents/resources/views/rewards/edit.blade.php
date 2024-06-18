@include('components.admin_navigation_layout')
@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div class="container mt-5">
    <h1>Редактировать награду</h1>
    @if ($errors->any())
        <div class="alert alert-danger">
            <ul>
                @foreach ($errors->all() as $error)
                    <li>{{ $error }}</li>
                @endforeach
            </ul>
        </div>
    @endif
    <form action="{{ route('rewards.update', $reward->id) }}" method="POST" enctype="multipart/form-data" class="form">
        @csrf
        @method('PUT')
        <div class="form-group">
            <label for="name">Название:</label>
            <input type="text" name="name" id="name" value="{{ $reward->name }}" class="form-control">
        </div>
        <div class="form-group">
            <label for="points_required">Необходимые баллы:</label>
            <input type="number" name="points_required" id="points_required" value="{{ $reward->points_required }}" class="form-control">
        </div>
        <div class="form-group">
            <label for="image">Изображение:</label>
            <input type="file" name="image" id="image" class="form-control-file">
        </div>
        <div class="form-group">
            <label for="quantity">Количество:</label>
            <input type="number" name="quantity" id="quantity" value="{{ $reward->quantity }}" class="form-control">
        </div>
        <div class="form-group">
            <label for="status_id">Статус:</label>
            <select name="status_id" id="status_id" class="form-control">
                @foreach($statuses as $status)
                    <option value="{{ $status->id }}" {{ $reward->status_id == $status->id ? 'selected' : '' }}>{{ $status->name }}</option>
                @endforeach
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Обновить награду</button>
    </form>
    <a href="{{ route('rewards.index') }}" class="btn btn-secondary mt-3">Назад к списку</a>
</div>
