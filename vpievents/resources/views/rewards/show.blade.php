@include('components.admin_navigation_layout')
@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div class="container mt-5">
    <h1>{{ $reward->name }}</h1>
    <p>Необходимые баллы: {{ $reward->points_required }}</p>
    @if ($reward->image)
        <p><img src="{{ asset('storage/' . $reward->image) }}" alt="{{ $reward->name }}" class="img-fluid"></p>
    @endif
    <p>Количество: {{ $reward->quantity }}</p>
    <p>Статус: {{ $reward->status->name }}</p>
    <a href="{{ route('rewards.edit', $reward->id) }}" class="btn btn-warning">Редактировать</a>
    <form action="{{ route('rewards.destroy', $reward->id) }}" method="POST" style="display:inline-block;">
        @csrf
        @method('DELETE')
        <button type="submit" class="btn btn-danger">Удалить</button>
    </form>
    <a href="{{ route('rewards.index') }}" class="btn btn-secondary mt-3">Назад к списку</a>
</div>
