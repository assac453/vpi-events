<?php

use App\Http\Controllers\Admin\RewardController;
use App\Http\Controllers\Api\NotificationApiController;
use App\Http\Controllers\Api\RewardApiController;
use App\Http\Controllers\Api\UserApiController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use \App\Http\Controllers\Api\EventApiController;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::prefix('v1')->as('v1')->group(function () {
    Route::prefix('events')->as('events')->group(function () {
        Route::post('/register', [EventApiController::class, 'register']);
        Route::get('/', [EventApiController::class, 'getAll']);
    });
    Route::prefix('users')->as('users')->group(function () {
        Route::get('', [UserApiController::class, 'getAll']);
        Route::get('/all', [UserApiController::class, 'AllUsers']);
    });

    Route::prefix('rewards')->as('rewards')->group(function () {
        Route::get('/', [RewardApiController::class, 'index']);
        Route::post('redeem', [EventApiController::class, 'redeemReward']);
        Route::post('update-status', [EventApiController::class, 'updateRewardRequestStatus']);
    });

    Route::prefix('notifications')->as('notifications')->group(function (){
        Route::get('/', [NotificationApiController::class, 'index']);
        Route::get('/{userId}', [NotificationApiController::class, 'getNotificationByUserId']);
    });
    
});
