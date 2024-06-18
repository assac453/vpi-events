@include('components.admin_navigation_layout')
@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div class="container mt-5">
    <h1>Список наград</h1>
    <a href="{{ route('rewards.create') }}" class="btn btn-primary mb-3">Создать новую награду</a>
    @if (session('success'))
        <div class="alert alert-success">{{ session('success') }}</div>
    @endif
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Название</th>
            <th>Необходимые баллы</th>
            <th>Количество</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        @foreach ($rewards as $reward)
            <tr>
                <td>{{ $reward->name }}</td>
                <td>{{ $reward->points_required }}</td>
                <td>{{ $reward->quantity }}</td>
                <td>{{ $reward->status->name }}</td>
                <td>
                    <a href="{{ route('rewards.show', $reward->id) }}" class="btn btn-info btn-sm">Просмотр</a>
                    <a href="{{ route('rewards.edit', $reward->id) }}" class="btn btn-warning btn-sm">Редактировать</a>
                    <form action="{{ route('rewards.destroy', $reward->id) }}" method="POST" style="display:inline-block;">
                        @csrf
                        @method('DELETE')
                        <button type="submit" class="btn btn-danger btn-sm">Удалить</button>
                    </form>
                </td>
            </tr>
        @endforeach
        </tbody>
    </table>
</div>
