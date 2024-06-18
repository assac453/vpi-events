@include('components.admin_navigation_layout')
@vite(['resources/sass/app.scss', 'resources/js/app.js'])

<div class="container">
    <div class="row">
        <div class="col-md-3">
            <!-- Включение левого сайдбара -->
            @include('components.admin_navigation_layout')
        </div>
        <!-- Основное содержимое страницы -->
{{--        <div class="col-m-9">--}}
{{--            <h1>История уведомлений</h1>--}}

{{--            <div class="accordion" id="notificationAccordion">--}}
{{--                @foreach($users as $user)--}}
{{--                    <div class="accordion-item">--}}
{{--                        <h2 class="accordion-header" id="heading{{ $user->id }}">--}}
{{--                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse{{ $user->id }}" aria-expanded="false" aria-controls="collapse{{ $user->id }}">--}}
{{--                                {{ $user->email }}--}}
{{--                            </button>--}}
{{--                        </h2>--}}
{{--                        <div id="collapse{{ $user->id }}" class="accordion-collapse collapse" aria-labelledby="heading{{ $user->id }}" data-bs-parent="#notificationAccordion">--}}
{{--                            <div class="accordion-body">--}}
{{--                                @if ($user->notifications->isEmpty())--}}
{{--                                    <p>Нет уведомлений для этого пользователя.</p>--}}
{{--                                @else--}}
{{--                                    <div class="accordion" id="nestedAccordion{{ $user->id }}">--}}
{{--                                        @foreach($user->notifications as $notification)--}}
{{--                                            <div class="accordion-item">--}}
{{--                                                <h2 class="accordion-header" id="headingNotification{{ $notification->id }}">--}}
{{--                                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseNotification{{ $notification->id }}" aria-expanded="false" aria-controls="collapseNotification{{ $notification->id }}">--}}
{{--                                                        {{ $notification->title }}--}}
{{--                                                    </button>--}}
{{--                                                </h2>--}}
{{--                                                <div id="collapseNotification{{ $notification->id }}" class="accordion-collapse collapse" aria-labelledby="headingNotification{{ $notification->id }}" data-bs-parent="#nestedAccordion{{ $user->id }}">--}}
{{--                                                    <div class="accordion-body">--}}
{{--                                                        <p>{{ $notification->body }}</p>--}}
{{--                                                        <p><small>Отправлено: {{ $notification->sent_at }}</small></p>--}}
{{--                                                    </div>--}}
{{--                                                </div>--}}
{{--                                            </div>--}}
{{--                                        @endforeach--}}
{{--                                    </div>--}}
{{--                                @endif--}}
{{--                            </div>--}}
{{--                        </div>--}}
{{--                    </div>--}}
{{--                @endforeach--}}
{{--            </div>--}}
{{--        </div>--}}

        <div class="container">
            <h1>История уведомлений</h1>

            <div class="accordion" id="notificationAccordion">
                @foreach($users as $user)
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="heading{{ $user->id }}">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse{{ $user->id }}" aria-expanded="false" aria-controls="collapse{{ $user->id }}">
                                {{ $user->email }}
                            </button>
                        </h2>
                        <div id="collapse{{ $user->id }}" class="accordion-collapse collapse" aria-labelledby="heading{{ $user->id }}" data-bs-parent="#notificationAccordion">
                            <div class="accordion-body">
                                @if ($notificationsByUser->has($user->id))
                                    @foreach($notificationsByUser[$user->id] as $notification)
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="headingNotification{{ $notification->id }}">
                                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseNotification{{ $notification->id }}" aria-expanded="false" aria-controls="collapseNotification{{ $notification->id }}">
                                                    {{ $notification->title }}
                                                </button>
                                            </h2>
                                            <div id="collapseNotification{{ $notification->id }}" class="accordion-collapse collapse" aria-labelledby="headingNotification{{ $notification->id }}" data-bs-parent="#nestedAccordion{{ $user->id }}">
                                                <div class="accordion-body">
                                                    <p>{{ $notification->body }}</p>
                                                    <p><small>Отправлено: {{ $notification->sent_at }}</small></p>
                                                </div>
                                            </div>
                                        </div>
                                    @endforeach
                                @else
                                    <p>Нет уведомлений для этого пользователя.</p>
                                @endif
                            </div>
                        </div>
                    </div>
                @endforeach

            </div>
        </div>
    </div>
</div>
