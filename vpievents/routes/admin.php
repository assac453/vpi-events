<?php

use App\Http\Controllers\Admin\EventController;
use App\Http\Controllers\Admin\RewardController;
use App\Http\Controllers\Admin\UserController;
use App\Http\Controllers\Auth\AdminAuthController;
use App\Http\Controllers\Email\EmailController;
use App\Http\Controllers\Email\NewPasswordController;
use App\Http\Controllers\Email\PasswordResetLinkController;
use App\Http\Controllers\Email\VerificationController;
use App\Http\Controllers\EventTypeController;
use App\Http\Controllers\Notification\PushNotificationController;
use App\Http\Controllers\Reports\ReportsController;
use App\Http\Controllers\Request\RequestController;
use Illuminate\Support\Facades\Route;


Route::prefix('/admin')->group(function () {
    Route::prefix('/authentication')->group(function () {
        Route::get('/login', [AdminAuthController::class, 'showLoginForm'])->name('admin.login');
        Route::post('/login', [AdminAuthController::class, 'login'])->name('admin.login.post');
        Route::get('/register', [AdminAuthController::class, 'showRegisterForm'])->name('admin.register');
        Route::post('/register', [AdminAuthController::class, 'register'])->name('admin.register.post');
        Route::post('/logout', [AdminAuthController::class, 'logout'])->name('admin.logout');
        Route::get('/forgot-password', function () {
            return view('forms.forgot-password');
        })->name('admin.forgot.password');
        Route::post('/forgot-password', [PasswordResetLinkController::class, 'store'])
            ->name('admin.forgot.password.post');
        Route::get('reset-password/{token}', [NewPasswordController::class, 'create'])
            ->name('password.reset');
    });


    Route::middleware(['auth'])->group(function () {

        Route::view('/dashboard', 'admin.admin-dashboard')->name('admin.dashboard');

        Route::post('/change-password', [AdminAuthController::class, 'changePassword'])->name('admin.change-password');
        Route::post('/confirm-email', [AdminAuthController::class, 'confirmEmail'])->name('admin.confirm-email');
        Route::post('/send-email', [EmailController::class, 'send'])->name('send.email');
        Route::post('/send-emails', [EmailController::class, 'sends'])->name('send.bulk.email');

        Route::get('verify-email/{token}', [VerificationController::class, 'verifyEmail'])->name('verify.email');



        Route::prefix('/notifications')->group(function (){
            Route::post('/send-by-criteria', [PushNotificationController::class, 'sendNotificationsByCriteria'])->name('notifications.send.by.criteria');
            Route::get('/', [PushNotificationController::class, 'index'])->name('notification.index');
            Route::post('/send-by-event', [PushNotificationController::class, 'sendNotificationsByEvent'])->name('notifications.send.by.event');
            Route::get('/layout', [PushNotificationController::class, 'notificationLayout'])->name('notification.layout');
            Route::get('/history', [PushNotificationController::class, 'history'])->name('admin.notifications.history');
        });



        Route::view('/', 'admin.admin_home')->name('admin.index');

        Route::prefix('/users')->group(function () {
            Route::view('/', 'layouts.users_layout')->name('users.main');
            Route::prefix('/section')->group(function () {
                Route::get('/', [UserController::class, 'index'])->name('users.index');
                Route::get('/create', [UserController::class, 'create'])->name('users.create');
                Route::post('/', [UserController::class, 'store'])->name('users.store');
                Route::get('/{user}', [UserController::class, 'show'])->name('users.show');
                Route::get('/{user}/edit', [UserController::class, 'edit'])->name('users.edit');
                Route::post('/user/{userId}/register/event/{eventId}', [UserController::class, 'registerEvent'])->name('user.register.event');
                Route::get('/{user}/events', [UserController::class, 'events'])->name('users.events');
                Route::put('/{user}', [UserController::class, 'update'])->name('users.update');
                Route::delete('/{userId}/events/{eventId}/unregister', [UserController::class, 'unregisterUserFromEvent'])->name('users.unregister');
                Route::delete('/{user}', [UserController::class, 'destroy'])->name('users.delete');
            });
        });

        Route::prefix('/reports')->group(function () {
            Route::view('/', 'layouts.reports_layout')->name('reports.main');
            Route::get('/events', [ReportsController::class, 'events'])->name('reports.events');
            Route::get('/users', [ReportsController::class, 'users'])->name('reports.users');
            Route::get('/events/{eventId}/download', [ReportsController::class, 'downloadEventReport'])->name('reports.event.download');
            Route::get('/users/{userId}/download', [ReportsController::class, 'downloadUserReport'])->name('reports.user.download');
        });

        Route::prefix('/events')->group(function () {
            Route::view('/', 'layouts.events_main_layout')->name('event.main');
            Route::prefix('/section')->group(function () {
                Route::get('/', [EventController::class, 'index'])->name('event.index');
                Route::get('/create', [EventController::class, 'create'])->name('event.create');
                Route::post('/', [EventController::class, 'store'])->name('event.store');
                Route::get('/{event}', [EventController::class, 'show'])->name('event.show');
                Route::get('/{event}/edit', [EventController::class, 'edit'])->name('event.edit');
                Route::put('/{event}', [EventController::class, 'update'])->name('event.update');
                Route::delete('/{event}', [EventController::class, 'destroy'])->name('event.delete');
                Route::get('/{event}/qrcode', [EventController::class, 'qrcode'])->name('event.qrcode');
                Route::get('/{event}/report', [EventController::class, 'report'])->name('event.report');
            });
        });

        Route::prefix('rewards')->group(function (){
            Route::get('/', [RewardController::class, 'index'])->name('rewards.index');
            Route::get('/create', [RewardController::class, 'create'])->name('rewards.create');
            Route::post('/', [RewardController::class, 'store'])->name('rewards.store');
            Route::get('/{id}', [RewardController::class, 'show'])->name('rewards.show');
            Route::get('/{id}/edit', [RewardController::class, 'edit'])->name('rewards.edit');
            Route::put('/{id}', [RewardController::class, 'update'])->name('rewards.update');
            Route::delete('/{id}', [RewardController::class, 'destroy'])->name('rewards.destroy');
        });

        Route::prefix('requests')->group(function (){
            Route::get('/', [RequestController::class, 'index'])->name('requests.index');
        });


        Route::prefix('/event-type')->group(function () {
            Route::get('/create', [EventTypeController::class, 'create'])->name('event_type.create');
            Route::post('/store/{eventId?}', [EventTypeController::class, 'store'])->name('event_type.store');
        });
    });
});

