{{--
@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<head>
    <!-- Другие теги head -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
</head>


<div class="row">
    <div class="col-md-3">
        <!-- Включение левого сайдбара -->
        @include('components.admin_navigation_layout')
    </div>
    <!-- Основное содержимое страницы -->
    <div class="content">
        <div class="container col-9">
            <h1>Редактирование мероприятия</h1>
            <form action="{{ route('event_type.store', ['eventId' => $event->id]) }}" method="POST" style="display: inline;">
                @csrf
                <input type="text" name="name" placeholder="Название типа события" required>
                <button type="submit" class="btn btn-primary">Создать тип события</button>
            </form>
            <form action="{{ route('event.update', $event->id) }}" method="POST" enctype="multipart/form-data" class="form">
                @csrf <!-- Защита CSRF -->
                @method('PUT') <!-- Метод PUT для обновления -->

                <!-- Выбор типа события -->
                <div class="form-group">
                    <label for="event_type_id">Тип события:</label>
                    <select id="event_type_id" name="event_type_id" class="form-control">
                        @foreach($eventTypes as $eventType)
                            <option value="{{ $eventType->id }}" {{ $eventType->id == $event->event_type_id ? 'selected' : '' }}>
                                {{ $eventType->name }}
                            </option>
                        @endforeach
                    </select>
                    <!-- Форма для создания нового типа события -->
                </div>

                <!-- Остальные поля мероприятия -->
                <div class="form-group">
                    <label for="name">Название:</label>
                    <input type="text" id="name" name="name" class="form-control" value="{{ $event->name }}">
                    @error('name')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="address">Адрес проведения:</label>
                    <input type="text" id="address" name="address" class="form-control" value="{{ $event->address }}">
                    @error('address')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>


                <div id="map" style="height: 400px;"></div>

                <input type="hidden" id="latitude" name="latitude">
                <input type="hidden" id="longitude" name="longitude">


                <div class="form-group">
                    <label for="description">Описание:</label>
                    <textarea id="description" name="description" class="form-control">{{ $event->description }}</textarea>
                    @error('description')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="points">Баллы:</label>
                    <input type="number" id="points" name="points" class="form-control" value="{{ $event->points }}">
                    @error('points')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="image">Картинка:</label>
                    <input type="file" id="image" name="image" class="form-control">
                    @if ($event->image)
                        <img src="{{ asset('images/' . $event->image) }}" alt="Текущая картинка" style="max-width: 200px; margin-top: 10px;">
                    @endif
                </div>

                <div class="form-group">
                    <label for="begin">Дата начала:</label>
                    <input type="datetime-local" id="begin" name="begin" class="form-control" value="{{ $event->begin/*->format('Y-m-d\TH:i') */}}">
                    @error('begin')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="end">Дата окончания:</label>
                    <input type="datetime-local" id="end" name="end" class="form-control" value="{{ $event->end/*->format('Y-m-d\TH:i') */}}">
                    @error('end')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <button type="submit" class="btn btn-success form-control">Обновить</button>
            </form>
        </div>
    </div>
</div>


<script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
<script>
    var map = L.map('map').setView([48.802816, 44.747954], 13); // Установка начальной позиции в Волжском

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    var marker = L.marker([48.802816, 44.747954]).addTo(map); // Установка маркера в центре Волжского

    map.on('click', function(e) {
        marker.setLatLng(e.latlng);
        updateAddress(e.latlng.lat, e.latlng.lng); // Обновление адреса при клике на карту
    });

    // Функция для отправки запроса на геокодирование и обновления адреса
    function updateAddress(latitude, longitude) {
        fetch(`https://nominatim.openstreetmap.org/reverse?lat=${latitude}&lon=${longitude}&format=json`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('address').value = data.display_name; // Обновление поля адреса
            });
        document.getElementById('latitude').value = latitude; // Обновление поля широты
        document.getElementById('longitude').value = longitude; // Обновление поля долготы
    }

    // Обработчик события изменения адреса
    document.getElementById('address').addEventListener('change', function() {
        var address = this.value;
        fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${address}`)
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    var latitude = data[0].lat;
                    var longitude = data[0].lon;
                    marker.setLatLng([latitude, longitude]); // Установка маркера на карте
                    map.setView([latitude, longitude]); // Перемещение карты к новым координатам
                    document.getElementById('latitude').value = latitude; // Обновление поля широты
                    document.getElementById('longitude').value = longitude; // Обновление поля долготы
                }
            });
    });
</script>
--}}

@include('components.admin_navigation_layout')
@vite(['resources/sass/app.scss', 'resources/js/app.js'])


<head>
    <!-- Другие теги head -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
</head>
<div class="container">
    <div class="row">
        <div class="col-md-3">
            <!-- Включение левого сайдбара -->
            @include('components.admin_navigation_layout')
        </div>
        <!-- Основное содержимое страницы -->
        <div class="col-m-9">
            <h1>Редактирование мероприятия</h1>
            <form action="{{ route('event_type.store', ['eventId' => $event->id]) }}" method="POST" style="display: inline;">
                @csrf
                <input type="text" name="name" placeholder="Название типа события" required>
                <button type="submit" class="btn btn-primary">Создать тип события</button>
            </form>
            <form action="{{ route('event.update', $event->id) }}" method="POST" enctype="multipart/form-data" class="form">
                @csrf
                @method('PUT')

                <div class="form-group">
                    <label for="event_type_id">Тип события:</label>
                    <select id="event_type_id" name="event_type_id" class="form-control">
                        @foreach($eventTypes as $eventType)
                            <option value="{{ $eventType->id }}" {{ $eventType->id == $event->event_type_id ? 'selected' : '' }}>
                                {{ $eventType->name }}
                            </option>
                        @endforeach
                    </select>
                </div>

                <div class="form-group">
                    <label for="name">Название:</label>
                    <input type="text" id="name" name="name" class="form-control" value="{{ $event->name }}">
                    @error('name')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="address">Адрес проведения:</label>
                    <input type="text" id="address" name="address" class="form-control" value="{{ $event->address }}">
                    @error('address')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div id="map" style="height: 400px;"></div>

                <input type="hidden" id="latitude" name="latitude" value="{{ $event->latitude }}">
                <input type="hidden" id="longitude" name="longitude" value="{{ $event->longitude }}">

                <div class="form-group">
                    <label for="description">Описание:</label>
                    <textarea id="description" name="description" class="form-control">{{ $event->description }}</textarea>
                    @error('description')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="points">Баллы:</label>
                    <input type="number" id="points" name="points" class="form-control" value="{{ $event->points }}">
                    @error('points')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="image">Картинка:</label>
                    <input type="file" id="image" name="image" class="form-control">
                    @if ($event->image)
                        <img src="{{ asset('images/' . $event->image) }}" alt="Текущая картинка" style="max-width: 200px; margin-top: 10px;">
                    @endif
                </div>

                <div class="form-group">
                    <label for="begin">Дата начала:</label>
                    <input type="datetime-local" id="begin" name="begin" class="form-control" value="{{ $event->begin}}">
                    @error('begin')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="end">Дата окончания:</label>
                    <input type="datetime-local" id="end" name="end" class="form-control" value="{{ $event->end }}">
                    @error('end')
                    <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <button type="submit" class="btn btn-success form-control">Обновить</button>
            </form>
        </div>
    </div>
</div>

<script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
<script>
    var map = L.map('map').setView([{{ $event->latitude }}, {{ $event->longitude }}], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    var marker = L.marker([{{ $event->latitude }}, {{ $event->longitude }}]).addTo(map);

    map.on('click', function(e) {
        marker.setLatLng(e.latlng);
        updateAddress(e.latlng.lat, e.latlng.lng);
    });

    function updateAddress(latitude, longitude) {
        fetch(`https://nominatim.openstreetmap.org/reverse?lat=${latitude}&lon=${longitude}&format=json`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('address').value = data.display_name;
            });
        document.getElementById('latitude').value = latitude;
        document.getElementById('longitude').value = longitude;
    }

    document.getElementById('address').addEventListener('change', function() {
        var address = this.value;
        fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${address}`)
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    var latitude = data[0].lat;
                    var longitude = data[0].lon;
                    marker.setLatLng([latitude, longitude]);
                    map.setView([latitude, longitude]);
                    document.getElementById('latitude').value = latitude;
                    document.getElementById('longitude').value = longitude;
                }
            });
    });
</script>
