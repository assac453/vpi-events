<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Event;
use App\Models\User;
use Illuminate\Http\Request;

class UserController extends Controller
{
    public function events(Request $request, $id)
    {
        $user = User::findOrFail($id);

        // Найти все мероприятия, которые посетил пользователь
        $attendedEvents = Event::whereHas('users', function ($query) use ($id) {
            $query->where('id', $id);
        })->get();

        // Найти все мероприятия, на которые пользователь еще не зарегистрирован
        $availableEvents = Event::whereDoesntHave('users', function ($query) use ($id) {
            $query->where('id', $id);
        })->get();

        return view('components.user_events_list', compact('user', 'attendedEvents', 'availableEvents'));
    }

    public function unregisterUserFromEvent(Request $request, $userId, $eventId)
    {
        $user = User::findOrFail($userId);
        $event = Event::findOrFail($eventId);

        // Удалить пользователя из мероприятия
        $event->users()->detach($userId);

        // Вернуть сообщение об успешной операции
        return redirect()->route('users.events', $userId)->with('success', 'Пользователь успешно дерегистрирован с мероприятия!');
    }

    public function registerEvent(Request $request, $userId, $eventId)
    {
        // Найдем пользователя по его ID
        $user = User::findOrFail($userId);

        // Найдем мероприятие по его ID
        $event = Event::findOrFail($eventId);

        // Проверим, посещает ли пользователь уже это мероприятие
        if ($user->events()->where('id', $eventId)->exists()) {
            return redirect()->back()->with('error', 'Вы уже зарегистрированы на это мероприятие.');
        }

        // Добавим мероприятие к списку мероприятий пользователя
        $user->events()->attach($eventId);

        // Вернем пользователя обратно на страницу с мероприятиями с сообщением об успешной регистрации
        return redirect()->back()->with('success', 'Вы успешно зарегистрированы на мероприятие: ' . $event->name);
    }

    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $users = User::with(['personalInformation', 'additionalInformation', 'contactInformation', 'schoolInformation'])->get();
        return view('components.users_list', compact('users'));
        //
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
