<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Event;
use App\Models\Reward;
use App\Models\Status;
use App\Models\User;
use App\Models\UserPoint;
use App\Models\UserReward;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Validator;

class EventApiController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function getAll()
    {
        $events = Event::with('event_type')->get();
//        $events = Event::all();
        return response()->json($events);
    }

    public function redeemReward(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'user_id' => 'required|uuid|exists:users,id',
            'reward_id' => 'required|uuid|exists:rewards,id',
        ]);

        if ($validator->fails()) {
            return response()->json(['errors' => $validator->errors()], 400);
        }

        $userId = $request->input('user_id');
        $rewardId = $request->input('reward_id');

        $reward = Reward::findOrFail($rewardId);
        $user = User::findOrFail($userId);

        if ($reward->status == 'out_of_stock' || $reward->quantity < 1) {
            return response()->json(['error' => 'Reward is out of stock.'], 400);
        }

        // Подсчёт доступных баллов пользователя
        $availablePoints = UserPoint::where('user_id', $userId)
            ->where('expires_at', '>', now())
            ->sum('points');

        if ($availablePoints < $reward->points_required) {
            return response()->json(['error' => 'Not enough points.'], 400);
        }

        // Уменьшить баллы пользователя
        $pointsNeeded = $reward->points_required;
        $userPoints = UserPoint::where('user_id', $userId)
            ->where('expires_at', '>', now())
            ->orderBy('earned_at')
            ->get();

        foreach ($userPoints as $userPoint) {
            if ($userPoint->points >= $pointsNeeded) {
                $userPoint->points -= $pointsNeeded;
                $userPoint->save();
                break;
            } else {
                $pointsNeeded -= $userPoint->points;
                $userPoint->points = 0;
                $userPoint->save();
            }
        }

        // Обновить количество награды
        $reward->quantity -= 1;
        if ($reward->quantity == 0) {
            $reward->status = 'out_of_stock';
        }
        $reward->save();

        // Запись о заявке на получение награды
        $status = Status::where('name', 'Pending')->first();
        $userReward = new UserReward();
        $userReward->user_id = $userId;
        $userReward->reward_id = $rewardId;
        $userReward->status_id = $status->id;
        $userReward->requested_at = now();
        $userReward->save();

        return response()->json(['success' => 'Reward request submitted successfully.']);
    }


    public function register(Request $request)
    {
        Log::debug($request);
        $validator = Validator::make($request->all(), [
            'user_id' => 'required|uuid|exists:users,id',
            'event_id' => 'required|uuid|exists:event_models,id',
            'longitude' => 'required|numeric',
            'latitude' => 'required|numeric',
        ]);
        Log::debug($validator->fails());

        if ($validator->fails()) {
            return response()->json(['errors' => $validator->errors()], 400)
                ->header('Content-Type', 'application/json; charset=utf-8');
        }

        $userId = $request->input('user_id');
        $eventId = $request->input('event_id');
        $userLongitude = $request->input('longitude');
        $userLatitude = $request->input('latitude');

        $event = Event::findOrFail($eventId);
        $user = User::findOrFail($userId);

        // Check if the user is already registered for the event
        if ($event->users()->where('user_id', $userId)->exists()) {
            return response()->json(['error' => 'Пользователь уже зарегистрирован на это мероприятие.'], 400)
                ->header('Content-Type', 'application/json; charset=utf-8');
        }

        // Calculate the distance between the user and the event location
        $distance = $this->calculateDistance($event->latitude, $event->longitude, $userLatitude, $userLongitude);

        if ($distance > 0.5) { // 0.5 kilometers is 500 meters
            return response()->json(['error' => 'Пользователь находится за пределами 500-метрового радиуса мероприятия.'], 400)
                ->header('Content-Type', 'application/json; charset=utf-8');
        }

        // Register the user for the event
        $event->users()->attach($userId);

        // Update user points
        $user->points += $event->points;
        $user->save();

        return response()->json(['success' => 'Пользователь успешно зарегистрирован на мероприятие и награжден баллами ('.$event->points .').'])
            ->header('Content-Type', 'application/json; charset=utf-8');
    }



    private function calculateDistance($lat1, $lon1, $lat2, $lon2)
    {
        $earthRadius = 6371; // Earth radius in kilometers

        $dLat = deg2rad($lat2 - $lat1);
        $dLon = deg2rad($lon2 - $lon1);

        $a = sin($dLat / 2) * sin($dLat / 2) +
            cos(deg2rad($lat1)) * cos(deg2rad($lat2)) *
            sin($dLon / 2) * sin($dLon / 2);

        $c = 2 * atan2(sqrt($a), sqrt(1 - $a));

        $distance = $earthRadius * $c;

        return $distance;
    }


    public function updateRewardRequestStatus(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'user_reward_id' => 'required|uuid|exists:user_rewards,id',
            'status_id' => 'required|uuid|exists:statuses,id',
        ]);

        if ($validator->fails()) {
            return response()->json(['errors' => $validator->errors()], 400);
        }

        $userRewardId = $request->input('user_reward_id');
        $statusId = $request->input('status_id');

        $userReward = UserReward::findOrFail($userRewardId);
        $userReward->status_id = $statusId;
        if ($statusId == Status::where('name', 'Completed')->first()->id) {
            $userReward->closed_at = now();
        }
        $userReward->save();

        return response()->json(['success' => 'Reward request status updated successfully.']);
    }


    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(string $id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        //
    }
}
