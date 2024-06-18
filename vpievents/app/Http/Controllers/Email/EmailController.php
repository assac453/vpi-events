<?php

namespace App\Http\Controllers\Email;

use App\Http\Controllers\Controller;
use App\Http\Controllers\Notification\PushNotificationController;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Mail;

class EmailController extends Controller
{
    protected $pushNotificationController;

    public function __construct(PushNotificationController $pushNotificationController)
    {
        $this->pushNotificationController = $pushNotificationController;
    }

    //пароль от почты #vpievents@yandex.ru vudkyirbpgvsbeyb
    public function send(Request $request)
    {

        $request->merge(['title' => 'ВПИ События']);


        $request->validate([
            'email' => 'required|email',
            'message' => 'required|string',
        ]);
        $sendToMobile = $request->has('sendToMobile'); // Проверяем наличие флага sendToMobile


        $email = $request->input('email');
        $message = $request->input('message');
        $title = $request->input('title');


        try {
            Mail::raw($message, function ($msg) use ($email, $title) {
                $msg->to($email)
                    ->subject($title);
            });
        } catch (\Exception $e) {
            // Обработка ошибки, например, вывод сообщения об ошибке или логирование
            // В данном случае мы просто проигнорируем ошибку и продолжим выполнение
        }


        $user = User::where('email', $email)->first();
        $user_id = $user->id;

        $request->merge(['user_id' => $user_id]);

        $requestForUser = Request::createFromBase($request); // Создаем новый объект Request для каждого пользователя
        $requestForUser->merge(['body' => $request->input('message')]); // Обновляем user_id для текущего пользователя


        if ($sendToMobile) {
            $this->pushNotificationController->sendNotificationsToEachUser($requestForUser);
        }

        return back()->with('success', 'Письмо успешно отправлено!');
    }

    public function sends(Request $request)
    {
        $request->validate([
            'emails' => 'required|string', // Убедитесь, что emails является строкой
            'message' => 'required|string',
        ]);
        $sendToMobile = $request->has('sendToMobile'); // Проверяем наличие флага sendToMobile

        $request->merge(['title' => 'ВПИ События']);
        $title = $request->input('title');


        $emailsString = $request->input('emails');
        $emails = explode(',', $emailsString); // Разделить строку на массив по запятой

        $invalidEmails = [];
        foreach ($emails as $email) {
            $email = trim($email); // Убедитесь, что удаляются лишние пробелы
            if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
                $invalidEmails[] = $email; // Добавить невалидный email в массив
            }
        }

        if (!empty($invalidEmails)) {
            return back()->withErrors(['emails' => 'Некоторые электронные адреса недействительны: ' . implode(', ', $invalidEmails)]);
        }

        $message = $request->input('message');

        $userIds = [];

        foreach ($emails as $email) {
            try {
                Mail::raw($message, function ($msg) use ($email, $title) {
                    $msg->to($email)
                        ->subject($title);
                });
            } catch (\Exception $e) {
                // Обработка ошибки, например, вывод сообщения об ошибке или логирование
                // В данном случае мы просто проигнорируем ошибку и продолжим выполнение
            }

            // Найдем пользователя по адресу электронной почты и добавим его id в массив
            $user = User::where('email', $email)->first();
            if ($user) {
                $userIds[] = $user->id;
            }

        }

//        dd($userIds);
        // Далее отправим уведомления каждому пользователю


        $userIdeas = [];


        foreach ($userIds as $userId) {
            $requestForUser = Request::createFromBase($request); // Создаем новый объект Request для каждого пользователя
            $requestForUser->merge(['user_id' => $userId]); // Обновляем user_id для текущего пользователя
            $requestForUser->merge(['body' => $request->input('message')]); // Обновляем user_id для текущего пользователя
            $userIdeas[] = $requestForUser;
            if ($sendToMobile) {
                $this->pushNotificationController->sendNotificationsToEachUser($requestForUser);
            }
        }

//        dd($userIdeas);


//
//        $user = User::where('email', $email)->first();
//        $user_id = $user->id;
//
//        $request->merge(['user_id' => $user_id]);
//
//        if ($sendToMobile)
//        {
//            $this->pushNotificationController->sendNotificationsToEachUser($request);
//        }

        return back()->with('success', 'Письма успешно отправлены!');
    }
}
