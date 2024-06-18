<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Notification;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;

class NotificationApiController extends Controller
{
    public function index()
    {
        $notifications = Notification::all();
        return response()->json($notifications);
    }

    public function getNotificationByUserId(string $userId)
    {
        // Получить текущую дату и время
        $currentDate = Carbon::now();
        Log::debug($currentDate->toString());

        // Получить дату начала трех месяцев назад (начиная с первого дня текущего месяца)
        $startDate = $currentDate->subMonths(2)->startOfMonth();

        Log::debug($startDate->toString());
        // Получить дату конца текущего месяца
        $currentDate = Carbon::now();

        $endDate = $currentDate->endOfMonth();

        Log::debug($endDate->toString());
        // Получить уведомления, созданные за последние три месяца
        $notifications = Notification::where('user_id', $userId)
            ->whereDate('created_at', '>=', $startDate)
            ->whereDate('created_at', '<=', $endDate)
            ->get();

        Log::debug($notifications);
        return response()->json($notifications);
    }

}
