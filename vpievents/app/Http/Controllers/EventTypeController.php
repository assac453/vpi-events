<?php

namespace App\Http\Controllers;

use App\Models\EventType;
use Illuminate\Http\Request;

class EventTypeController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        return view('forms.event_type_create');
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request, $eventId = null)
    {
        // Валидация данных формы
        $validatedData = $request->validate([
            'name' => 'required|string|max:255',
        ]);

        // Создание нового типа события
        $eventType = new EventType();
        $eventType->name = $request->name;
        $eventType->save();

        // Перенаправление пользователя обратно на страницу редактирования мероприятия, если указан идентификатор мероприятия
        if ($eventId) {
            return redirect()->route('event.edit', $eventId)->with('success', 'Тип события успешно создан');
        }

        // Перенаправление пользователя на предыдущую страницу
        return redirect()->back()->with('success', 'Тип события успешно создан');
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
