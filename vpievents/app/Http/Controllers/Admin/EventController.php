<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Event;
use App\Models\EventType;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;
use SimpleSoftwareIO\QrCode\Facades\QrCode;

class EventController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $events = Event::with('event_type')->get();
        return view('components.events_list', ['events' => $events]);
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        $eventTypes = EventType::all();
        return view('forms.events_create', ['eventTypes' => $eventTypes]);
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {

        // Проверка входных данных
        $request->validate([
            'name' => 'required|string|max:255',
            'address' => 'required|string|max:255',
            'longitude' => 'required|numeric',
            'latitude' => 'required|numeric',
            'description' => 'required|string',
            'points' => 'required|integer',
            'image' => 'required|image|mimes:jpeg,png,jpg,gif|max:2048',
            'begin' => 'required|date',
            'end' => 'required|date|after:begin',
        ]);

        $data = $request->only(['name', 'address', 'longitude', 'latitude', 'description', 'points', 'begin', 'end', 'event_type_id']);


        if ($request->hasFile('image')) {
            $image = $request->file('image');
            $imageName = time() . '.' . $image->getClientOriginalExtension(); // Генерация уникального имени для изображения
            $image->move(public_path('images'), $imageName); // Сохранение изображения в папку "public/images"
            $data['image'] = $imageName; // Сохранение имени изображения в базу данных
        }
        // Создание нового события
        $event = Event::create($data);
//        if ($event) {
        return redirect()->route('event.index')->with('success', 'Событие успешно добавлено.');
//        } else {
//            return redirect()->back()->with('error', 'Что-то пошло не так. Пожалуйста, попробуйте снова.');
//        }
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {

    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(string $id)
    {
        $event = Event::findOrFail($id);
        $eventTypes = EventType::all();
        return view('forms.events_edit', ['event'=>$event, 'eventTypes' => $eventTypes]);
    }

    /**
     * Update the specified resource in storage.
     */
    /*public function update(Request $request, string $id)
    {
        $event = Event::findOrFail($id);

        // Проверка входных данных
        $request->validate([
            'name' => 'required|string|max:255',
            'address' => 'required|string|max:255',
            'longitude' => 'required|numeric',
            'latitude' => 'required|numeric',
            'description' => 'required|string',
            'points' => 'required|integer',
            'image' => 'nullable|image|mimes:jpeg,png,jpg,gif|max:2048',
            'begin' => 'required|date',
            'end' => 'required|date|after:begin',
        ]);

        $data = $request->only(['name', 'address', 'longitude', 'latitude', 'description', 'points', 'begin', 'end', 'event_type_id']);

        if ($request->hasFile('image')) {
            // Удаление старого изображения, если оно существует
            if ($event->image && file_exists(public_path('images/' . $event->image))) {
                unlink(public_path('images/' . $event->image));
            }

            $image = $request->file('image');
            $imageName = time() . '.' . $image->getClientOriginalExtension(); // Генерация уникального имени для изображения
            $image->move(public_path('images'), $imageName); // Сохранение изображения в папку "public/images"
            $data['image'] = $imageName; // Сохранение имени изображения в базу данных
        } else {
            // Сохранение старого имени изображения, если новое не загружено
            $data['image'] = $event->image;
        }

        // Обновление события
        $event->update($data);

        return redirect()->route('event.index')->with('success', 'Событие успешно обновлено.');
    }*/
    public function update(Request $request, string $id)
    {
        $event = Event::findOrFail($id);

        // Проверка входных данных
        $request->validate([
            'name' => 'required|string|max:255',
            'address' => 'required|string|max:255',
            'longitude' => 'required|numeric',
            'latitude' => 'required|numeric',
            'description' => 'required|string',
            'points' => 'required|integer',
            'image' => 'nullable|image|mimes:jpeg,png,jpg,gif|max:2048',
            'begin' => 'required|date',
            'end' => 'required|date|after:begin',
        ]);

        $data = $request->only(['name', 'address', 'longitude', 'latitude', 'description', 'points', 'begin', 'end', 'event_type_id']);

        if ($request->hasFile('image')) {
            // Удаление старого изображения, если оно существует
            if ($event->image && file_exists(public_path('images/' . $event->image))) {
                unlink(public_path('images/' . $event->image));
            }

            $image = $request->file('image');
            $imageName = time() . '.' . $image->getClientOriginalExtension(); // Генерация уникального имени для изображения
            $image->move(public_path('images'), $imageName); // Сохранение изображения в папку "public/images"
            $data['image'] = $imageName; // Сохранение имени изображения в базу данных
        }

        // Обновление события
        $event->update($data);

        return redirect()->route('event.index')->with('success', 'Событие успешно обновлено.');
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        $event = Event::findOrFail($id);

        $event->delete();
        // Удаление изображения из директории хранения
        if (Storage::disk('public')->exists('images/' . $event->image)) {
            Storage::disk('public')->delete('images/' . $event->image);
        }

        return redirect()->route('event.index')->with('success', 'Элемент успешно удалён');
    }

    public function qrcode(string $uuid)
    {

        $event = Event::findOrFail($uuid);
        $qrcode = QrCode::format('png')->size(800)->generate($event->id);

        // Генерация пути для временного хранения изображения
        $filePath = 'public/qrcodes/' . $event->id . '.png';
        Storage::put($filePath, $qrcode);

        return view('layouts.event_qrcode', [
            'qrcode' => 'data:image/png;base64,' . base64_encode($qrcode),
            'downloadUrl' => Storage::url($filePath)
        ]);
    }

    public function report(string $id)
    {

    }
}
