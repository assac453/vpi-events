<?php

namespace App\Http\Controllers\Notification;

use App\Http\Controllers\Controller;
use App\Models\Event;
use App\Models\EventType;
use http\Env\Url;
use Illuminate\Http\Request;
use Kreait\Firebase\Factory;
use Kreait\Firebase\Messaging\CloudMessage;
use App\Models\Notification;
use App\Models\User;
use Illuminate\Support\Str;
use Illuminate\Support\Facades\Log;

class PushNotificationController extends Controller
{
    protected $firebase;
    protected $database;
    protected $messaging;

    public function __construct()
    {
        $this->firebase = (new Factory)
            ->withServiceAccount(base_path('config/firebase_credentials.json'))
            ->withDatabaseUri('https://vpievents-cd315-default-rtdb.europe-west1.firebasedatabase.app');
        $this->database = $this->firebase->createDatabase();
        $this->messaging = $this->firebase->createMessaging();
    }

    public function index(Request $request)
    {
        $events = Event::all();
        return view('components.notification_list', ['events' => $events]);
    }

    public function history()
    {
        $notifications = Notification::with('user')->get();
        $notificationsByUser = $notifications->groupBy('user_id');
        $users = User::whereIn('id', $notifications->pluck('user_id'))->get();
        $notificationsByUser->each(function ($notifications) {
            $notifications->sortBy('created_at');
        });
        return view('components.notification_history', compact('notificationsByUser', 'users'));
    }

    public function notificationLayout(Request $request)
    {
        $eventTypes = EventType::all();
        return view('layouts.notification_layout', ['eventTypes' => $eventTypes]);
    }

    public function updateFirebaseToken($userId)
    {
        return $this->database->getReference('users/' . $userId . '/notification_token')->getValue();
    }

    public function sendPushNotification($token, $title, $body)
    {
        $message = CloudMessage::fromArray([
            'notification' => [
                'title' => $title,
                'body' => $body,
            ],
            'token' => $token,
        ]);

        try {
            $this->messaging->send($message);
        } catch (\Kreait\Firebase\Exception\Messaging\NotFound $e) {
            // Handle the specific exception if the token is invalid
            Log::error("Firebase token not found: " . $e->getMessage());
        } catch (\Kreait\Firebase\Exception\Messaging\InvalidMessage $e) {
            // Handle the specific exception if the message is invalid
            Log::error("Invalid message: " . $e->getMessage());
        } catch (\GuzzleHttp\Exception\RequestException $e) {
            // Handle the request exception
            Log::error("Request exception: " . $e->getMessage());
        } catch (\GuzzleHttp\Exception\ConnectException $e) {
            // Handle the connect exception
            Log::error("Connect exception: " . $e->getMessage());
        } catch (\Exception $e) {
            // Handle other exceptions
            Log::error("An error occurred while sending push notification: " . $e->getMessage());
        }
    }


    public function sendNotificationsByEvent(Request $request)
    {
        // Валидация данных
        $request->validate([
            'event_id' => 'required|exists:event_models,id',
            'title' => 'required|string',
            'body' => 'required|string',
        ]);

        // Получение мероприятия
        $event = Event::findOrFail($request->event_id);

        // Получение пользователей, которые посещали мероприятие
        $users = $event->users()->get();

        foreach ($users as $user) {
            $user->firebase_token = $this->updateFirebaseToken($user->id);
            $user->save();
            // Создание уведомления в базе данных
            Notification::create([
                'id' => Str::uuid()->toString(),
                'user_id' => $user->id,
                'title' => $request->title,
                'body' => $request->body,
                'sent_at' => now(),
            ]);

            // Отправка push-уведомления
            $this->sendPushNotification($user->firebase_token, $request->title, $request->body);
        }

        return redirect()->back()->with('success', 'Уведомления успешно отправлены по посещенному мероприятию.');
    }


    public function sendNotificationsByCriteria(Request $request)
    {
        // Валидация данных
        $request->validate([
            'gender' => 'nullable|string|in:муж,жен',
            'age' => 'nullable|integer',
            'class' => 'nullable|string',
            'event_type' => 'nullable|exists:event_types,id',
            'title' => 'required|string',
            'body' => 'required|string',
        ]);

        // Фильтрация пользователей на основе критериев
        $query = User::query();

        if ($request->filled('gender') || $request->filled('age')) {
            $query->whereHas('personalInformation', function ($q) use ($request) {
                if ($request->filled('gender')) {
                    $q->where('gender', $request->gender);
                }
                if ($request->filled('age')) {
                    $q->whereRaw('TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) = ?', [$request->age]);
                }
            });
        }

        if ($request->filled('class')) {
            $query->whereRaw('YEAR(CURDATE()) - start_year + 1 = ?', [$request->class]);
        }

        if ($request->filled('event_type')) {
            $query->whereHas('events', function ($q) use ($request) {
                $q->where('event_type_id', $request->event_type);
            });
        }

        $users = $query->get();

        foreach ($users as $user) {
            $user->firebase_token = $this->updateFirebaseToken($user->id);
            $user->save();
            // Создание уведомления в базе данных
            Notification::create([
                'id' => Str::uuid()->toString(),
                'user_id' => $user->id,
                'title' => $request->title,
                'body' => $request->body,
                'sent_at' => now(),
            ]);

            // Отправка push-уведомления
            $this->sendPushNotification($user->firebase_token, $request->title, $request->body);
        }

        return redirect()->back()->with('success', 'Уведомления успешно отправлены по критериям отбора.');
    }

    public function sendNotificationsToEachUser(Request $request)
    {
        // Валидация данных
        $request->validate([
            'user_id' => 'required|exists:users,id',
            'title' => 'required|string',
            'body' => 'required|string',
        ]);
        // Получение пользователя
        $user = User::findOrFail($request->user_id);

        // Создание уведомления в базе данных
        Notification::create([
            'id' => Str::uuid()->toString(),
            'user_id' => $user->id,
            'title' => $request->title,
            'body' => $request->body,
            'sent_at' => now(),
        ]);
        $user->firebase_token = $this->updateFirebaseToken($user->id);
        $user->save();
        $this->sendPushNotification($user->firebase_token, $request->title, $request->body);
    }


}
