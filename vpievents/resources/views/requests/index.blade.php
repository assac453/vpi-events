@include('components.admin_navigation_layout')
@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div class="container">
    <h1>Заявки</h1>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Пользователь</th>
            <th>Награда</th>
            <th>Статус</th>
            <th>Дата создания</th>
            <th>Дата закрытия</th>
        </tr>
        </thead>
        <tbody>
        @foreach($requests as $request)
            <tr>
                <td>{{ $request->id }}</td>
                <td>{{ $request->user->email ?? 'данные не предоставлены' }}</td>
                <td>{{ $request->reward->name ?? 'данные не предоставлены' }}</td>
                <td>{{ $request->status->name ?? 'данные не предоставлены' }}</td>
                <td>{{ $request->created_at }}</td>
                <td>{{ $request->closed_at ?? 'не закрыта' }}</td>
            </tr>
        @endforeach
        </tbody>
    </table>
</div>
