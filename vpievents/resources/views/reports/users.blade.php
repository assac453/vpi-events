@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div class="row">
    <div class="col-md-2">
        @include('components.admin_navigation_layout')
    </div>
    <div class="col-md-9">
        <!-- Основное содержимое страницы -->
        <div class="content">
            <h1>Отчеты по пользователям</h1>
            <table class="table table-striped">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Email</th>
                    <th scope="col">Дата создания</th>
                    <th scope="col">Дата обновления</th>
                    <th scope="col">Действие</th>

                </tr>
                </thead>
                <tbody>
                @foreach($users as $user)
                    <tr>
                        <td>{{ $user->id }}</td>
                        <td>{{ $user->email }}</td>
                        <td>{{ $user->created_at }}</td>
                        <td>{{ $user->updated_at }}</td>
                        <td><a href="{{ route('reports.user.download', $user->id) }}">Скачать отчет</a></td>
                    </tr>
                @endforeach
                </tbody>
            </table>

        </div>
    </div>
</div>

